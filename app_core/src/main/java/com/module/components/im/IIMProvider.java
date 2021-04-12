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
            
            return null;
        }

        @Override
        public void sendMessage(String msg) {
            
        }

        @Override
        public void onComponentEnter() {
            
        }

        @Override
        public void onComponentExit() {
            
        }

        @Override
        public String getComponentName() {
            
            return null;
        }

        @Override
        public int getComponentIconResId() {
            
            return 0;
        }

        @Override
        public View getComponentTabView(Context context, boolean isCreatedIfNull) {
            
            return null;
        }

        @Override
        public Fragment getComponentMainFragment(boolean isCreatedIfNull) {
            
            return null;
        }

        @Override
        public void startComponentMainActivity(Context context) {
            
        }

        @Override
        public void init(Context context) {
            
        }
    };
}
