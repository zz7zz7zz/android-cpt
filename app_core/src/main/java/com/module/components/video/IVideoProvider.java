package com.module.components.video;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.components.IConsts;
import com.module.components.IComponentsProvider;

public interface IVideoProvider extends IComponentsProvider {

    void playVideo(Context context, String msg);

    public static IVideoProvider get(){
        IVideoProvider ret = (IVideoProvider) ARouter.getInstance().build(IVideoConsts.Provider.MAIN).navigation();
        return null != ret ? ret : DEFAULT;
    }

    static final String TAG = "IVideoProvider";
    static IVideoProvider DEFAULT = new IVideoProvider() {
        @Override
        public void playVideo(Context context, String msg) {
            Log.e(TAG, IConsts.PROMPT_COMPONENT_NOT_FOUND);
        }

        @Override
        public void onComponentEnter() {
            Log.e(TAG, IConsts.PROMPT_COMPONENT_NOT_FOUND);
        }

        @Override
        public void onComponentExit() {
            Log.e(TAG, IConsts.PROMPT_COMPONENT_NOT_FOUND);
        }

        @Override
        public String getComponentName() {
            Log.e(TAG, IConsts.PROMPT_COMPONENT_NOT_FOUND);
            return null;
        }

        @Override
        public int getComponentIconResId() {
            Log.e(TAG, IConsts.PROMPT_COMPONENT_NOT_FOUND);
            return 0;
        }

        @Override
        public View getComponentTabView(Context context, boolean isCreatedIfNull) {
            Log.e(TAG, IConsts.PROMPT_COMPONENT_NOT_FOUND);
            return null;
        }

        @Override
        public Fragment getComponentMainFragment(boolean isCreatedIfNull) {
            Log.e(TAG, IConsts.PROMPT_COMPONENT_NOT_FOUND);
            return null;
        }

        @Override
        public void startComponentMainActivity(Context context) {
            Log.e(TAG, IConsts.PROMPT_COMPONENT_NOT_FOUND);
        }

        @Override
        public void init(Context context) {
            Log.e(TAG, IConsts.PROMPT_COMPONENT_NOT_FOUND);
        }
    };

}
