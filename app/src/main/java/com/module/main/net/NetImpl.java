package com.module.main.net;

import android.os.Handler;
import android.os.Looper;

import com.module.main.ComponentConfig;

import java.util.List;
import java.util.Arrays;

public class NetImpl {

    public interface ICallback<T>{

        void onSuccss(T t);

        void onFailed(int err);

    }
    //模拟请求服务器。向服务器请求组件配置
    public static void getModules(ICallback<List<String>> callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                    ComponentConfig.setServerComponents(Arrays.asList(":app_video",":app_im",":app_shopping"));
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccss(ComponentConfig.getServerComponents());
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
