package com.module.core.net;

import com.lib.net.http.Http;
import com.lib.net.http.config.DefaultHttpConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class NetHttp {

    private static boolean isInitialized = false;
    private static void init(){
        Http.getInstance().init(new DefaultHttpConfig() {

            @Override
            public String getBaseUrl() {
                return super.getBaseUrl();
            }

            @Override
            public ArrayList<Interceptor> getInterceptors() {
                ArrayList<Interceptor> ret= super.getInterceptors();
                Interceptor commonHeaderParam = new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        request = request.newBuilder()
                                .addHeader("Charset","UTF-8")
                                .addHeader("Content-Type","application/octet-stream")
                                .addHeader("Accept","application/octet-stream")
                                .build();
                        return chain.proceed(request);
                    }
                };
                ret.add(commonHeaderParam);
                return ret;
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
