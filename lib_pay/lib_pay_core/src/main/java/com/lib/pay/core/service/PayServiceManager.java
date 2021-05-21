package com.lib.pay.core.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public final class PayServiceManager {

    private static final String TAG = "PayServiceManager";
    private static final HashMap<String, PayServiceInfo> nameService = new HashMap<>();//元数据集合,组件名为key

    private static boolean registerByPlugin = false;
    private static final HashMap<String, Object> sMap = new HashMap<>();//实例对象
    private static ArrayList<String> blackList = null;

    static {
        loadServiceToMap();

        Log.v(TAG,"registerByPlugin " + registerByPlugin + " nameService.size " + nameService.size());
        if(!registerByPlugin || nameService.size() == 0){
            degradeLoadServiceToMap();
        }

        Log.v(TAG,"------------- static start ------------");
        for (PayServiceInfo info: nameService.values()) {
            Log.v(TAG,info.toString());
        }
        Log.v(TAG,"------------- static end ------------");
    }

    private static void loadServiceToMap(){
        registerByPlugin = false;
    }

    //没有使用插件的情况下，才用反射的方法
    private static void degradeLoadServiceToMap(){
        registerService(IPayConsts.PAY_ALI,"com.lib.pay.ali.AliPay");
        registerService(IPayConsts.PAY_WECHAT,"com.lib.pay.wechat.WechatPay");
        registerService(IPayConsts.PAY_HUAWEI,"com.lib.pay.huawei.HuaweiPay");
        registerService(IPayConsts.PAY_GOOGLE,"com.lib.pay.google.GooglePay");
    }

    private static void registerService(String name,String serviceImplName){
        PayServiceInfo PayServiceInfo = new PayServiceInfo(name,serviceImplName);
        nameService.put(name,PayServiceInfo);
    }

    public static IPayService getService(String component){
        if (null != blackList && blackList.contains(component)) {
            return null;
        }

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
            if(null == blackList){
                blackList = new ArrayList<>();
            }
            blackList.add(component);
        }
        return null;
    }
}
