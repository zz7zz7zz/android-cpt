package com.module.pay;

import com.module.pay.service.IPayResult;
import com.module.pay.service.IPayService;
import com.module.pay.service.PayOrder;
import com.module.pay.service.PayServiceManager;

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
        IPayService service = PayServiceManager.getService(payType);
        if(null != service){
            service.pay(payOrder.sku);
            listener.onPaySuccess();
        }else{
            listener.onPayFailed(-1,"Service 未找到");
        }
    }

}
