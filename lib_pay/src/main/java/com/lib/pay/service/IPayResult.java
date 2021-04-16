package com.lib.pay.service;

public interface IPayResult {

    void onPaySuccess();

    void onPayFailed(int errCode, String errMessage);
}
