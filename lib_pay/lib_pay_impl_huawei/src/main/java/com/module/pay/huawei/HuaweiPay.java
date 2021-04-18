package com.lib.pay.huawei;


import android.content.Context;
import android.util.Log;

import com.lib.pay.core.service.IPayConsts;
import com.lib.pay.core.service.IPayService;


public class HuaweiPay implements IPayService {

    private static final String TAG = "HuaweiPay";

    @Override
    public void pay(String sku) {
        Log.v(TAG,"payment processing ...");
    }

}
