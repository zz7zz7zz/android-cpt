package com.lib.pay.core.service;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

public final class PayServiceManager {

    private static final HashMap<String, PayServiceInfo> nameService = new HashMap<>();//元数据集合,组件名为key

    private static final HashMap<String, Object> sMap = new HashMap<>();//实例对象

    static {
        loadServiceToMap();

        Log.v("PayServiceManager","------------- static start ------------");
        for (PayServiceInfo info: nameService.values()) {
            Log.v("PayServiceManager",info.toString());
        }
        Log.v("PayServiceManager","------------- static end ------------");
    }

    private static void loadServiceToMap(){
        registerService("ali","com.lib.pay.ali.AliPay");
        registerService("wechat","com.lib.pay.wechat.WechatPay");
        registerService("huawei","com.lib.pay.huawei.HuaweiPay");
        registerService("google","com.lib.pay.google.GooglePay");
    }

    private static void registerService(String name,String serviceImplName){
        PayServiceInfo PayServiceInfo = new PayServiceInfo(name,serviceImplName);
        nameService.put(name,PayServiceInfo);
    }

    public static IPayService getService(String component){
        try{
            IPayService ret = (IPayService)sMap.get(component);
            if(null == ret){

                PayServiceInfo PayServiceInfo = nameService.get(component);
                if(null != PayServiceInfo){
                    Class serviceImplClass = PayServiceInfo.getServiceImpl();
                    if(null != serviceImplClass){
                        ret = (IPayService)serviceImplClass.getConstructor().newInstance();
                    }
                }
            }

            if(null != ret){
                sMap.put(component,ret);
            }
            return ret;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
