package com.module.components.im;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.components.IConsts;
import com.module.components.IComponentsProvider;

public interface IIMProvider extends IComponentsProvider {

    String getMessage();

    void sendMessage(String msg);

    public static IIMProvider get(){
        IIMProvider ret = (IIMProvider) ARouter.getInstance().build(IIMConsts.Provider.MAIN).navigation();
        return null != ret ? ret : DEFAULT;
    }

    static final String TAG = "IIMProvider";
    static IIMProvider DEFAULT = new IIMProvider() {
        @Override
        public String getMessage() {
            Log.e(TAG, IConsts.PROMPT_MODULE_NOT_FOUND);
            return null;
        }

        @Override
        public void sendMessage(String msg) {
            Log.e(TAG, IConsts.PROMPT_MODULE_NOT_FOUND);
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
