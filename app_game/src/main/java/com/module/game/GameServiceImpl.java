package com.module.game;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.module.service.game.IGameService;

import java.lang.ref.WeakReference;

public class GameServiceImpl implements IGameService {

    private static final String TAG = "GameServiceImpl";
    private Context context;
    private WeakReference<Fragment> fragmentWeakReference;
    private WeakReference<View> viewWeakReference;

    @Override
    public View getComponentTabView(Context context, boolean isCreatedIfNull) {
        View ret = (null != viewWeakReference) ? viewWeakReference.get() : null;
        if(null == ret && isCreatedIfNull){
            ret = LayoutInflater.from(context).inflate(R.layout.common_tab,null);
            ((TextView)(ret.findViewById(R.id.common_tab_name))).setText(getComponentName(context));
            ((ImageView)(ret.findViewById(R.id.common_tab_icon))).setBackgroundResource(getComponentIconResId(context));
            viewWeakReference = new WeakReference<>(ret);
        }
        return ret;
    }

    @Override
    public Fragment getComponentMainFragment(Context context,boolean isCreatedIfNull) {
        Fragment ret = (null != fragmentWeakReference) ? fragmentWeakReference.get() : null;
        if(null == ret && isCreatedIfNull){
            ret = new GameMainFragment();
            fragmentWeakReference = new WeakReference<>(ret);
        }
        return ret;
    }

    @Override
    public void startComponentMainActivity(Context context) {
        Intent mIntent = new Intent(context, GameMainActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    public String getComponentName(Context context) {
        return context.getString(R.string.game_name);
    }

    @Override
    public int getComponentIconResId(Context context) {
        return R.drawable.game_icon_selector;
    }

    @Override
    public void startGame(String msg) {
        Log.v(TAG,"startGame " + msg);
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
        if(null != fragmentWeakReference){
            fragmentWeakReference.clear();
            fragmentWeakReference = null;
        }

        if(null != viewWeakReference){
            viewWeakReference.clear();
            viewWeakReference = null;
        }

        //help gc
        System.gc();
    }
}
