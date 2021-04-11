package com.module.plugin.cpt

import javassist.*
import com.module.plugin.cpt.util.ScanSetting
import javassist.CtClass;

class CptCodeGenerator {

    static def pool = ClassPool.default

    static void insertCodeTo(ScanSetting setting,String fileName){

        def className = ScanSetting.GENERATE_TO_CLASS_FILE_NAME.replace("\\",".").replace("/",".").replace(".class","")
//println("insertCodeTo className " + className)
//println("insertCodeTo fileName " + fileName)
        CtClass ctClass = pool.getCtClass(className)
        if (ctClass.isFrozen()){
            ctClass.defrost()
        }

        if(ctClass.isInterface()){
            return
        }

//println("ctClass "+ctClass)
//println("className "+ctClass.getName())
        CtMethod[] ctMethods = ctClass.getDeclaredMethods()
        for (method in ctMethods){

            //空函数或者抽象函数
            if(method.isEmpty()){
                continue
            }

            //Native方法
            if (Modifier.isNative(method.getModifiers())){
                continue
            }

//println("method " + method.getName())
            if(ScanSetting.GENERATE_TO_METHOD_NAME.equals(method.getName())){
                boolean isFrist = true;
                StringBuilder sb = new StringBuilder();
                setting.classList.each { name ->
                    name = name.replaceAll("/", ".")
                    if(!isFrist){
                        sb.append("else ")
                    }
                    sb.append(String.format("if(clazz.equals(%s.class)){\n" +
                              "     return /*(T)*/ %s.get();\n" +
                            "}",name,name))
                    isFrist = false;
                }
println(sb.toString())
                method.insertBefore(sb.toString())
            }

        }

        ctClass.writeFile(fileName)
        ctClass.detach()

    }
}
