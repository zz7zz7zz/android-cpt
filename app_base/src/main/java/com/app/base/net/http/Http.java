package com.app.base.net.http;

import android.os.Build;

import java.util.concurrent.TimeUnit;

import cn.douyuu.base.biz.network.SSLTrustManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.wire.WireConverterFactory;

public class Http {

    private OkHttpConfig okHttpConfig;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    private static volatile Http INS;

    private Http(){
        okHttpConfig = new OkHttpConfig();
        okHttpClient = getDefaultOkHttpClient();
        retrofit = getDefaultRetrofit(okHttpConfig.getBaseUrl(),okHttpClient);
    }

    public static Http getInstance() {
        if(null == INS){
            synchronized (Http.class){
                if(null == INS){
                    INS = new Http();
                }
            }
        }
        return INS;
    }

    private OkHttpClient getDefaultOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(20, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(false); //错误重连
        Interceptor[] interceptors= okHttpConfig.getInterceptors();
        if(null != interceptors && interceptors.length>0){
            for (int i = 0; i < interceptors.length; i++) {
                builder.addInterceptor(interceptors[i]); //添加自定义拦截器
            }
        }
        builder.hostnameVerifier((hostname, session) -> true);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //5.0以下信任所有证书
            SSLTrustManager sslTrustManager = new SSLTrustManager();
            builder.sslSocketFactory(new SSLSocketFactoryCompat(sslTrustManager), sslTrustManager);
        }
        return builder.build();
    }

    private Retrofit getDefaultRetrofit(String baseUrl,okhttp3.Call.Factory factory){
        Retrofit.Builder mBuilder = new Retrofit.Builder();
        mBuilder.baseUrl(baseUrl);
        mBuilder.callFactory(factory);
        mBuilder.addConverterFactory(WireConverterFactory.create());
        mBuilder.addConverterFactory(GsonConverterFactory.create());
        return mBuilder.build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
