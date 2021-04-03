package com.module.video;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.router.consts.IVideoConsts;
import com.module.router.provider.IVideoProvider;

@Route(path = IVideoConsts.Provider.MAIN, name = "视频服务")
public class IVideoProviderImpl implements IVideoProvider {

    private static final String TAG = "IVideoProviderImpl";
    private Context context;

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
    public Fragment getMainFragment() {
        return null;
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

}
