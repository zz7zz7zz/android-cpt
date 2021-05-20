package com.lib.analysis.umeng;

import android.content.Context;
import android.util.Log;

import com.module.analysis.core.IAnalysisService;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

import org.json.JSONException;

public class UmengAnalysisImpl implements IAnalysisService {

    @Override
    public void init(Context context, String channel, String appVersionName, String pkgName) {

        UMConfigure.setLogEnabled(BuildConfig.DEBUG);

        UMConfigure.preInit(context,BuildConfig.umengAppKey,channel);

        //如果用户没有同意隐私协议
//        if(){
//                    UMConfigure.init(context,BuildConfig.umengAppKey,channel,UMConfigure.DEVICE_TYPE_PHONE,null);
//        }

        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

//        //------------------------测试是否接入成功 start --------------------------
//        try {
//            String[] deviceInfo = getTestDeviceInfo(context);
//            org.json.JSONObject json = new org.json.JSONObject();
//            json.put("device_id", deviceInfo[0]);
//            json.put("mac", deviceInfo[1]);
//            Log.v("umeng",json.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        MobclickAgent.onEvent(context,"integration_testing");
//        //------------------------测试是否接入成功 end --------------------------
    }

    @Override
    public void agreePrivacyPolicy(Context context, String channel, String appVersionName, String pkgName) {
        UMConfigure.init(context,BuildConfig.umengAppKey,channel,UMConfigure.DEVICE_TYPE_PHONE,null);
    }


    @Override
    public void bindUserId(Context context, String userId) {
        MobclickAgent.onProfileSignIn(userId);
    }

    @Override
    public void unBindUserId(Context context, String userId) {
        MobclickAgent.onProfileSignOff();
    }

    @Override
    public void close(Context context) {

    }

    public static String[] getTestDeviceInfo(Context context){
        String[] deviceInfo = new String[2];
        try {
            if(context != null){
                deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
                deviceInfo[1] = DeviceConfig.getMac(context);
            }
        } catch (Exception e){
        }
        return deviceInfo;
    }
}
