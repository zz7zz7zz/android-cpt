package com.module.service;

public interface IConsts {

    //----------------------- 字符串 -----------------------
    public static final String PROMPT_COMPONENT_NOT_FOUND = "Serivce 未找到，可能的原因是: \n" +
            "1.没有将组件代码打包进Apk中; \n" +
            "2.已实现对外提供服务类，但没有添加注解,如 @Route(path = \"xxx\", name = \"yyy\");\n" +
            "3.未实现对外提供服务类; \n"+
            "4.本地组件配置/服务器组件配置，未启用组件; \n";

}
