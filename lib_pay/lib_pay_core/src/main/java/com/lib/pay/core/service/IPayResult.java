package com.lib.pay.core.service;

public interface IPayResult {

    void onPaySuccess();

    void onPayFailed(int errCode, String errMessage);
}
