package com.module.plugin.cpt

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.module.plugin.cpt.bean.CptConfig
import com.module.plugin.cpt.util.ScanSetting
import javassist.ClassPool
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Project

class TransformApp extends Transform {

    def project
    def isApp
    def pool = ClassPool.default

    static CptConfig cptConfig
    static ArrayList<ScanSetting> registerList

    static File oldRegisterClassFile
    static String oldRegisterClassFileParentPath
    static File newRegisterClassFile

    static File initCodeToClassFile

    TransformApp(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "pluginTransformApp"
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

        println("----------------------TransformApp Scan / Generate code start----------------------")

        //1.扫描一遍jar和class文件，找到Service和ServiceImpl
        ScanService.scanServiceAndServiceImpl(transformInvocation)

        //        transformInvocation.outputProvider.deleteAll()

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

                //scan jar file to find classes
//                if (ScanUtil.shouldProcessPreDexJar(src.absolutePath)) {
//                    ScanUtil.scanJar(src, dest)
//                }

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
                }

                // copy to dest
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }

        println("----------------------TransformApp Scan code end----------------------finish, current cost time: " + (System.currentTimeMillis() - startTime) + "ms")

        //javassist 修改jar包有点问题；现在改为app_core也参与编译，在library中直接修改文件
        registerList.each { ext ->
            if (ext.serviceList.isEmpty()) {
                println("No class implements found for interface:" + ext.interfaceName)
            } else {
                //方案一：
                //已成功(Asm方案：在ServiceManager的registerService方法中注入服务)
                //该实现方案可以把ServiceManager的registerService方法定义为公有的
                //该实现方案可以把ServiceManager的registerService方法定义为私有的，开发过程中对外不暴露
                RegisterCodeGeneratorApp0.insertInitCodeTo(ext,cptConfig)

                //方案二：
                //已经成功(Javassist方案: 在App.class中的onCreate()方法中调用另外一个方法auto_register_service_2021_04_15(),该方法被动态加入，该方法中注入服务)
                //该实现方案可以把ServiceManager的registerService方法定义为公有的
                //该实现方案可以把ServiceManager的registerService方法定义为私有的，开发过程中对外不暴露，字节码操作时更改为public，但是此时又需要结合asm操作)
                //RegisterCodeGeneratorApp1.insertInitCodeToApp(ext, oldRegisterClassFileParentPath,cptConfig)
                //FileUtils.copyFile(oldRegisterClassFile, newRegisterClassFile)

                //未成功
                //RegisterCodeGenerator2.insertInitCodeTo(ext, initCodeToClassFile.parent+"/999.jar")
            }
        }

        println("----------------------TransformApp Generate code end----------------------finish, current cost time: " + (System.currentTimeMillis() - startTime) + "ms")
    }
}
