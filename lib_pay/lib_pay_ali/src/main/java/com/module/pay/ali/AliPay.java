package com.module.pay.ali;


import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.pay.service.IPayConsts;
import com.module.pay.service.IPayService;


@Route(path = IPayConsts.PAY_ALI, name = IPayConsts.PAY_ALI)
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
