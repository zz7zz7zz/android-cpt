package com.module.main;

import android.text.TextUtils;

import com.module.BaseApplication;
import com.module.main.config.ComponentConfig;

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        onInitComponentService();
    }

    //do not remove this method, it will auto generator registerService by plugin
    //In particular, do not add code manually
    private void onInitComponentService(){

    }

    @Override
    public boolean isComponentCodeIn(String component) {
        if(!TextUtils.isEmpty(component)){
            for (String cpt : BuildConfig.modules){
                if(component.equals(cpt)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isComponentConfigured(String component) {
        if(!TextUtils.isEmpty(component)){
            for (String cpt : ComponentConfig.getServerComponents()){
                if(component.equals(cpt)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    @Override
    public String getChannel() {
        return BuildConfig.FLAVOR_channel;
    }

    @Override
    public String getProduct() {
        return BuildConfig.FLAVOR_product;
    }
}
