package com.lib.pay.google;


import android.content.Context;
import android.util.Log;

import com.lib.pay.core.service.IPayConsts;
import com.lib.pay.core.service.IPayService;


public class GooglePay implements IPayService {

    private static final String TAG = "GooglePay";

    @Override
    public void pay(String sku) {
        Log.v(TAG,"payment processing ...");
    }

}