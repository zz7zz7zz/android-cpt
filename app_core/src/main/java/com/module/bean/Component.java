package com.module.bean;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.router.provider.IModuleProvider;

public class Component {

    public static IModuleProvider getMainProvider(String name){
        //通过module名字转化成provider的路由名称: ":app_im" - > /im/p
        return (IModuleProvider)ARouter.getInstance().build("/"+name.substring(name.indexOf("_")+1)+"/P").navigation();
    }

}
