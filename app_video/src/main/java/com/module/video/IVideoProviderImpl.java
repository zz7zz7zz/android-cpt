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
    public View getModuleTabView(Context context, boolean isCreatedIfNull) {
        View ret = (null != viewWeakReference) ? viewWeakReference.get() : null;
        if(null == ret && isCreatedIfNull){
            ret = LayoutInflater.from(context).inflate(R.layout.tab_item,null);
            ((TextView)(ret.findViewById(R.id.moudle_name))).setText(getModuleName());
            ((ImageView)(ret.findViewById(R.id.moudle_icon))).setBackgroundResource(getModuleIconResId());
            viewWeakReference = new WeakReference<>(ret);
        }
        return ret;
    }

    @Override
    public Fragment getModuleMainFragment(boolean isCreatedIfNull) {
        Fragment ret = (null != fragmentWeakReference) ? fragmentWeakReference.get() : null;
        if(null == ret && isCreatedIfNull){
            ret = new VideoMainFragment();
            fragmentWeakReference = new WeakReference<>(ret);
        }
        return ret;
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
    public void onModuleEnter() {
        Log.v(TAG,"onModuleEnter ");
    }

    @Override
    public void onModuleExit() {
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
