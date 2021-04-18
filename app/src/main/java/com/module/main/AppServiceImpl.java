package com.module.main;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.service.app.IAppService;

/**
 * 可能用的到也可能用不到
 */
public class AppServiceImpl implements IAppService {

    private static final String TAG = "AppServiceImpl";
    private Context context;

    @Override
    public View getComponentTabView(Context context, boolean isCreatedIfNull) {
        return null;
    }

    @Override
    public Fragment getComponentMainFragment(Context context, boolean isCreatedIfNull) {
        return null;
    }

    @Override
    public void startComponentMainActivity(Context context) {

    }

    @Override
    public String getComponentName(Context context) {
        return context.getString(R.string.app_name);
    }

    @Override
    public int getComponentIconResId(Context context) {
        return 0;
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void onComponentEnter(Context context) {
        Log.v(TAG,"onModuleEnter ");
    }

    @Override
    public void onComponentExit(Context context) {
        Log.v(TAG,"onModuleExit ");
    }
}
