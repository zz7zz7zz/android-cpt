package com.module.plugin.cpt;

import com.module.plugin.cpt.util.ScanSetting;

import org.apache.commons.io.IOUtils
import org.objectweb.asm.*

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class RegisterCodeGeneratorApp0 {

    ScanSetting extension

    private RegisterCodeGeneratorApp0(ScanSetting extension) {
        this.extension = extension
    }

    static void insertInitCodeTo(ScanSetting registerSetting) {
        if (registerSetting != null && !registerSetting.serviceList.isEmpty()) {
            RegisterCodeGeneratorApp0 processor = new RegisterCodeGeneratorApp0(registerSetting)
            File file = TransformApp.fileContainsInitClass
            if (null != file && file.getName().endsWith('.jar'))
                processor.insertInitCodeIntoJarFile(file)
        }
    }

    /**
     * generate code into jar file
     * @param jarFile the jar file which contains LogisticsCenter.class
     * @return
     */
    private File insertInitCodeIntoJarFile(File jarFile) {
        if (jarFile) {
            def optJar = new File(jarFile.getParent(), jarFile.name + ".opt")
            if (optJar.exists())
                optJar.delete()
            def file = new JarFile(jarFile)
            Enumeration enumeration = file.entries()
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(optJar))

            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()
                ZipEntry zipEntry = new ZipEntry(entryName)
                InputStream inputStream = file.getInputStream(jarEntry)
                jarOutputStream.putNextEntry(zipEntry)
                if (ScanSetting.GENERATE_TO_CLASS_FILE_NAME == entryName) {

                    println('RegisterCodeGenerator Insert init code to class >> ' + entryName)

                    def bytes = referHackWhenInit(inputStream)
                    jarOutputStream.write(bytes)
                } else {
                    jarOutputStream.write(IOUtils.toByteArray(inputStream))
                }
                inputStream.close()
                jarOutputStream.closeEntry()
            }
            jarOutputStream.close()
            file.close()

            if (jarFile.exists()) {
                jarFile.delete()
            }
            optJar.renameTo(jarFile)
        }
        return jarFile
    }

    //refer hack class when object init
    private byte[] referHackWhenInit(InputStream inputStream) {
        ClassReader cr = new ClassReader(inputStream)
        ClassWriter cw = new ClassWriter(cr, 0)
        ClassVisitor cv = new MyClassVisitor(Opcodes.ASM5, cw)
        cr.accept(cv, ClassReader.EXPAND_FRAMES)
        return cw.toByteArray()
    }

    class MyClassVisitor extends ClassVisitor {

        MyClassVisitor(int api, ClassVisitor cv) {
            super(api, cv)
        }

        void visit(int version, int access, String name, String signature,
                   String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces)
        }
        @Override
        MethodVisitor visitMethod(int access, String name, String desc,
                                  String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions)
            //generate code into this method
            if (name == ScanSetting.GENERATE_TO_METHOD_NAME) {
                mv = new RouteMethodVisitor(Opcodes.ASM5, mv)
            }
            return mv
        }
    }

    class RouteMethodVisitor extends MethodVisitor {

        RouteMethodVisitor(int api, MethodVisitor mv) {
            super(api, mv)
        }

        @Override
        void visitInsn(int opcode) {
            //generate code before return
            if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)) {
                extension.serviceList.each { it ->

                    def originalService = it
                    def originalServiceImpl = extension.serviceImplMap.get(it)
                    def moduleName = extension.serviceModuleNameMap.get(originalService);
                    def service = it.replaceAll("/", ".")
                    def serviceImpl = null != extension.serviceImplMap.get(it) ? extension.serviceImplMap.get(it).replace("/", ".") : "";

                    println("RegisterCodeGenerator moduleName "+moduleName)
                    println("RegisterCodeGenerator originalService "+originalService)
                    println("RegisterCodeGenerator originalServiceImpl "+originalServiceImpl)
                    println("RegisterCodeGenerator service "+service)
                    println("RegisterCodeGenerator serviceImpl "+serviceImpl)

                    if(null != originalServiceImpl){

                        //此段代码有效----start-----
//                        mv.visitFieldInsn(Opcodes.GETSTATIC,
//                                "java/lang/System",
//                                "out",
//                                "Ljava/io/PrintStream;");
//                        mv.visitLdcInsn("RegisterCode : service" + service + " serviceImpl " + serviceImpl);
//                        // invokes the 'println' method (defined in the PrintStream class)
//                        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
//                                "java/io/PrintStream",
//                                "println",
//                                "(Ljava/lang/String;)V");
                        //此段代码有效----end-----

                        //ARouter impl
//                        name = name.replaceAll("/", ".")
//                        mv.visitLdcInsn(name)//类名
//                        // generate invoke register method into LogisticsCenter.loadRouterMap()
//                        mv.visitMethodInsn(Opcodes.INVOKESTATIC
//                                , ScanSetting.GENERATE_TO_CLASS_NAME
//                                , ScanSetting.REGISTER_METHOD_NAME
//                                , "(Ljava/lang/String;)V"
//                                , false)


                        println("RegisterCodeGenerator serviceClass "+Type.getType('L'+originalService+';'))
                        println("RegisterCodeGenerator serviceImplClass "+Type.getType('L'+originalServiceImpl+';'))

                        println("RegisterCodeGenerator serviceClass 2  "+Type.getObjectType(originalService))
                        println("RegisterCodeGenerator serviceImplClass 2  "+Type.getObjectType(originalServiceImpl))

                        mv.visitLdcInsn(moduleName)
                        mv.visitLdcInsn(Type.getObjectType(originalService))
                        mv.visitLdcInsn(Type.getObjectType(originalServiceImpl))
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                                ScanSetting.GENERATE_TO_CLASS_NAME,
                                "registerService",
                                 "(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/Class;)V",
                                false)


                    }

                }

                //registerByPlugin 设置为true
                mv.visitInsn(Opcodes.ICONST_1);
                mv.visitFieldInsn(Opcodes.PUTSTATIC, ScanSetting.GENERATE_TO_CLASS_NAME, "registerByPlugin", "Z");
            }
            super.visitInsn(opcode)
        }
        @Override
        void visitMaxs(int maxStack, int maxLocals) {
            super.visitMaxs(maxStack + 4, maxLocals)
        }
    }
}
