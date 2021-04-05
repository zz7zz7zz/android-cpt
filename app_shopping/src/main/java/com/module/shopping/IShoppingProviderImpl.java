package com.module.shopping;

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
import com.module.router.consts.IShoppingConsts;
import com.module.router.provider.IShoppingProvider;

import java.lang.ref.WeakReference;

@Route(path = IShoppingConsts.Provider.MAIN, name = "购物服务")
public class IShoppingProviderImpl implements IShoppingProvider {

    private static final String TAG = "IShoppingProviderImpl";
    private Context context;
    private WeakReference<Fragment> fragmentWeakReference;
    private WeakReference<View> viewWeakReference;

    @Override
    public View getTabView(Context context) {
        if(null == viewWeakReference || null == viewWeakReference.get()){
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
            fragmentWeakReference = new WeakReference<>(new ShoppingMainFragment());
        }
        return fragmentWeakReference.get();
    }
    @Override
    public String getGoodInfo() {
        Log.v(TAG,"getGoodInfo");
        return null;
    }

    @Override
    public void startMainActivity(Context context) {
        Intent mIntent = new Intent(context, ShoppingMainActivity.class);
        context.startActivity(mIntent);
    }

    @Override
    public String getModuleName() {
        return context.getString(R.string.shopping_name);
    }

    @Override
    public int getModuleIconResId() {
        return R.drawable.shopping_icon_selector;
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
