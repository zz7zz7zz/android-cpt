package com.module.game;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.router.consts.IGameConsts;
import com.module.router.provider.IGameProvider;

@Route(path = IGameConsts.Provider.MAIN, name = "游戏服务")
public class IGameProviderImpl implements IGameProvider {

    private static final String TAG = "IVideoProviderImpl";
    private Context context;


    @Override
    public void startGame(String msg) {
        Log.v(TAG,"startGame " + msg);
    }

    @Override
    public void startMainActivity(Context context) {
        Intent mIntent = new Intent(context, GameMainActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    public Fragment getMainFragment() {
        return null;
    }

    @Override
    public String getModuleName() {
        return context.getString(R.string.game_name);
    }

    @Override
    public int getModuleIconResId() {
        return R.drawable.game_icon_selector;
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }


}
