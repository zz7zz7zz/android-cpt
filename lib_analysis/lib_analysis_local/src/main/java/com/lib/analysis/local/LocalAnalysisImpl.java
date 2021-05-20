package com.lib.analysis.local;

import android.content.Context;

import com.module.analysis.core.IAnalysisService;

public class LocalAnalysisImpl implements IAnalysisService {

    private String userId;
    @Override
    public void init(Context context, String channel, String appVersionName, String pkgName) {

    }

    @Override
    public void agreePrivacyPolicy(Context context, String channel, String appVersionName, String pkgName) {

    }

    @Override
    public void bindUserId(Context context, String userId) {
        this.userId = userId;
    }

    @Override
    public void unBindUserId(Context context, String userId) {
        this.userId = userId;
    }

    @Override
    public void close(Context context) {

    }

}
