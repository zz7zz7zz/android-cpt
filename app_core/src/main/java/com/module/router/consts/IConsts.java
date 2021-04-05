package com.module.router.consts;

public interface IConsts {

    //----------------------- 模块申明 -----------------------
    public static final String MODULE_APP       = "app";

    public static final String MODULE_IM       = "im";
    public static final String MODULE_GAME     = "game";
    public static final String MODULE_INTEGERATE = "integrate";
    public static final String MODULE_NEWS     = "news";
    public static final String MODULE_SHOPPING = "shopping";
    public static final String MODULE_VIDEO    = "video";

    //----------------------- 字符串 -----------------------
    public static final String PROMPT_MODULE_NOT_FOUND = "Provider Not Found，可能是: 1.你没有将组件代码打包进来; 2.实现类中没有添加注解,如 @Route(path = \"xxx\", name = \"yyy\"); 3.未实现";

}
