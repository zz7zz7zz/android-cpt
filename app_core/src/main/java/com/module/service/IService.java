package com.module.service;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

public interface IService {

    void init(Context context);

    //---------------- 组件进入，组件退出 相关 ----------------
    void onComponentEnter(Context context);

    void onComponentExit(Context context);

    //---------------- 基础信息相关 ----------------
    String getComponentName(Context context);

    int    getComponentIconResId(Context context);

    //---------------- UI 相关 ----------------
    View getComponentTabView(Context context, boolean isCreatedIfNull);

    Fragment getComponentMainFragment(Context context,boolean isCreatedIfNull);

    void startComponentMainActivity(Context context);

}
