package com.module.core.service.im;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.module.core.service.IService;

public interface IIMService extends IService {

    //------------------------- 1 -------------------------
    static final String TAG = "IIMService";

    //------------------------- 2 -------------------------
    public static final String MODULE   = ":app_im";

    public static final String ACTIVITY_MAIN = "/app_im/A";

    public static final String FRAGMENT_MAIN = "/app_im/F";

    public static final String PROVIDER_MAIN = "/app_im/P";

    //------------------------- 3 -------------------------
    static IIMService DEFAULT = new IIMService() {
        @Override
        public String getMessage() {
            return null;
        }

        @Override
        public void sendMessage(String msg) {

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

    //------------------------- 4.对位提供的方法在以下进行定义 -------------------------
    String getMessage();

    void sendMessage(String msg);

}