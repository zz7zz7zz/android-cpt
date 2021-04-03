package com.module.integrate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.router.consts.IIntegrateConsts;
import com.module.router.provider.IIntegrateProvider;

@Route(path = IIntegrateConsts.Provider.MAIN, name = "积分服务")
public class IIntegrateProviderImpl implements IIntegrateProvider {

    private static final String TAG = "IIntegrateProviderImpl";
    private Context context;



    @Override
    public void startMainActivity(Context context) {
        Intent mIntent = new Intent(context, IntegrateMainActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    public Fragment getMainFragment() {
        return null;
    }

    @Override
    public String getIntegrateTasks() {
        Log.v(TAG,"getIntegrateTasks ");
        return null;
    }

    @Override
    public String getModuleName() {
        return context.getString(R.string.integrate_name);
    }

    @Override
    public int getModuleIconResId() {
        return R.drawable.integrate_icon_selector;
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

}
