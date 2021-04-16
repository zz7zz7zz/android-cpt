package com.module.pay.google;


import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.pay.service.IPayConsts;
import com.module.pay.service.IPayService;


@Route(path = IPayConsts.PAY_GOOGLE, name = IPayConsts.PAY_GOOGLE)
public class GooglePay implements IPayService {

    private static final String TAG = "GooglePay";

    @Override
    public void pay(String sku) {
        Log.v(TAG,"payment processing ...");
    }

    @Override
    public void init(Context context) {

    }
}
