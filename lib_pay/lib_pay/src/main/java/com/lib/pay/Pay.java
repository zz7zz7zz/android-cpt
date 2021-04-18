package com.lib.pay;

import android.text.TextUtils;

import com.lib.pay.core.service.IPayResult;
import com.lib.pay.core.service.IPayService;
import com.lib.pay.core.service.PayOrder;

public class Pay {

    private static volatile Pay INS;

    private Pay(){
    }

    public static Pay getInstance() {
        if(null == INS){
            synchronized (Pay.class){
                if(null == INS){
                    INS = new Pay();
                }
            }
        }
        return INS;
    }

    public void pay(String payType, PayOrder payOrder, IPayResult listener) {
        IPayService service = getService(payType);
        if(null != service){
            service.pay(payOrder.sku);
            listener.onPaySuccess();
        }else{
            listener.onPayFailed(-1,"Service 未找到");
        }
    }

    public static IPayService getService(String component){
        return !TextUtils.isEmpty(component) ? (IPayService) com.alibaba.android.arouter.launcher.ARouter.getInstance().build(component).navigation() : null;
    }

}
