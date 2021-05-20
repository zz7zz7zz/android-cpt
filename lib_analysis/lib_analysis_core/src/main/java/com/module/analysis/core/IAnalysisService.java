package com.module.analysis.core;

import android.content.Context;

public interface IAnalysisService {

    void init(Context context,String channel,String appVersionName,String pkgName);

    void agreePrivacyPolicy(Context context,String channel,String appVersionName,String pkgName);

    void bindUserId(Context context, String userId);

    void unBindUserId(Context context, String userId);
}
