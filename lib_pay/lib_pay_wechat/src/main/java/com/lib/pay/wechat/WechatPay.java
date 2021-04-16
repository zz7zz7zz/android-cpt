package com.lib.pay.wechat;


import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.pay.service.IPayConsts;
import com.lib.pay.service.IPayService;


@Route(path = IPayConsts.PAY_WECHAT, name = IPayConsts.PAY_WECHAT)
public class WechatPay implements IPayService {

    private static final String TAG = "WechatPay";

    @Override
    public void pay(String sku) {
        Log.v(TAG,"payment processing ...");
    }

    @Override
    public void init(Context context) {

    }
}
