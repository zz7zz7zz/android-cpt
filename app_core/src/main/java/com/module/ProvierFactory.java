package com.module;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.router.provider.IModuleProvider;

public class ProvierFactory {

    public static IModuleProvider create(String name){
        //通过module名字转化成provider的路由名称: ":app_im" - > /im/p
        return (IModuleProvider)ARouter.getInstance().build("/"+name.substring(name.indexOf("_")+1)+"/P").navigation();
    }

}
