package com.module.plugin.cpt.util

import com.module.plugin.cpt.TransformApp;
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

import java.util.jar.JarEntry
import java.util.jar.JarFile

class ScanUtil {

    //-------------------处理jar文件---------------------
    static boolean shouldProcessPreDexJar(String path) {
//        println("----------shouldProcessPreDexJar------------ " + path)
        return !path.contains("com.android.support") && !path.contains("/android/m2repository")
    }

    static void scanJar(File jarFile, File destFile) {
//        println("----------scanJar------------ " + jarFile.absolutePath)
        if (jarFile) {
            def file = new JarFile(jarFile)
            Enumeration enumeration = file.entries()
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()
                if (ScanUtil.shouldProcessClass(entryName) || ScanUtil.shouldServiceImpl(entryName)) {
//                    println("----------scanJar shouldProcessClass ------------ " + (entryName))
                    InputStream inputStream = file.getInputStream(jarEntry)
                    scanClass(inputStream)
                    inputStream.close()
                } else if (ScanSetting.GENERATE_TO_CLASS_FILE_NAME == entryName) {
                    // mark this jar file contains LogisticsCenter.class
                    // After the scan is complete, we will generate register code into this file
                    TransformApp.fileContainsInitClass= destFile

                    println("----------scanJar jarFile ------------ " + (null != jarFile ? jarFile.absolutePath : " null"))
                    println("----------scanJar fileContainsInitClass ------------ " + (null != destFile ? destFile.absolutePath : " null"))
                }
            }
            file.close()
        }
    }

    //-------------------处理Java文件---------------------
    static boolean shouldProcessClass(String entryName) {
        return entryName != null && entryName.startsWith(ScanSetting.FLITER_CLASS_NAME_START) && entryName.endsWith(ScanSetting.FLITER_CLASS_NAME_END) && !entryName.endsWith('$'+ScanSetting.FLITER_CLASS_NAME_END)
    }

    static boolean shouldServiceImpl(String entryName) {
        return entryName != null && entryName.endsWith(ScanSetting.FLITER_CLASS_NAME_SERVICE_IMPL_END)
    }

    static boolean shouldProcessClassWithLog(String entryName) {
        return entryName != null  && entryName.endsWith('Service\$1.class')
    }

    static void scanClass(File file) {
        println("----------scanClass------------")
        scanClass(new FileInputStream(file))
    }

    static void scanClass(InputStream inputStream) {
        ClassReader cr = new ClassReader(inputStream)
        ClassWriter cw = new ClassWriter(cr, 0)
        ScanClassVisitor cv = new ScanClassVisitor(Opcodes.ASM5, cw)
        cr.accept(cv, ClassReader.EXPAND_FRAMES)
        inputStream.close()
    }

    static class ScanClassVisitor extends ClassVisitor {

        ScanClassVisitor(int api, ClassVisitor cv) {
            super(api, cv)
        }

        void visit(int version, int access, String name, String signature,
                   String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces)
            TransformApp.registerList.each { ext ->
                if (ext.interfaceName && interfaces != null) {
                    interfaces.each { itName ->
println("ScanClassVisitor cmpInterface "+ext.interfaceName + " interface " + itName + " name " + name)
                        if (itName == ext.interfaceName) {
                            //fix repeated inject init code when Multi-channel packaging
                            if (!ext.serviceList.contains(name)) {
                                ext.serviceList.add(name)
                            }
                        }else{
                            ext.allMap.put(itName,name);
                        }
                    }
                }
            }
        }
    }
}
