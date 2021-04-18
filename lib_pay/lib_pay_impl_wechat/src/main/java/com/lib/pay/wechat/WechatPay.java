package com.lib.pay.wechat;


import android.content.Context;
import android.util.Log;

import com.lib.pay.core.service.IPayConsts;
import com.lib.pay.core.service.IPayService;


public class WechatPay implements IPayService {

    private static final String TAG = "WechatPay";

    @Override
    public void pay(String sku) {
        Log.v(TAG,"payment processing ...");
    }

}
