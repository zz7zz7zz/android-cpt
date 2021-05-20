package com.lib.analysis.bugly;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.module.analysis.core.IAnalysisService;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Handler;

public class BuglyAnalysisImpl implements IAnalysisService {

    @Override
    public void init(Context context,String channel,String appVersionName,String pkgName) {

        CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG);

//        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppChannel(channel);  //设置渠道
        strategy.setAppVersion(appVersionName);      //App的版本
        strategy.setAppPackageName(pkgName);  //App的包名

        // 初始化Bugly
        CrashReport.initCrashReport(context, BuildConfig.buglyAppId,BuildConfig.DEBUG, strategy);

        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        // CrashReport.initCrashReport(context, strategy);

        //------------------------测试是否接入成功 start --------------------------
//        new android.os.Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setUserId(context,"8888");
//                CrashReport.testJavaCrash();
//            }
//        },5000);
        //------------------------测试是否接入成功 end --------------------------
    }

    @Override
    public void agreePrivacyPolicy(Context context, String channel, String appVersionName, String pkgName) {

    }

    @Override
    public void bindUserId(Context context, String userId) {
        CrashReport.setUserId(userId);
        CrashReport.putUserData(context, "userId", userId);
    }

    @Override
    public void unBindUserId(Context context, String userId) {
        CrashReport.setUserId("");
        CrashReport.putUserData(context, "userId", "");
    }

    @Override
    public void close(Context context) {

    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
