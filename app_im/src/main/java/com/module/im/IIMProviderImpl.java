package com.module.im;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.router.consts.IIMConsts;
import com.module.router.provider.IIMProvider;

import java.lang.ref.WeakReference;

@Route(path = IIMConsts.Provider.MAIN, name = "聊天服务")
public class IIMProviderImpl implements IIMProvider {

    private static final String TAG = "IVideoProviderImpl";
    private Context context;

    @Override
    public View getTabView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item,null);
        ((TextView)(view.findViewById(R.id.moudle_name))).setText(getModuleName());
        ((ImageView)(view.findViewById(R.id.moudle_icon))).setBackgroundResource(getModuleIconResId());
        return view;
    }

    @Override
    public Fragment getMainFragment() {
        return new IMMainFragment();
    }

    @Override
    public void startMainActivity(Context context) {
        Intent mIntent = new Intent(context, IMMainActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    public String getMessage() {
        return "I am From Im Module";
    }

    @Override
    public void sendMessage(String msg) {
        Log.v(TAG,"sendMessage " + msg);
    }

    @Override
    public String getModuleName() {
        return context.getString(R.string.im_name);
    }

    @Override
    public int getModuleIconResId() {
        return R.drawable.im_icon_selector;
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

}
