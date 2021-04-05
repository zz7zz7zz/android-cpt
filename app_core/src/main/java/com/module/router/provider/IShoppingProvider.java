package com.module.router.provider;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.router.consts.IConsts;
import com.module.router.consts.IIMConsts;
import com.module.router.consts.IShoppingConsts;

public interface IShoppingProvider extends IModuleProvider {

    String getGoodInfo();

    public static IShoppingProvider get(){
        IShoppingProvider ret = (IShoppingProvider) ARouter.getInstance().build(IShoppingConsts.Provider.MAIN).navigation();
        return null != ret ? ret : DEFAULT;
    }

    static final String TAG = "IShoppingProvider";
    static IShoppingProvider DEFAULT = new IShoppingProvider() {
        @Override
        public String getGoodInfo() {
            Log.e(TAG, IConsts.PROMPT_MODULE_NOT_FOUND);
            return null;
        }

        @Override
        public void onModuleEnter() {
            Log.e(TAG, IConsts.PROMPT_MODULE_NOT_FOUND);
        }

        @Override
        public void onModuleExit() {
            Log.e(TAG, IConsts.PROMPT_MODULE_NOT_FOUND);
        }

        @Override
        public String getModuleName() {
            Log.e(TAG, IConsts.PROMPT_MODULE_NOT_FOUND);
            return null;
        }

        @Override
        public int getModuleIconResId() {
            Log.e(TAG, IConsts.PROMPT_MODULE_NOT_FOUND);
            return 0;
        }

        @Override
        public View getModuleTabView(Context context, boolean isCreatedIfNull) {
            Log.e(TAG, IConsts.PROMPT_MODULE_NOT_FOUND);
            return null;
        }

        @Override
        public Fragment getModuleMainFragment(boolean isCreatedIfNull) {
            Log.e(TAG, IConsts.PROMPT_MODULE_NOT_FOUND);
            return null;
        }

        @Override
        public void startMainActivity(Context context) {
            Log.e(TAG, IConsts.PROMPT_MODULE_NOT_FOUND);
        }

        @Override
        public void init(Context context) {
            Log.e(TAG, IConsts.PROMPT_MODULE_NOT_FOUND);
        }
    };
}
