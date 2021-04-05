package com.module.router.provider;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.router.consts.IConsts;
import com.module.router.consts.IIMConsts;
import com.module.router.consts.IVideoConsts;

public interface IVideoProvider extends IModuleProvider {

    void playVideo(Context context, String msg);

    public static IVideoProvider get(){
        IVideoProvider ret = (IVideoProvider) ARouter.getInstance().build(IVideoConsts.Provider.MAIN).navigation();
        return null != ret ? ret : DEFAULT;
    }

    static final String TAG = "IVideoProvider";
    static IVideoProvider DEFAULT = new IVideoProvider() {
        @Override
        public void playVideo(Context context, String msg) {
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
