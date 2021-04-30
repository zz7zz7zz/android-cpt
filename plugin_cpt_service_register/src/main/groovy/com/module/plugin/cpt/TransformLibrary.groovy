package com.module.plugin.cpt

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.module.plugin.cpt.bean.CptConfig
import com.module.plugin.cpt.util.ScanSetting
import com.module.plugin.cpt.util.ScanUtil
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.Modifier
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Project

class TransformLibrary extends Transform {

    def project
    def isApp
    def pool = ClassPool.default

    static CptConfig cptConfig
    static ArrayList<ScanSetting> registerList

    static File oldServiceManagerClassFile
    static String oldServiceManagerClassFileParentPath
    static File newServiceManagerClassFileName

    TransformLibrary(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "pluginTransformLibrary"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return this.isApp ? TransformManager.SCOPE_FULL_PROJECT : TransformManager.PROJECT_ONLY
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        println("----------------------TransformLibrary Scan / Generate code start----------------------")

        long startTime = System.currentTimeMillis()
        boolean leftSlash = File.separator == '/'

        transformInvocation.inputs.each { TransformInput input ->

            input.jarInputs.each { JarInput jarInput ->

                pool.insertClassPath(jarInput.file.absolutePath)

//                println("ScanService jarInputs------------ " + jarInput.file.absolutePath)

                String destName = jarInput.name
                // rename jar files
                def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath)
                if (destName.endsWith(".jar")) {
                    destName = destName.substring(0, destName.length() - 4)
                }
                // input file
                File src = jarInput.file
                // output file
                File dest = transformInvocation.outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR)

                FileUtils.copyFile(src,dest)
            }

            // scan class files
            input.directoryInputs.each { DirectoryInput directoryInput ->

                pool.insertClassPath(directoryInput.file.absolutePath)

                File dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                String root = directoryInput.file.absolutePath
                if (!root.endsWith(File.separator))
                    root += File.separator
                directoryInput.file.eachFileRecurse { File file ->
                    def path = file.absolutePath.replace(root, '')
                    if (!leftSlash) {
                        path = path.replaceAll("\\\\", "/")
                    }

//                    println("----------TransformLibrary directoryInputs file  " + file.absolutePath)
                    //创建默认的Service
                    if(file.isFile() && ScanUtil.isServiceClass(path)){
                        ScanUtil.scanClass(file)
                    }else if (ScanUtil.isServiceManagerClass(path)) {
                        // After the scan is complete, we will generate register code into this file
                        oldServiceManagerClassFile = file
                        oldServiceManagerClassFileParentPath = directoryInput.file.absolutePath;
                        newServiceManagerClassFileName = new File(dest.absolutePath+File.separator+path);

//                        println("oldServiceManagerClassFileName.absolutePath "+TransformLibrary.oldServiceManagerClassFile.absolutePath)
//                        println("providerFactoryParentPath "+TransformLibrary.oldServiceManagerClassFileParentPath)
//                        println("newServiceManagerClassFileName.absolutePath "+TransformLibrary.newServiceManagerClassFileName.absolutePath)
                    }

                     //在默认实现类中各个方法打印日志
                    if(file.isFile() && ScanUtil.isServiceInnerClass(path)){
                        //为了能找到android相关的所有类，添加project.android.bootClasspath 加入android.jar，
                        pool.appendClassPath(project.android.bootClasspath[0].toString())
                        pool.insertClassPath(cptConfig.fragmentPath)

                        def preFileName = directoryInput.file.absolutePath
                        //println("-----preFileName----- " + file.absolutePath)
                        pool.insertClassPath(preFileName)
                        findTarget(file,preFileName)
                    }
                }

                // copy to dest
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }

        println("----------------------TransformLibrary Generate code end----------------------finish, current cost time: " + (System.currentTimeMillis() - startTime) + "ms")
        if (oldServiceManagerClassFile) {
            registerList.each { ext ->
//                println('Insert register code to file ' + oldServiceManagerClassFileName.absolutePath)

                if (ext.serviceList.isEmpty()) {
                    println("No class implements found for interface:" + ext.interfaceName)
                } else {

//                    ext.serviceList.each {
//                        println("service: "+it + " serviceImpl: "+ext.serviceImplMap.get(it))
//                    }

                    //将当前路径加入类池,不然找不到这个类
                    pool.appendClassPath(oldServiceManagerClassFileParentPath)
                    //为了能找到android相关的所有类，添加project.android.bootClasspath 加入android.jar，
                    pool.appendClassPath(project.android.bootClasspath[0].toString())

                    RegisterCodeGeneratorLibrary0.insertCodeTo(ext,oldServiceManagerClassFileParentPath)
                    FileUtils.copyFile(oldServiceManagerClassFile, newServiceManagerClassFileName)
                }
            }
        }
        println("----------------------TransformLibrary Generate code end----------------------finish, current cost time: " + (System.currentTimeMillis() - startTime) + "ms")
    }


    void findTarget(File dir,String fileName){
        if(dir.isDirectory()){
            dir.listFiles().each {
                findTarget(it,fileName)
            }
        }else{
            modify(dir,fileName)
        }
    }

    void modify(File dir,String fileName){

        def filePath = dir.absolutePath
        if(!filePath.endsWith(".class")){
            return
        }

        if(filePath.contains('R$') || filePath.contains('R.class') || filePath.contains('BuildConfig.class')){
            return
        }


        def className = filePath.replace(fileName,"")
                .replace("\\",".")
                .replace("/",".")
        def name = className.replace(".class","").substring(1)

//        println("-----start----- ")
//        println("filePath " + filePath)
//        println("fileName " + fileName)
//        println("name " + name)
//        println("-----end----- ")

//        if(name.contains("com.open.test")){
            //修改类，插入代码
            CtClass ctClass = pool.get(name)

            addCode(ctClass,fileName)

//        }

    }

    static void addCode(CtClass ctClass,String fileName){
        //报异常：javassist.CannotCompileException: no method body

        if(ctClass.isInterface()){
            return
        }

//        println("class "+ctClass)
//        println("className "+ctClass.getName())

        CtMethod[] ctMethods = ctClass.getDeclaredMethods()
        for (method in ctMethods){
//            println("method "+method.getName())
            //空函数或者抽象函数
//            if(method.isEmpty()){
//                continue
//            }

            //Native方法
            if (Modifier.isNative(method.getModifiers())){
                continue
            }

            String PROMPT_COMPONENT_NOT_FOUND = "\"%s(), 服务未找到，可能的原因是:  1.没有将组件代码打包进Apk中; 2.已实现对外提供服务类，但没有添加注解,如 @Route(path = xxx, name = yyy); 3.未实现对外提供服务类; 4.本地组件配置/服务器组件配置，未启用组件\""
            String code = String.format("android.util.Log.e(TAG, %s);",String.format(PROMPT_COMPONENT_NOT_FOUND,method.getName()))
            method.insertBefore(code)
        }

        ctClass.writeFile(fileName)
        ctClass.detach()
    }
}
