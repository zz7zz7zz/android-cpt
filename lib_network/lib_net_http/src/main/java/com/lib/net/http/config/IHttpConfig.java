package com.lib.net.http.config;

import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;

public interface IHttpConfig {

    String getBaseUrl();

    int getConnectTimeout();

    int getReadTimeout();

    int getWriteTimeout();

    ArrayList<Interceptor> getInterceptors();

    HostnameVerifier getHostnameVerifier();

    X509TrustManager getSSLTrustManager();

    SSLSocketFactory getSSLSocketFactory(X509TrustManager sslTrustManager);
}
