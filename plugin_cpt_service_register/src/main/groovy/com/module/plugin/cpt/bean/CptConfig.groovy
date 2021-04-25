package com.module.plugin.cpt.bean;

class CptConfig {

    //1.asm
    String registerToClass
    String registerToClassMethod
    String registerToClassMethodCall
    String registerToClassMethodCallDescriptor
    String registerToClassTag

    //2.javassist
//    String registerToClass
//    String registerToClassMethod
    String registerToClassMethodAdd
    String registerToClassMethodCode

    //other:一下两个参数用于在编译过程中，需要用到引用库的绝对路径
    String arouterPath
    String fragmentPath


    @Override
    public String toString() {
        return "CptConfig{" +
                " registerToClass='" + registerToClass + '\'' +
                ", registerToClassMethod='" + registerToClassMethod + '\'' +
                ", registerToClassMethodCall='" + registerToClassMethodCall + '\'' +
                ", registerToClassMethodCallDescriptor='" + registerToClassMethodCallDescriptor + '\'' +
                ", registerToClassTag='" + registerToClassTag + '\'' +
                ", registerToClassMethodAdd='" + registerToClassMethodAdd + '\'' +
                ", registerToClassMethodCode='" + registerToClassMethodCode + '\'' +
                ", arouterPath='" + arouterPath + '\'' +
                ", fragmentPath='" + fragmentPath + '\'' +
                '}';
    }
}