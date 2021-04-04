package com.module;

import android.app.Application;

import com.module.init.Initializer;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Initializer.init(this);
    }
}
