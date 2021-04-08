package com.module.main;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.components.app.IAppConsts;
import com.module.components.app.IAppProvider;

/**
 * 可能用的到也可能用不到
 */
@Route(path = IAppConsts.Provider.MAIN, name = "App壳")
public class AppProviderImpl implements IAppProvider {

    private static final String TAG = "AppProviderImpl";
    private Context context;

    @Override
    public View getModuleTabView(Context context, boolean isCreatedIfNull) {
        return null;
    }

    @Override
    public Fragment getModuleMainFragment(boolean isCreatedIfNull) {
        return null;
    }

    @Override
    public void startMainActivity(Context context) {

    }

    @Override
    public String getModuleName() {
        return context.getString(R.string.app_name);
    }

    @Override
    public int getModuleIconResId() {
        return 0;
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void onModuleEnter() {
        Log.v(TAG,"onModuleEnter ");
    }

    @Override
    public void onModuleExit() {
        Log.v(TAG,"onModuleExit ");
    }
}
