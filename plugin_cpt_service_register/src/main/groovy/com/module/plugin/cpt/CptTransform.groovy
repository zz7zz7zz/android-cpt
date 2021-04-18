package com.module.plugin.cpt

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.module.plugin.cpt.bean.CptJarPathConfig
import com.module.plugin.cpt.util.ScanSetting
import com.module.plugin.cpt.util.ScanUtil
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.Modifier
import org.gradle.api.Project

class CptTransform extends Transform {

    def project

    def pool = ClassPool.default

    static CptJarPathConfig cptJarPathConfig

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

//        println("----------------------Scan / Generate code start----------------------")

        long startTime = System.currentTimeMillis()
        boolean leftSlash = File.separator == '/'

        transformInvocation.inputs.each {
            it.jarInputs.each {

                pool.insertClassPath(it.file.absolutePath)

                println("----------jarInputs------------ " + it.file.absolutePath)
                File src = it.file
                def dest = transformInvocation.outputProvider.getContentLocation(it.name,it.contentTypes,it.scopes,Format.JAR)

                //scan jar file to find classes
                if (ScanUtil.shouldProcessPreDexJar(src.absolutePath)) {
                    ScanUtil.scanJar(src, dest)
                }

                FileUtils.copyFile(src,dest)
            }

            // scan class files
            it.directoryInputs.each {

                pool.insertClassPath(it.file.absolutePath)
//                pool.insertClassPath("/Users/long/.gradle/caches/transforms-2/files-2.1/c734ae58d954bef0f67be11fa375b6b1/jetified-arouter-api-1.5.1/jars/classes.jar")
                pool.insertClassPath(cptJarPathConfig.arouterPath)

                File dest = transformInvocation.outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.DIRECTORY)
                String root = it.file.absolutePath
                if (!root.endsWith(File.separator))
                    root += File.separator
                it.file.eachFileRecurse { File file ->
                    def path = file.absolutePath.replace(root, '')
                    if (!leftSlash) {
                        path = path.replaceAll("\\\\", "/")
                    }

//                    println("path "+path + " isFile " + file.isFile() + " process " + ScanUtil.shouldProcessClass(path));
                    if(file.isFile() && ScanUtil.shouldProcessClass(path)){
                        ScanUtil.scanClass(file)
                    }else if(file.isFile() && ScanUtil.shouldServiceImpl(path)){
                        ScanUtil.scanClass(file)
                    }else if (ScanSetting.GENERATE_TO_CLASS_FILE_NAME == path) {
//                        println("----------scanJar providerFactoryClass ------------ " + file.absolutePath)
                        // After the scan is complete, we will generate register code into this file
                        providerFactoryClass = file
                        providerFactoryParentPath = it.file.absolutePath;
                        destComponentServiceManagerClassFile = new File(dest.absolutePath+File.separator+path);

//                        println("fileContainsInitClass path "+path);
//                        println("file.absolutePath "+file.absolutePath);
//                        println("it.file.absolutePath "+it.file.absolutePath);
//                        println("dest.absolutePath "+dest.absolutePath);
                    }

                    if(file.isFile() && ScanUtil.shouldProcessClasswithLog(path)){
                        //为了能找到android相关的所有类，添加project.android.bootClasspath 加入android.jar，
                        pool.appendClassPath(project.android.bootClasspath[0].toString())
//                        pool.appendClassPath("/Users/long/.gradle/caches/transforms-2/files-2.1/220da564e9915000fbdc9d39834da3cf/fragment-1.1.0/jars/classes.jar")
                        pool.insertClassPath(cptJarPathConfig.fragmentPath)

                        def preFileName = it.file.absolutePath
                        //println("-----preFileName----- " + file.absolutePath)
                        pool.insertClassPath(preFileName)
                        findTarget(file,preFileName)
                    }
                }

                // copy to dest
                FileUtils.copyDirectory(it.file, dest)
            }
        }

//        println("----------------------Scan code end----------------------finish, current cost time: " + (System.currentTimeMillis() - startTime) + "ms")
        if (providerFactoryClass) {
            registerList.each { ext ->
//                println('Insert register code to file ' + providerFactoryClass.absolutePath)

                if (ext.serviceList.isEmpty()) {
                    println("No class implements found for interface:" + ext.interfaceName)
                } else {

                    //将当前路径加入类池,不然找不到这个类
                    pool.appendClassPath(providerFactoryParentPath)
                    //为了能找到android相关的所有类，添加project.android.bootClasspath 加入android.jar，
                    pool.appendClassPath(project.android.bootClasspath[0].toString())

//                    CptCodeGenerator.insertCodeTo(ext,providerFactoryParentPath)
//                    FileUtils.copyFile(providerFactoryClass, destComponentServiceManagerClassFile)

                    ext.serviceList.each {
                        println("service: "+it + " serviceImpl: "+ext.serviceImplMap.get(it))
//                        pool.appendClassPath("/Users/long/.gradle/caches/transforms-2/files-2.1/220da564e9915000fbdc9d39834da3cf/fragment-1.1.0/jars/classes.jar")
//                        CptCodeGenerator.insertLogCodeTo(it,providerFactoryParentPath)
                    }

                    RegisterCodeGenerator.insertInitCodeTo(ext)
                }
            }
        }
//        println("----------------------Generate code end----------------------finish, current cost time: " + (System.currentTimeMillis() - startTime) + "ms")

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

    void addCode(CtClass ctClass,String fileName){
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
