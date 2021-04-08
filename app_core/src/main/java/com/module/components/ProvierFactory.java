package com.module.components;

import com.alibaba.android.arouter.launcher.ARouter;

public class ProvierFactory {

    public static IModuleProvider create(String name){
        //通过module名字转化成provider的路由名称: ":app_im" - > /im/p
        return (IModuleProvider)ARouter.getInstance().build("/"+name.substring(name.indexOf("_")+1)+"/P").navigation();
    }

}
