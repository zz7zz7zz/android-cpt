package com.module.components.video;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.components.IConsts;
import com.module.components.IComponentsProvider;

public interface IVideoProvider extends IComponentsProvider {

    //---------------------------------------------------

    static final String TAG = "IVideoProvider";

    //---------------------------------------------------
    public static final String MODULE   = "video";

    public static final String ACTIVITY_MAIN = "/"+MODULE+"/A";

    public static final String FRAGMENT_MAIN = "/"+MODULE+"/F";

    public static final String PROVIDER_MAIN = "/"+MODULE+"/P";
    //---------------------------------------------------
    static IVideoProvider DEFAULT = new IVideoProvider() {
        @Override
        public void playVideo(Context context, String msg) {
            
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

    //---------------------------------------------------
    void playVideo(Context context, String msg);
}
