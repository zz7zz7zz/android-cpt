package com.app.base.net.http;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpConfig {


    public String getBaseUrl() {
//        return "http://test-mqapp.duomiyuanplay.com";
        return "http://fiddler2.com";
    }

    public Interceptor[] getInterceptors() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
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
        return new Interceptor[] {interceptor,commonHeaderParam};
    }
}
