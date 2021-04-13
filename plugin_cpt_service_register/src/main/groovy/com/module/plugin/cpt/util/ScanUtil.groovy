package com.module.plugin.cpt.util

import com.module.plugin.cpt.CptTransform;

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

class ScanUtil {

    static boolean shouldProcessClass(String entryName) {
        return entryName != null && entryName.startsWith(ScanSetting.FLITER_CLASS_NAME_START) && entryName.endsWith(ScanSetting.FLITER_CLASS_NAME_END) && !entryName.endsWith('$'+ScanSetting.FLITER_CLASS_NAME_END)
    }

    static boolean shouldProcessClasswithLog(String entryName) {
        return entryName != null  && entryName.endsWith('Service\$1.class')
    }

    static void scanClass(File file) {
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
            CptTransform.registerList.each { ext ->
                if (ext.interfaceName && interfaces != null) {
                    interfaces.each { itName ->
                        if (itName == ext.interfaceName) {
                            //fix repeated inject init code when Multi-channel packaging
                            if (!ext.classList.contains(name)) {
//                                println("----"+ext.interfaceName + " name " + name)
                                ext.classList.add(name)
                            }
                        }
                    }
                }
            }
        }
    }
}
