package com.module.video;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.router.consts.IVideoConsts;
import com.module.router.provider.IVideoProvider;

import java.lang.ref.WeakReference;

@Route(path = IVideoConsts.Provider.MAIN, name = "视频服务")
public class IVideoProviderImpl implements IVideoProvider {

    private static final String TAG = "IVideoProviderImpl";
    private Context context;
    private WeakReference<Fragment> fragmentWeakReference;
    private WeakReference<View> viewWeakReference;

    @Override
    public View getTabView(Context context) {
        if(null == fragmentWeakReference || null == fragmentWeakReference.get()){
            View view = LayoutInflater.from(context).inflate(R.layout.tab_item,null);
            ((TextView)(view.findViewById(R.id.moudle_name))).setText(getModuleName());
            ((ImageView)(view.findViewById(R.id.moudle_icon))).setBackgroundResource(getModuleIconResId());
            viewWeakReference = new WeakReference<>(view);
        }
        return viewWeakReference.get();
    }

    @Override
    public Fragment getMainFragment() {
        if(null == fragmentWeakReference || null == fragmentWeakReference.get()){
            fragmentWeakReference = new WeakReference<>(new VideoMainFragment());
        }
        return fragmentWeakReference.get();
    }
    @Override
    public void playVideo(Context context,String msg) {
        Log.v(TAG,"playVideo " + msg);
    }


    @Override
    public void startMainActivity(Context context) {
        Intent mIntent = new Intent(context, VideoMainActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    public String getModuleName() {
        return context.getString(R.string.video_name);
    }

    @Override
    public int getModuleIconResId() {
        return R.drawable.video_icon_selector;
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void onEnter() {
        Log.v(TAG,"onEnter ");
    }

    @Override
    public void onExit() {
        Log.v(TAG,"onExit ");
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
