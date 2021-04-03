package com.module.router.provider;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IModuleProvider extends IProvider {

    String getModuleName();

    int getModuleIconResId();

    void startMainActivity(Context context);

    Fragment getMainFragment();

}
