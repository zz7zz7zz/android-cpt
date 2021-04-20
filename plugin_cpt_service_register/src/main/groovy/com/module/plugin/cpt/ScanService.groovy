package com.module.plugin.cpt

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInvocation
import com.module.plugin.cpt.util.ScanUtil
import javassist.ClassPool
import org.apache.commons.codec.digest.DigestUtils

class ScanService {

    static void scanAllClasses(TransformInvocation transformInvocation, ClassPool classPool){
        println("----------------------ScanService scanAllClasses start----------------------")
        boolean leftSlash = File.separator == '/'

//        transformInvocation.outputProvider.deleteAll()

        transformInvocation.inputs.each {
            it.jarInputs.each { JarInput jarInput ->

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
                if (ScanUtil.shouldProcessPreDexJar(src.absolutePath)) {
                    ScanUtil.scanJar(src, dest)
                }
            }

            it.directoryInputs.each { DirectoryInput directoryInput ->

//                println("ScanService directoryInput------------ " + directoryInput.file.absolutePath)

                File dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                String root = directoryInput.file.absolutePath
                if (!root.endsWith(File.separator))
                    root += File.separator

                directoryInput.file.eachFileRecurse { File file ->
                    def path = file.absolutePath.replace(root, '')
                    if (!leftSlash) {
                        path = path.replaceAll("\\\\", "/")
                    }
                    if(file.isFile() && ScanUtil.shouldProcessClass(path)){
                        ScanUtil.scanClass(file)
                    }else if(file.isFile() && ScanUtil.shouldServiceImpl(path)){
                        ScanUtil.scanClass(file)
                    }

                    def className = path.replace("\\",".").replace("/",".").replace(".class","")
                    if (TransformApp.cptConfig.applicationName == className) {
                        TransformApp.oldAppFile = file
                        TransformApp.oldAppFileParentPath = directoryInput.file.absolutePath;
                        TransformApp.newAppFile = new File(dest.absolutePath+File.separator+path);

//                        println("oldAppFile.absolutePath "+TransformApp.oldAppFile.absolutePath)
//                        println("oldAppFileParentPath "+TransformApp.oldAppFileParentPath)
//                        println("newAppFile.absolutePath "+TransformApp.newAppFile.absolutePath)
                    }
                }
            }
        }

        TransformApp.registerList.each { ext->

            println("---------------- serviceList ----------------")
            ext.serviceList.each {
                ext.serviceImplMap.put(it,ext.allMap.get(it));
                println("serviceList  "+it)
            }

            println("---------------- serviceImplMap ----------------")
            ext.serviceImplMap.each { k,v ->
                println("serviceImplMap service-serviceImpl "+k + " ---> "+v)
            }

            println("---------------- allMap ----------------")
            ext.allMap.each { k,v ->
                println("allMap  "+k + " ---> " + v)
            }
        }

        println("---------------- fileContainsInitClass ----------------" + (null != TransformApp.fileContainsInitClass ? TransformApp.fileContainsInitClass.absolutePath : null))

        println("----------------------ScanService scanAllClasses end----------------------")
    }
}
