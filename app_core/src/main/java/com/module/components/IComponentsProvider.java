package com.module.components;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IComponentsProvider extends IProvider {

    //---------------- 组件进入，组件退出 相关 ----------------
    void onComponentEnter();

    void onComponentExit();

    //---------------- 基础信息相关 ----------------
    String getComponentName();

    int    getComponentIconResId();

    //---------------- UI 相关 ----------------
    View getComponentTabView(Context context, boolean isCreatedIfNull);

    Fragment getComponentMainFragment(boolean isCreatedIfNull);

    void startComponentMainActivity(Context context);

}
