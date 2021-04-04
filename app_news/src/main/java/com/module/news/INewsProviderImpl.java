package com.module.news;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.router.consts.IIntegrateConsts;
import com.module.router.consts.INewsConsts;
import com.module.router.provider.IIMProvider;
import com.module.router.provider.INewsProvider;

@Route(path = INewsConsts.Provider.MAIN, name = "新闻服务")
public class INewsProviderImpl implements INewsProvider {

    private static final String TAG = "INewsProviderImpl";
    private Context context;

    @Override
    public String getNewsList() {
        Log.v(TAG,"getNewsList");
        return null;
    }

    @Override
    public View getTabView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item,null);
        ((TextView)(view.findViewById(R.id.moudle_name))).setText(getModuleName());
        ((ImageView)(view.findViewById(R.id.moudle_icon))).setBackgroundResource(getModuleIconResId());
        return view;
    }

    @Override
    public Fragment getMainFragment() {
        return new NewsMainFragment();
    }

    @Override
    public void startMainActivity(Context context) {
        Intent mIntent = new Intent(context, NewsMainActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    public String getModuleName() {
        return context.getString(R.string.news_name);
    }

    @Override
    public int getModuleIconResId() {
        return R.drawable.news_icon_selector;
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

}
