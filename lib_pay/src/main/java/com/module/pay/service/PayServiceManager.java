package com.module.pay.service;

import android.text.TextUtils;

public final class PayServiceManager {

    public static IPayService getService(String component){
        return !TextUtils.isEmpty(component) ? (IPayService) com.alibaba.android.arouter.launcher.ARouter.getInstance().build(component).navigation() : null;
    }
}
