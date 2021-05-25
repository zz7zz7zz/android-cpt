package com.lib.net.http;

import android.os.Build;

import com.lib.net.http.config.IHttpConfig;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.wire.WireConverterFactory;

public final class Http {

    private IHttpConfig httpConfig;
    private OkHttpClient  okHttpClient;
    private Retrofit      retrofit;

    private static volatile Http INS;

    private Http(){

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

    public void init(IHttpConfig config){
        httpConfig = config;
        okHttpClient = getDefaultOkHttpClient();
        retrofit = getDefaultRetrofit(httpConfig.getBaseUrl(),okHttpClient);
    }

    private OkHttpClient getDefaultOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(httpConfig.getConnectTimeout(), TimeUnit.SECONDS);
        builder.readTimeout(httpConfig.getReadTimeout(), TimeUnit.SECONDS);
        builder.writeTimeout(httpConfig.getWriteTimeout(), TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(false); //错误重连

        ArrayList<Interceptor> interceptors= httpConfig.getInterceptors();
        int size = (null != interceptors) ? interceptors.size() : 0;
        for (int i = 0; i < size; i++) {
            builder.addInterceptor(interceptors.get(i)); //添加自定义拦截器
        }

        if(null != httpConfig.getHostnameVerifier()){
            builder.hostnameVerifier(httpConfig.getHostnameVerifier());
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //5.0以下信任所有证书
//            SSLTrustManager sslTrustManager = new SSLTrustManager();
//            builder.sslSocketFactory(new SSLSocketFactoryCompat(sslTrustManager), sslTrustManager);
            X509TrustManager trustManager = httpConfig.getSSLTrustManager();
            SSLSocketFactory sslSocketFactory = httpConfig.getSSLSocketFactory(trustManager);
            builder.sslSocketFactory(sslSocketFactory,trustManager);
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

    private Retrofit getRetrofit() {
        return retrofit;
    }

    public <T> T create(Class<? extends T> clazz){
        Retrofit retrofit = Http.getInstance().getRetrofit();
        if(null != retrofit){
            return getInstance().getRetrofit().create(clazz);
        }else{
            throw new IllegalStateException("You Should Init Http before request, Like Http.getInstance().init(config); in Application onCreate()");
        }
    }
}
