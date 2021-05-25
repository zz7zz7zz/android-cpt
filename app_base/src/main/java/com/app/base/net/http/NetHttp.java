package com.app.base.net.http;

import com.lib.net.http.Http;
import com.lib.net.http.config.DefaultHttpConfig;

public class NetHttp {

    private static boolean isInitialized = false;
    public static void init(){
        Http.getInstance().init(new DefaultHttpConfig() {
            @Override
            public String getBaseUrl() {
                return super.getBaseUrl();
            }
        });
        isInitialized = true;
    }

    public static<T> T create(Class<? extends T> clazz){
        if(!isInitialized){
            init();
        }
        return Http.getInstance().create(clazz);
    }

}
