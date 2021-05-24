package com.lib.net.http;

public interface IServiceCallBack<T> {

    T onAsyncProcessData(byte[] data);

    void onSuccess(T t);

    void onFailed(int errCode ,String errMessage);
}
