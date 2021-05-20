package com.module.analysis.core;

import android.util.Log;

import java.util.HashMap;

public final class AnalysisServiceManager {

    private static final String TAG = "AnalysisServiceManager";
    private static final HashMap<String, AnalysisServiceInfo> nameService = new HashMap<>();//元数据集合,组件名为key

    private static boolean registerByPlugin = false;
    private static final HashMap<String, Object> sMap = new HashMap<>();//实例对象

    static {
        loadServiceToMap();

        Log.v(TAG,"registerByPlugin " + registerByPlugin + " nameService.size " + nameService.size());
        if(!registerByPlugin || nameService.size() == 0){
            degradeLoadServiceToMap();
        }

        Log.v(TAG,"------------- static start ------------");
        for (AnalysisServiceInfo info: nameService.values()) {
            Log.v(TAG,info.toString());
        }
        Log.v(TAG,"------------- static end ------------");
    }

    private static void loadServiceToMap(){
        registerByPlugin = false;
    }

    //没有使用插件的情况下，才用反射的方法
    private static void degradeLoadServiceToMap(){
        registerService(IAnalysisConsts.ANALYSIS_BUGLY,"com.lib.analysis.bugly.BuglyAnalysisImpl");
        registerService(IAnalysisConsts.ANALYSIS_UMENG,"com.lib.analysis.umeng.UmengAnalysisImpl");
        registerService(IAnalysisConsts.ANALYSIS_SENSORSDATA,"com.lib.analysis.sensorsdata.SensorsdataAnalysisImpl");
        registerService(IAnalysisConsts.ANALYSIS_LOCAL,"com.lib.analysis.local.LocalAnalysisImpl");
        registerService(IAnalysisConsts.ANALYSIS_SERVER,"com.lib.analysis.server.ServerAnalysisImpl");
    }

    private static void registerService(String name,String serviceImplName){
        AnalysisServiceInfo PayServiceInfo = new AnalysisServiceInfo(name,serviceImplName);
        nameService.put(name,PayServiceInfo);
    }

    public static IAnalysisService getService(String component){
        try{
            IAnalysisService ret = (IAnalysisService)sMap.get(component);
            if(null == ret){
                AnalysisServiceInfo PayServiceInfo = nameService.get(component);
                if(null != PayServiceInfo){
                    Class serviceImplClass = PayServiceInfo.getServiceImpl();
                    if(null != serviceImplClass){
                        ret = (IAnalysisService)serviceImplClass.getConstructor().newInstance();
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
