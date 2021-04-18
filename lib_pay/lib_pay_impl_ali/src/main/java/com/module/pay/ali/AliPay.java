package com.lib.pay.ali;


import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lib.pay.core.service.IPayConsts;
import com.lib.pay.core.service.IPayService;


public class AliPay implements IPayService {

    private static final String TAG = "AliPay";

    @Override
    public void pay(String sku) {
        Log.v(TAG,"payment processing ...");
    }

    @Override
    public void init(Context context) {

    }
}
