package com.module.components;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IModuleProvider extends IProvider {

    //---------------- 组件进入，组件退出 相关 ----------------
    void     onModuleEnter();

    void     onModuleExit();

    //---------------- 基础 相关 ----------------
    String   getModuleName();

    int      getModuleIconResId();

    //---------------- UI 相关 ----------------
    View     getModuleTabView(Context context, boolean isCreatedIfNull);

    Fragment getModuleMainFragment(boolean isCreatedIfNull);

    void     startMainActivity(Context context);

}
