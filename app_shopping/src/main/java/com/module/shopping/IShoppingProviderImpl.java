package com.module.shopping;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.router.consts.IShoppingConsts;
import com.module.router.provider.IShoppingProvider;

@Route(path = IShoppingConsts.Provider.MAIN, name = "购物服务")
public class IShoppingProviderImpl implements IShoppingProvider {

    private static final String TAG = "IShoppingProviderImpl";
    private Context context;

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
    public Fragment getMainFragment() {
        return null;
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


}
