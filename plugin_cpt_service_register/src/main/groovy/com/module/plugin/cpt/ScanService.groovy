package com.module.plugin.cpt

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInvocation
import com.android.utils.FileUtils
import com.module.plugin.cpt.util.ScanSetting
import com.module.plugin.cpt.util.ScanUtil
import javassist.ClassPool
import org.apache.commons.codec.digest.DigestUtils

import java.util.jar.JarEntry
import java.util.jar.JarFile;

class ScanService {

    static void scanAllClasses(TransformInvocation transformInvocation, ClassPool classPool){

        boolean leftSlash = File.separator == '/'

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
                }
            }
        }

        CptTransform.registerList.each { ext->

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

        println("---------------- fileContainsInitClass ----------------" + (null != CptTransform.fileContainsInitClass ? CptTransform.fileContainsInitClass.absolutePath : null))


    }
}
