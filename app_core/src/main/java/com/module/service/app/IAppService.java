package com.module.service.app;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.module.service.IService;

public interface IAppService extends IService {

    //---------------------------------------------------
    static final String TAG = "IAppSerivce";

    //---------------------------------------------------
    public static final String MODULE   = ":app";

    public static final String ACTIVITY_MAIN = "/app/A";

    public static final String FRAGMENT_MAIN = "/app/F";

    public static final String PROVIDER_MAIN = "/app/P";
    //---------------------------------------------------

    static IAppService DEFAULT = new IAppService() {
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
}
