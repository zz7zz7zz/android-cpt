package com.module.main;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.components.app.IAppService;

/**
 * 可能用的到也可能用不到
 */
@Route(path = IAppService.PROVIDER_MAIN, name = IAppService.MODULE)
public class AppProviderImpl implements IAppService {

    private static final String TAG = "AppProviderImpl";
    private Context context;

    @Override
    public View getComponentTabView(Context context, boolean isCreatedIfNull) {
        return null;
    }

    @Override
    public Fragment getComponentMainFragment(boolean isCreatedIfNull) {
        return null;
    }

    @Override
    public void startComponentMainActivity(Context context) {

    }

    @Override
    public String getComponentName() {
        return context.getString(R.string.app_name);
    }

    @Override
    public int getComponentIconResId() {
        return 0;
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void onComponentEnter() {
        Log.v(TAG,"onModuleEnter ");
    }

    @Override
    public void onComponentExit() {
        Log.v(TAG,"onModuleExit ");
    }
}
