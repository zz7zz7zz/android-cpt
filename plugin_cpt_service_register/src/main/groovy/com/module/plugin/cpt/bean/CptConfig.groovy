package com.module.plugin.cpt.bean;


class CptConfig {

    String applicationName

    //1.asm
    String regiserToClass
    String regiserToClassMethod

    //2.javassist
//    String regiserToClass
//    String regiserToClassMethod
    String regiserToClassMethodAdd
    String regiserToClassMethodCode

    //other:一下两个参数用于在编译过程中，需要用到引用库的绝对路径
    String arouterPath
    String fragmentPath


    @Override
    public String toString() {
        return "CptConfig{" +
                "applicationName='" + applicationName + '\'' +
                ", regiserToClass='" + regiserToClass + '\'' +
                ", regiserToClassMethod='" + regiserToClassMethod + '\'' +
                ", regiserToClassMethodAdd='" + regiserToClassMethodAdd + '\'' +
                ", regiserToClassMethodCode='" + regiserToClassMethodCode + '\'' +
                ", arouterPath='" + arouterPath + '\'' +
                ", fragmentPath='" + fragmentPath + '\'' +
                '}';
    }
}