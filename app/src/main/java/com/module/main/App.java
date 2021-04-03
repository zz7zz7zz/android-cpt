package com.module.main;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.init.Initializer;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Initializer.init(this);
    }
}
