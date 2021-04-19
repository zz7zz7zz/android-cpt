package com.module.plugin.cpt

import com.module.plugin.cpt.bean.CptConfig
import com.module.plugin.cpt.util.ScanSetting
import javassist.CtMember
import org.apache.commons.io.IOUtils
import org.gradle.api.Project
import org.objectweb.asm.*

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

class RegisterCodeGenerator2 {

    ScanSetting extension

    private RegisterCodeGenerator2(ScanSetting extension) {
        this.extension = extension
    }

    //1.往方法里添加方法体,空则插入，不为空则不插入
//    static void insertInitCodeToApp(ScanSetting registerSetting,String fileName) {
//        if (registerSetting != null && !registerSetting.serviceList.isEmpty()) {
//            RegisterCodeGenerator2 processor = new RegisterCodeGenerator2(registerSetting)
//            File file = CptTransform.fileContainsInitClass
//            if (null != file && file.getName().endsWith('.jar')){
//
//                try {
//                    ClassPool.getDefault().insertClassPath(CptTransform.fileContainsInitClass.absolutePath);
//                    def className = registerSetting.GENERATE_TO_CLASS_FILE_NAME2.replace("\\",".").replace("/",".").replace(".class","")
//                    CtClass c2 = ClassPool.getDefault().getCtClass(className);
//                    if (c2.isFrozen()){
//                        c2.defrost()
//                    }
//
//                    CtMethod[] ms = c2.getDeclaredMethods();
//                    for (CtMethod c : ms) {
//                        System.out.println(c.getName());
//                        CtClass[] ps = c.getParameterTypes();
//                        for (CtClass cx : ps) {
//                            System.out.println(" ctClass getName \t" + cx.getName());
//                        }
//
//                        if(c.isEmpty()){//增量编译使用插件时，每次都会插入，所以这里判断一下；但是当如果有插件增加或者删除时，需要clean一下才行，但是这种情况下也就几次，也能接受
//                            if (c.getName().equals(registerSetting.REGISTER_METHOD_NAME2) && ps.length == 0) {
//
//                                StringBuilder sb = new StringBuilder();
//                                registerSetting.serviceImplMap.each { k,v ->
//                                    def cls1  = k.replaceAll("/", ".")
//                                    def cls2  = v.replaceAll("/", ".")
//                                    sb.append(String.format("com.module.service.ServiceManager.registerService(%s.MODULE,%s.class,%s.class);\n",cls1,cls1,cls2))
//                                }
////                              sb.append("registerByPlugin = true;")
//                                c.insertBefore(sb.toString())
//                                println("code:\n"+sb.toString())
//                                break;
//                            }
//                        }
//                    }
////                    c2.writeFile(fileName)
//                    c2.writeFile()
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//    }

    //2.整个方法体替换
//    static void insertInitCodeToApp(ScanSetting registerSetting,String fileName) {
//        if (registerSetting != null && !registerSetting.serviceList.isEmpty()) {
//            RegisterCodeGenerator2 processor = new RegisterCodeGenerator2(registerSetting)
//            File file = CptTransform.fileContainsInitClass
//            if (null != file && file.getName().endsWith('.jar')){
//
//                try {
//                    ClassPool.getDefault().insertClassPath(CptTransform.fileContainsInitClass.absolutePath);
//                    def className = registerSetting.GENERATE_TO_CLASS_FILE_NAME2.replace("\\",".").replace("/",".").replace(".class","")
//                    CtClass c2 = ClassPool.getDefault().getCtClass(className);
//                    if (c2.isFrozen()){
//                        c2.defrost()
//                    }
//
//                    CtMethod[] ms = c2.getDeclaredMethods();
//                    for (CtMethod c : ms) {
//                        System.out.println(c.getName());
//                        CtClass[] ps = c.getParameterTypes();
//                        for (CtClass cx : ps) {
//                            System.out.println(" ctClass getName \t" + cx.getName());
//                        }
//
//                        if (c.getName().equals(registerSetting.REGISTER_METHOD_NAME2) && ps.length == 0) {
//
//                            StringBuilder sb = new StringBuilder();
//                            sb.append("{")
//                            registerSetting.serviceImplMap.each { k,v ->
//                                def cls1  = k.replaceAll("/", ".")
//                                def cls2  = v.replaceAll("/", ".")
//                                sb.append(String.format("com.module.service.ServiceManager.registerService(%s.MODULE,%s.class,%s.class);\n",cls1,cls1,cls2))
//                            }
////                            sb.append("registerByPlugin = true;")
//                            sb.append("}")
//                            c.setBody(sb.toString())//setBody需要{}
//                            println("code:\n"+sb.toString())
//                            break;
//                        }
//
//
//                    }
//                    c2.writeFile(fileName)
////                    c2.writeFile()
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }
//    }


    //3.动态生成方法，动态插入
    static void insertInitCodeToApp(ScanSetting registerSetting, String fileName, CptConfig cptConfig) {
        if (registerSetting != null && !registerSetting.serviceList.isEmpty()) {
            RegisterCodeGenerator2 processor = new RegisterCodeGenerator2(registerSetting)
            File file = CptTransform.fileContainsInitClass
            if (null != file && file.getName().endsWith('.jar')){

                try {
                    ClassPool.getDefault().insertClassPath(CptTransform.fileContainsInitClass.absolutePath);
                    def className = cptConfig.applicationName.replace("\\",".").replace("/",".").replace(".class","")
                    CtClass c2 = ClassPool.getDefault().getCtClass(className);
                    if (c2.isFrozen()){
                        c2.defrost()
                    }

                    CtMethod[] ms = c2.getDeclaredMethods();

                    //删除旧的方法
                    String addMethodName = registerSetting.ADD_METHOD_NAME
                    for (CtMethod c : ms) {
                        if (c.getName().equals(addMethodName)) {
                            c2.removeMethod(c)
                            println("remove method " + addMethodName + " success ")
                        }
                    }

                    //添加新的方法
                    for (CtMethod c : ms) {
                        println(c.getName());
                        CtClass[] ps = c.getParameterTypes();
                        for (CtClass cx : ps) {
                            System.out.println(" ctClass getName \t" + cx.getName());
                        }

                        if (c.getName().equals(registerSetting.REGISTER_METHOD_NAME3) && ps.length == 0) {

                            //增加方法
                            StringBuilder sb = new StringBuilder();
                            sb.append("private void "+addMethodName+"() {")
                            registerSetting.serviceImplMap.each { k,v ->
                                def cls1  = k.replaceAll("/", ".")
                                def cls2  = v.replaceAll("/", ".")
                                sb.append(String.format("com.module.service.ServiceManager.registerService(%s.MODULE,%s.class,%s.class);\n",cls1,cls1,cls2))
                            }
//                            sb.append("registerByPlugin = true;")
                            sb.append("}")
                            c2.addMethod(CtMethod.make(sb.toString(), c2))
                            println("code:\n"+sb.toString())

                            //增加调用
                            sb.delete(0,sb.length())
                            sb.append(addMethodName+"();")
                            c.insertBefore(sb.toString())
                            println("code2 :\n"+sb.toString())
                            break;
                        }
                    }
                    c2.writeFile(fileName)
//                    c2.writeFile()
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

    static void insertInitCodeTo(ScanSetting registerSetting,String fileName) {
        if (registerSetting != null && !registerSetting.serviceList.isEmpty()) {
            RegisterCodeGenerator2 processor = new RegisterCodeGenerator2(registerSetting)
            File file = CptTransform.fileContainsInitClass
            if (null != file && file.getName().endsWith('.jar')){

                try {
                    ClassPool.getDefault().insertClassPath(CptTransform.fileContainsInitClass.absolutePath);
                    def className = registerSetting.GENERATE_TO_CLASS_FILE_NAME.replace("\\",".").replace("/",".").replace(".class","")
                    CtClass c2 = ClassPool.getDefault().getCtClass(className);
                    if (c2.isFrozen()){
                        c2.defrost()
                    }

                    CtMethod[] ms = c2.getDeclaredMethods();
                    for (CtMethod c : ms) {
                        System.out.println(c.getName());
                        CtClass[] ps = c.getParameterTypes();
                        for (CtClass cx : ps) {
                            System.out.println(" ctClass getName \t" + cx.getName());
                        }
                        if (c.getName().equals(registerSetting.REGISTER_METHOD_NAME) && ps.length == 0) {

                            StringBuilder sb = new StringBuilder();
                            registerSetting.serviceImplMap.each { k,v ->
                                def cls1  = k.replaceAll("/", ".")
                                def cls2  = v.replaceAll("/", ".")
                                sb.append(String.format("registerService(%s.MODULE,%s.class,%s.class);\n",cls1,cls1,cls2))
                            }
                            sb.append("registerByPlugin = true;")
println("code:\n"+sb.toString())
                            c.insertAfter(sb.toString())
                        }
                    }
                    c2.writeFile(fileName)
                    c2.writeFile()
//                    c2.toClass()
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //替换文件
//                insertInitCodeIntoJarFile(file,fileName+"/com/module/service/ServiceManager.class",registerSetting)
            }

        }
    }

    static File insertInitCodeIntoJarFile(File jarFile,String replaceFileName,ScanSetting registerSetting) {
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

                    println('Insert init code to class >> ' + entryName + " start ")

//                    def bytes = referHackWhenInit(inputStream)
//                    jarOutputStream.write(bytes)

//                    InputStream fixInputStream = new FileInputStream(replaceFileName);
//                    jarOutputStream.write(IOUtils.toByteArray(fixInputStream))
//                    fixInputStream.close()

                    ClassPool.getDefault().insertClassPath(CptTransform.fileContainsInitClass.absolutePath);
                    def className = registerSetting.GENERATE_TO_CLASS_FILE_NAME.replace("\\",".").replace("/",".").replace(".class","")
                    CtClass c2 = ClassPool.getDefault().getCtClass(className);
                    def bytes = c2.toBytecode()

                    println("new code :\n" + c2.getDeclaredMethod("loadServiceToMap"))

                    jarOutputStream.write(bytes)

                    println('Insert init code to class >> ' + entryName + " end ")

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

}
