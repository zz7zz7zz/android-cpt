package com.module.analysis;

import android.content.Context;

import com.module.analysis.core.AnalysisServiceManager;
import com.module.analysis.core.IAnalysisConsts;
import com.module.analysis.core.IAnalysisService;

public class Analysis {

    private static volatile Analysis INS;

    private Analysis(){
    }

    public static Analysis getInstance() {
        if(null == INS){
            synchronized (Analysis.class){
                if(null == INS){
                    INS = new Analysis();
                }
            }
        }
        return INS;
    }

    public void init(Context context, String channel, String appVersionName, String pkgName) {
        IAnalysisService service = AnalysisServiceManager.getService(IAnalysisConsts.ANALYSIS_BUGLY);
        if(null != service){
            service.init(context,channel,appVersionName,pkgName);
        }

        service = AnalysisServiceManager.getService(IAnalysisConsts.ANALYSIS_UMENG);
        if(null != service){
            service.init(context,channel,appVersionName,pkgName);
        }

        service = AnalysisServiceManager.getService(IAnalysisConsts.ANALYSIS_SENSORSDATA);
        if(null != service){
            service.init(context,channel,appVersionName,pkgName);
        }
    }

    public void bindUserId(Context context,String userId){
        IAnalysisService service = AnalysisServiceManager.getService(IAnalysisConsts.ANALYSIS_BUGLY);
        if(null != service){
            service.bindUserId(context,userId);
        }

        service = AnalysisServiceManager.getService(IAnalysisConsts.ANALYSIS_UMENG);
        if(null != service){
            service.bindUserId(context,userId);
        }

        service = AnalysisServiceManager.getService(IAnalysisConsts.ANALYSIS_SENSORSDATA);
        if(null != service){
            service.bindUserId(context,userId);
        }
    }

    public void unBindUser(Context context,String userId){

    }
}
