package com.module;

import android.app.Application;

public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Initializer.init(this);


    }

    //--------------------- 组件代码是否包含了 ---------------------
    /**
     * 打包时，apk是否包含了组件的代码
     */
    public abstract boolean isComponentCodeIn(String component);

    /**
     * 服务器/本地配置是否配置显示相应的组件
     */
    public abstract boolean isComponentConfigured(String component);



    //--------------------- 应用信息相关 ---------------------
    public abstract String getVersionName();

    public abstract int getVersionCode();

}
