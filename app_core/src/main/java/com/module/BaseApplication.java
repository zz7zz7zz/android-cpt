package com.module;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.module.service.ServiceManager;

public abstract class BaseApplication extends Application {

    private static BaseApplication INS = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        INS = this;
        ServiceManager.init();
        Initializer.init(this);
    }


    public static Application getInstance(){
        return INS;
    }

    //--------------------- 组件代码是否包含了 ---------------------

    /**
     * 组件是否启用（代码包含组件 并且 本地/服务器配置了开启组件）
     * @param component
     * @return
     */
    public boolean isComponentEnable(String component){
        return isComponentCodeIn(component) && isComponentConfigured(component);
    }

    /**
     * 打包时，apk是否包含了组件的代码
     */
    public abstract boolean isComponentCodeIn(String component);

    /**
     * 服务器/本地配置是否配置开启相应的组件
     */
    public abstract boolean isComponentConfigured(String component);



    //--------------------- 应用信息相关 ---------------------
    public abstract String getVersionName();

    public abstract int getVersionCode();

    public abstract String getChannel();

    public abstract String getProduct();

}
