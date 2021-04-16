package com.module.pay.huawei;


import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.pay.service.IPayConsts;
import com.module.pay.service.IPayService;


@Route(path = IPayConsts.PAY_HUAWEI, name = IPayConsts.PAY_HUAWEI)
public class HuaweiPay implements IPayService {

    private static final String TAG = "HuaweiPay";

    @Override
    public void pay(String sku) {
        Log.v(TAG,"payment processing ...");
    }

    @Override
    public void init(Context context) {

    }
}
