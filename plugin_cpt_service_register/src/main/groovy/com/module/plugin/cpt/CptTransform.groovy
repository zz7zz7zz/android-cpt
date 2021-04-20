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

class CptTransform extends Transform {

    def project

    def pool = ClassPool.default

    static CptConfig cptConfig

    static ArrayList<ScanSetting> registerList
    static File providerFactoryClass
    static String providerFactoryParentPath

    static File destComponentServiceManagerClassFile

    static File fileContainsInitClass

    def isApp

    CptTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "CptTransform"
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

        println("----------------------Scan / Generate code start----------------------")
        if(isApp){
            ScanService.scanAllClasses(transformInvocation,pool)
        }

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

                if(isApp){
                    //                //scan jar file to find classes
                    if (ScanUtil.shouldProcessPreDexJar(src.absolutePath)) {
                        ScanUtil.scanJar(src, dest)
                    }
                }

                FileUtils.copyFile(src,dest)
            }

            // scan class files
            input.directoryInputs.each { DirectoryInput directoryInput ->

                pool.insertClassPath(directoryInput.file.absolutePath)
//                pool.insertClassPath("/Users/long/.gradle/caches/transforms-2/files-2.1/c734ae58d954bef0f67be11fa375b6b1/jetified-arouter-api-1.5.1/jars/classes.jar")
//                pool.insertClassPath(cptConfig.arouterPath)


                File dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                String root = directoryInput.file.absolutePath
                if (!root.endsWith(File.separator))
                    root += File.separator
                directoryInput.file.eachFileRecurse { File file ->
                    def path = file.absolutePath.replace(root, '')
                    if (!leftSlash) {
                        path = path.replaceAll("\\\\", "/")
                    }
//                    if(file.isFile() && ScanUtil.shouldProcessClass(path)){
//                        ScanUtil.scanClass(file)
//                    }else if(file.isFile() && ScanUtil.shouldServiceImpl(path)){
//                        ScanUtil.scanClass(file)
//                    }
//
//                    if (ScanSetting.GENERATE_TO_CLASS_FILE_NAME == path) {
////                        println("----------scanJar providerFactoryClass ------------ " + file.absolutePath)
//                        // After the scan is complete, we will generate register code into this file
//                        providerFactoryClass = file
//                        providerFactoryParentPath = directoryInput.file.absolutePath;
//                        destComponentServiceManagerClassFile = new File(dest.absolutePath+File.separator+path);
//
////                        println("fileContainsInitClass path "+path);
////                        println("file.absolutePath "+file.absolutePath);
////                        println("it.file.absolutePath "+it.file.absolutePath);
////                        println("dest.absolutePath "+dest.absolutePath);
//                    }

                    if(isApp){
                        if (ScanSetting.GENERATE_TO_CLASS_FILE_NAME2 == path) {
                            providerFactoryClass = file
                            providerFactoryParentPath = directoryInput.file.absolutePath;
                            destComponentServiceManagerClassFile = new File(dest.absolutePath+File.separator+path);

                            println("path "+path);
                            println("providerFactoryClass.absolutePath "+providerFactoryClass.absolutePath);
                            println("providerFactoryParentPath "+providerFactoryParentPath);
                            println("ddestComponentServiceManagerClassFile.absolutePath "+destComponentServiceManagerClassFile.absolutePath);
                        }
                    }

                    if(!isApp){
                        //                    //在默认实现类中各个方法打印日志
                        if(file.isFile() && ScanUtil.shouldProcessClasswithLog(path)){
                            //为了能找到android相关的所有类，添加project.android.bootClasspath 加入android.jar，
                            pool.appendClassPath(project.android.bootClasspath[0].toString())
                            pool.insertClassPath(cptConfig.fragmentPath)

                            def preFileName = directoryInput.file.absolutePath
                            //println("-----preFileName----- " + file.absolutePath)
                            pool.insertClassPath(preFileName)
                            findTarget(file,preFileName)
                        }
                    }
                }

                // copy to dest
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }

        println("----------------------Scan code end----------------------finish, current cost time: " + (System.currentTimeMillis() - startTime) + "ms")
//javassist 修改jar包有点问题；现在改为app_core也参与编译，在library中直接修改文件
        if(isApp){
            if (fileContainsInitClass) {
                registerList.each { ext ->
                    println('Insert register code to file ' + fileContainsInitClass.absolutePath)
                    println('Insert register code to file ' + fileContainsInitClass.parent)
                    if (ext.serviceList.isEmpty()) {
                        println("No class implements found for interface:" + ext.interfaceName)
                    } else {
//                    RegisterCodeGenerator.insertInitCodeTo(ext)
//                    RegisterCodeGenerator2.insertInitCodeTo(ext, fileContainsInitClass.parent+"/999.jar")

                        RegisterCodeGenerator2.insertInitCodeToApp(ext, providerFactoryParentPath,cptConfig)
                        FileUtils.copyFile(providerFactoryClass, destComponentServiceManagerClassFile)
                    }
                }
            }
        }

//        if (fileContainsInitClass) {
//            registerList.each { ext ->
//                println('Insert register code to file ' + fileContainsInitClass.absolutePath)
//                println('Insert register code to file ' + fileContainsInitClass.parent)
//                if (ext.serviceList.isEmpty()) {
//                    println("No class implements found for interface:" + ext.interfaceName)
//                } else {
//                    if(!isApp){
//                        //将当前路径加入类池,不然找不到这个类
//                        pool.appendClassPath(providerFactoryParentPath)
//                        //为了能找到android相关的所有类，添加project.android.bootClasspath 加入android.jar，
//                        pool.appendClassPath(project.android.bootClasspath[0].toString())
//
//                        CptCodeGenerator.insertCodeTo(ext,providerFactoryParentPath)
//                        FileUtils.copyFile(providerFactoryClass, destComponentServiceManagerClassFile)
//                    }
//                }
//            }
//        }



        println("----------------------Generate code end----------------------finish, current cost time: " + (System.currentTimeMillis() - startTime) + "ms" + " isApp" + isApp)

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

//        if(ctClass.getName().contains("PatchProxy")){
//            return
//        }

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

            method.insertBefore("android.util.Log.e(TAG, com.module.service.IConsts.PROMPT_COMPONENT_NOT_FOUND);")

        }

        ctClass.writeFile(fileName)
        ctClass.detach()
    }
}
