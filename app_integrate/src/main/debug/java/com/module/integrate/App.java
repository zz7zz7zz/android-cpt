package com.module.integrate;

import com.module.BaseApplication;

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean isComponentCodeIn(String component) {
        return false;
    }

    @Override
    public boolean isComponentConfigured(String component) {
        return false;
    }

    @Override
    public String getVersionName() {
        return null;
    }

    @Override
    public int getVersionCode() {
        return 0;
    }

    @Override
    public String getChannel() {
        return null;
    }

    @Override
    public String getProduct() {
        return null;
    }
}
