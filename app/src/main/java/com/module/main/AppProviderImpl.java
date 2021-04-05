package com.module.main;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.game.GameMainActivity;
import com.module.game.GameMainFragment;
import com.module.router.consts.IAppConsts;
import com.module.router.consts.IGameConsts;
import com.module.router.provider.IAppProvider;
import com.module.router.provider.IGameProvider;

import java.lang.ref.WeakReference;

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
