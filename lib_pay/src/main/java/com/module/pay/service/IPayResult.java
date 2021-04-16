package com.module.pay.service;

public interface IPayResult {

    void onPaySuccess();

    void onPayFailed(int errCode, String errMessage);
}
