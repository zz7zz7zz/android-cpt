package com.module.service.shopping;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.module.service.IService;

public interface IShoppingService extends IService {

    //------------------------- 1 -------------------------
    static final String TAG = "IShoppingService";

    //------------------------- 2 -------------------------
    public static final String MODULE   = ":app_shopping";

    public static final String ACTIVITY_MAIN = "/app_shopping/A";

    public static final String FRAGMENT_MAIN = "/app_shopping/F";

    public static final String PROVIDER_MAIN = "/app_shopping/P";

    //------------------------- 3 -------------------------
    static IShoppingService DEFAULT = new IShoppingService() {
        @Override
        public String getGoodInfo() {
            return null;
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
    String getGoodInfo();

}
