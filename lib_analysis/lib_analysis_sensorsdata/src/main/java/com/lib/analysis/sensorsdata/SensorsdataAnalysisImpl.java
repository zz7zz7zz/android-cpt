package com.lib.analysis.sensorsdata;

import android.content.Context;

import com.module.analysis.core.IAnalysisService;
import com.sensorsdata.analytics.android.sdk.SAConfigOptions;
import com.sensorsdata.analytics.android.sdk.SensorsAnalyticsAutoTrackEventType;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

public class SensorsdataAnalysisImpl implements IAnalysisService {
    @Override
    public void init(Context context, String channel, String appVersionName, String pkgName) {
        String SA_SERVER_URL = BuildConfig.sa_server_url;

    // 初始化配置
            SAConfigOptions saConfigOptions = new SAConfigOptions(SA_SERVER_URL);
    // 开启全埋点
            saConfigOptions.setAutoTrackEventType(SensorsAnalyticsAutoTrackEventType.APP_CLICK |
                    SensorsAnalyticsAutoTrackEventType.APP_START |
                    SensorsAnalyticsAutoTrackEventType.APP_END |
                    SensorsAnalyticsAutoTrackEventType.APP_VIEW_SCREEN)
                    //开启 Log
                    .enableLog(BuildConfig.DEBUG);

        saConfigOptions.enableTrackAppCrash();//开启 Crash 信息的自动采集
        saConfigOptions.enableAutoAddChannelCallbackEvent(true); //追踪并进行渠道匹配和回传,开启新渠道功能

        //如果用户没有同意隐私协议，需要
//        if(){
//            saConfigOptions.disableDataCollect();
//        }

    /**
     * 其他配置，如开启可视化全埋点
     */
    // 需要在主线程初始化神策 SDK
            SensorsDataAPI.startWithConfigOptions(context, saConfigOptions);
    }

    @Override
    public void agreePrivacyPolicy(Context context, String channel, String appVersionName, String pkgName) {
        SensorsDataAPI.sharedInstance().enableDataCollect();
    }

    @Override
    public void bindUserId(Context context, String userId) {
        SensorsDataAPI.sharedInstance().login(userId);
    }

    @Override
    public void unBindUserId(Context context, String userId) {
        SensorsDataAPI.sharedInstance().login("");
    }
}
