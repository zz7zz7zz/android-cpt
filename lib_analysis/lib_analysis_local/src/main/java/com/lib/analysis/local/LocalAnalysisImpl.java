package com.lib.analysis.local;

import android.content.Context;

import com.module.analysis.core.IAnalysisService;
import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

public class LocalAnalysisImpl extends IAnalysisService.AdapterAnalysisService {

    private String userId;

    @Override
    public void init(Context context, String channel, String appVersionName, String pkgName) {

        System.loadLibrary("c++_shared");
        System.loadLibrary("marsxlog");

//        final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
//        final String logPath = SDCARD + "/marssample/log";
        final String logPath = context.getFilesDir() + "/xlog";

        // this is necessary, or may cash for SIGBUS
        final String cachePath = context.getFilesDir() + "/xlogCache";

        //init xlog
//        Xlog.XLogConfig logConfig = new Xlog.XLogConfig();
//        logConfig.mode = Xlog.AppednerModeAsync;
//        logConfig.logdir = logPath;
//        logConfig.nameprefix = "douyu.log";
//        logConfig.pubkey = "";
//        logConfig.compressmode = Xlog.ZLIB_MODE;
//        logConfig.compresslevel = 0;
//        logConfig.cachedir = cachePath;
//        logConfig.cachedays = 0;
//        if (BuildConfig.DEBUG) {
//            logConfig.level = Xlog.LEVEL_VERBOSE;
//            Log.setConsoleLogOpen(true);
//        } else {
//            logConfig.level = Xlog.LEVEL_INFO;
//            Log.setConsoleLogOpen(false);
//        }
//
//        Log.setLogImp(new Xlog());
        //init xlog
        Xlog xlog = new Xlog();
        Log.setLogImp(xlog);
        if (BuildConfig.DEBUG) {
            Log.setConsoleLogOpen(true);
            Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, "douyu.log", 0);
        } else {
            Log.setConsoleLogOpen(false);
            Log.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, cachePath, logPath, "douyu.log", 0);
        }

//        Log.v("LocalAnalysisImpl","I am first log ");
//        Log.v("LocalAnalysisImpl","I am first log 2 ");
//        Log.i("LocalAnalysisImpl","I am first log 3");
//        Log.d("LocalAnalysisImpl","I am first log 4");
//        Log.e("LocalAnalysisImpl","I am first log 5");
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
    public void close() {
        Log.appenderClose();
    }

    @Override
    public void e(String tag, String msg) {
        Log.e(tag,msg);
    }

    @Override
    public void w(String tag, String msg) {
        Log.w(tag,msg);
    }

    @Override
    public void i(String tag, String msg) {
        Log.i(tag,msg);
    }

    @Override
    public void d(String tag, String msg) {
        Log.d(tag,msg);
    }

    @Override
    public void v(String tag, String msg) {
        Log.v(tag,msg);
    }

}
