package com.module.analysis.core;

import android.content.Context;

public interface IAnalysisService {

    void init(Context context,String channel,String appVersionName,String pkgName);

    void agreePrivacyPolicy(Context context,String channel,String appVersionName,String pkgName);

    void bindUserId(Context context, String userId);

    void unBindUserId(Context context, String userId);

    void close();

    //------------------------------------------------------------
    void e(final String tag, final String msg);

    void w(final String tag, final String msg);

    void i(final String tag, final String msg);

    void d(final String tag, final String msg);

    void v(final String tag, final String msg);
    //------------------------------------------------------------

    public static class AdapterAnalysisService  implements IAnalysisService{
        @Override
        public void init(Context context, String channel, String appVersionName, String pkgName) {

        }

        @Override
        public void agreePrivacyPolicy(Context context, String channel, String appVersionName, String pkgName) {

        }

        @Override
        public void bindUserId(Context context, String userId) {

        }

        @Override
        public void unBindUserId(Context context, String userId) {

        }

        @Override
        public void close() {

        }

        @Override
        public void e(String tag, String msg) {

        }

        @Override
        public void w(String tag, String msg) {

        }

        @Override
        public void i(String tag, String msg) {

        }

        @Override
        public void d(String tag, String msg) {

        }

        @Override
        public void v(String tag, String msg) {

        }
    }
}
