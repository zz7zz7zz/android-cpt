package com.module.plugin.cpt

import com.module.plugin.cpt.bean.CptConfig
import com.module.plugin.cpt.util.ScanSetting
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.apache.commons.io.IOUtils

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class RegisterCodeGeneratorLibrary {

    ScanSetting extension

    private RegisterCodeGeneratorLibrary(ScanSetting extension) {
        this.extension = extension
    }

    static void insertInitCodeTo(ScanSetting registerSetting,String fileName) {
        if (registerSetting != null && !registerSetting.serviceList.isEmpty()) {
            RegisterCodeGeneratorLibrary processor = new RegisterCodeGeneratorLibrary(registerSetting)
            File file = TransformApp.fileContainsInitClass
            if (null != file && file.getName().endsWith('.jar')){

                try {
                    ClassPool.getDefault().insertClassPath(TransformApp.fileContainsInitClass.absolutePath);
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

                    ClassPool.getDefault().insertClassPath(TransformApp.fileContainsInitClass.absolutePath);
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
