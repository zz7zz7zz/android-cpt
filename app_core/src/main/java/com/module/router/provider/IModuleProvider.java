package com.module.router.provider;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IModuleProvider extends IProvider {

    //---------------- 销毁 相关 ----------------
    void destroy();

    //---------------- 基础 相关 ----------------
    String getModuleName();

    int getModuleIconResId();

    //---------------- UI 相关 ----------------
    View getTabView(Context context);

    Fragment getMainFragment();

    void startMainActivity(Context context);

}
