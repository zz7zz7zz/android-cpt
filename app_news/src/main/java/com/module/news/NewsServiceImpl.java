package com.module.news;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.module.service.news.INewsService;

import java.lang.ref.WeakReference;

public class NewsServiceImpl implements INewsService {

    private static final String TAG = "NewsServiceImpl";
    private Context context;
    private WeakReference<Fragment> fragmentWeakReference;
    private WeakReference<View> viewWeakReference;

    @Override
    public View getComponentTabView(Context context, boolean isCreatedIfNull) {
        View ret = (null != viewWeakReference) ? viewWeakReference.get() : null;
        if(null == ret && isCreatedIfNull){
            ret = LayoutInflater.from(context).inflate(R.layout.tab_item,null);
            ((TextView)(ret.findViewById(R.id.moudle_name))).setText(getComponentName(context));
            ((ImageView)(ret.findViewById(R.id.moudle_icon))).setBackgroundResource(getComponentIconResId(context));
            viewWeakReference = new WeakReference<>(ret);
        }
        return ret;
    }

    @Override
    public Fragment getComponentMainFragment(Context context, boolean isCreatedIfNull) {
        Fragment ret = (null != fragmentWeakReference) ? fragmentWeakReference.get() : null;
        if(null == ret && isCreatedIfNull){
            ret = new NewsMainFragment();
            fragmentWeakReference = new WeakReference<>(ret);
        }
        return ret;
    }

    @Override
    public String getNewsList() {
        Log.v(TAG,"getNewsList");
        return null;
    }

    @Override
    public void startComponentMainActivity(Context context) {
        Intent mIntent = new Intent(context, NewsMainActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    public String getComponentName(Context context) {
        return context.getString(R.string.news_name);
    }

    @Override
    public int getComponentIconResId(Context context) {
        return R.drawable.news_icon_selector;
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
