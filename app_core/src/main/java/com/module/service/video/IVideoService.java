package com.module.service.video;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.module.service.IService;

public interface IVideoService extends IService {

    //---------------------------------------------------

    static final String TAG = "IVideoSerivce";

    //---------------------------------------------------
    public static final String MODULE   = ":app_video";

    public static final String ACTIVITY_MAIN = "/app_video/A";

    public static final String FRAGMENT_MAIN = "/app_video/F";

    public static final String PROVIDER_MAIN = "/app_video/P";
    //---------------------------------------------------
    static IVideoService DEFAULT = new IVideoService() {
        @Override
        public void playVideo(Context context, String msg) {

        }

        @Override
        public void onComponentEnter(Context context) {

        }

        @Override
        public void onComponentExit(Context context) {

        }

        @Override
        public String getComponentName(Context context) {
            return null;
        }

        @Override
        public int getComponentIconResId(Context context) {
            return 0;
        }

        @Override
        public View getComponentTabView(Context context, boolean isCreatedIfNull) {
            return null;
        }

        @Override
        public Fragment getComponentMainFragment(Context context, boolean isCreatedIfNull) {
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
