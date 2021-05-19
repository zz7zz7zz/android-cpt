package com.module.analysis.core;

import android.content.Context;

public interface IAnalysisService {

    void init(Context context,String channel,String appVersionName,String pkgName);

    void setUserId(Context context,String userId);
}
