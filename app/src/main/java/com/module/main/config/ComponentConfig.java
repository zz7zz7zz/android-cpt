package com.module.main.config;

import java.util.Arrays;
import java.util.List;

/**
 * 组件配置
 */
public class ComponentConfig {

    //1.本地代码自带的组件(代码打进来的组件)
    public static final List<String> allComponents = Arrays.asList(com.module.main.BuildConfig.modules);

    //2.1组件配置来自：本地
    public static final List<String> localComponents = Arrays.asList(":app_im",":app_video",":app_game",":app_integrate",":app_news");

    //2.2组件配置来自：服务器
    private static List<String> serverComponents;


    public static List<String> getServerComponents() {
        return null != serverComponents ? serverComponents : localComponents;
    }

    public static void setServerComponents(List<String> serverComponents) {
        ComponentConfig.serverComponents = serverComponents;
    }
}
