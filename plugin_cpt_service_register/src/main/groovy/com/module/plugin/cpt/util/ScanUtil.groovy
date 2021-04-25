package com.module.plugin.cpt.util

import com.module.plugin.cpt.TransformApp;
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.Opcodes

import java.util.jar.JarEntry
import java.util.jar.JarFile

class ScanUtil {

    //-------------------处理jar文件---------------------
    static boolean shouldProcessPreDexJar(String path) {
//        println("----------shouldProcessPreDexJar------------ " + path)
        return !path.contains("com.android.support") && !path.contains("/android/m2repository")
    }

    static boolean isServiceClass(String entryName) {
        //可以用正则处理
        return entryName != null && entryName.startsWith(ScanSetting.FLITER_CLASS_NAME_START) && entryName.endsWith(ScanSetting.FLITER_CLASS_NAME_END) && !entryName.endsWith('$'+ScanSetting.FLITER_CLASS_NAME_END)
    }

    static boolean isServiceImplClass(String entryName) {
        return entryName != null && entryName.endsWith(ScanSetting.FLITER_CLASS_NAME_SERVICE_IMPL_END)
    }

    static boolean isServiceInnerClass(String entryName) {
        return entryName != null  && entryName.endsWith('Service\$1.class')
    }

    //---------------------------------------------------
    static void scanJar(File jarFile, File destFile) {
//        println("----------scanJar------------ " + jarFile.absolutePath)
        if (jarFile) {
            def file = new JarFile(jarFile)
            Enumeration enumeration = file.entries()
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()
                if (ScanUtil.isServiceClass(entryName) || ScanUtil.isServiceImplClass(entryName)) {
//                    println("----------scanJar shouldProcessClass ------------ " + (entryName))
                    InputStream inputStream = file.getInputStream(jarEntry)
                    scanClass(inputStream)
                    inputStream.close()
                } else if (ScanSetting.GENERATE_TO_CLASS_FILE_NAME == entryName) {
                    // mark this jar file contains LogisticsCenter.class
                    // After the scan is complete, we will generate register code into this file
                    TransformApp.initCodeToClassFile= destFile

//                    println("----------scanJar jarFile ------------ " + (null != jarFile ? jarFile.absolutePath : " null"))
//                    println("----------scanJar fileContainsInitClass ------------ " + (null != destFile ? destFile.absolutePath : " null"))
                }
            }
            file.close()
        }
    }

    static void scanClass(File file) {
//        println("----------scanClass------------")
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

        String serviceName
        ScanClassVisitor(int api, ClassVisitor cv) {
            super(api, cv)
        }

        void visit(int version, int access, String name, String signature,
                   String superName, String[] interfaces) {
//println(String.format("ScanClassVisitor visit version %d access %d name %s signature %s superName %s",version,access,name,signature,superName))
            super.visit(version, access, name, signature, superName, interfaces)
            TransformApp.registerList.each { ext ->
                if (ext.interfaceName && interfaces != null) {
                    interfaces.each { itName ->
//println("ScanClassVisitor cmpInterface "+ext.interfaceName + " interface " + itName + " name " + name)
                        if (itName == ext.interfaceName) {
                            //fix repeated inject init code when Multi-channel packaging
                            if (!ext.serviceList.contains(name)) {
                                ext.serviceList.add(name)
                                serviceName = name
                            }
                        }else{
                            ext.allMap.put(itName,name)
                        }
                    }
                }
            }
        }

        @Override
        FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
//println(String.format("ScanClassVisitor visitField 1 serviceName %s access %d name %s descriptor %s signature %s value %s",serviceName,access,name,descriptor,signature,value))
            if(name == ScanSetting.MODULE_NAME_OF_FIELD){
                if(null != serviceName){
                    TransformApp.registerList.each { ext ->
                        ext.serviceModuleNameMap.put(serviceName,value)
//                        println(String.format("ScanClassVisitor visitField 2 serviceName %s value %s",serviceName,value))
                    }
                }
            }
            return super.visitField(access, name, descriptor, signature, value)
        }
    }
}
