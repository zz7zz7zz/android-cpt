package com.module.plugin.cpt

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.module.plugin.cpt.util.ScanSetting
import com.module.plugin.cpt.util.ScanUtil
import javassist.ClassPool
import org.gradle.api.Project

class CptTransform extends Transform {

    def project

    def pool = ClassPool.default

    static ArrayList<ScanSetting> registerList
    static File providerFactoryClass
    static String providerFactoryParentPath

    static File destProviderFactoryClassFile

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
        return TransformManager.PROJECT_ONLY
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        println("----------------------Scan / Generate code start----------------------")
        long startTime = System.currentTimeMillis()
        boolean leftSlash = File.separator == '/'

        transformInvocation.inputs.each {
            it.jarInputs.each {

                pool.insertClassPath(it.file.absolutePath)

                def dest = transformInvocation.outputProvider.getContentLocation(it.name,it.contentTypes,it.scopes,Format.JAR)
                FileUtils.copyFile(it.file,dest)
            }

            // scan class files
            it.directoryInputs.each {

                File dest = transformInvocation.outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.DIRECTORY)
                String root = it.file.absolutePath
                if (!root.endsWith(File.separator))
                    root += File.separator
                it.file.eachFileRecurse { File file ->
                    def path = file.absolutePath.replace(root, '')
                    if (!leftSlash) {
                        path = path.replaceAll("\\\\", "/")
                    }

                    if(file.isFile() && ScanUtil.shouldProcessClass(path)){
//                        println("path "+path + " isFile " + file.isFile() + " process " + ScanUtil.shouldProcessClass(path));
                        ScanUtil.scanClass(file)
                    }else if (ScanSetting.GENERATE_TO_CLASS_FILE_NAME == path) {
                        // After the scan is complete, we will generate register code into this file
                        providerFactoryClass = file
                        providerFactoryParentPath = it.file.absolutePath;
                        destProviderFactoryClassFile = new File(dest.absolutePath+File.separator+path);

//                        println("fileContainsInitClass path "+path);
//                        println("file.absolutePath "+file.absolutePath);
//                        println("it.file.absolutePath "+it.file.absolutePath);
//                        println("dest.absolutePath "+dest.absolutePath);
                    }
                }

                // copy to dest
                FileUtils.copyDirectory(it.file, dest)
            }
        }

        println("----------------------Scan code end----------------------finish, current cost time: " + (System.currentTimeMillis() - startTime) + "ms")
        if (providerFactoryClass) {
            registerList.each { ext ->
                println('Insert register code to file ' + providerFactoryClass.absolutePath)

                if (ext.classList.isEmpty()) {
                    println("No class implements found for interface:" + ext.interfaceName)
                } else {

                    //将当前路径加入类池,不然找不到这个类
                    pool.appendClassPath(providerFactoryParentPath)
                    //为了能找到android相关的所有类，添加project.android.bootClasspath 加入android.jar，
                    pool.appendClassPath(project.android.bootClasspath[0].toString())

                    CptCodeGenerator.insertCodeTo(ext,providerFactoryParentPath)
                    FileUtils.copyFile(providerFactoryClass, destProviderFactoryClassFile)

                    ext.classList.each {
                        println(it)
//                        pool.appendClassPath("/Users/long/.gradle/caches/transforms-2/files-2.1/220da564e9915000fbdc9d39834da3cf/fragment-1.1.0/jars/classes.jar")
//                        CptCodeGenerator.insertLogCodeTo(it,providerFactoryParentPath)
                    }
                }
            }
        }
        println("----------------------Generate code end----------------------finish, current cost time: " + (System.currentTimeMillis() - startTime) + "ms")

    }
}
