package com.module.components.shopping;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.module.components.IComponentService;

public interface IShoppingService extends IComponentService {

    //---------------------------------------------------

    static final String TAG = "IShoppingSerivce";

    //---------------------------------------------------
    public static final String MODULE   = ":app_shopping";

    public static final String ACTIVITY_MAIN = "/app_shopping/A";

    public static final String FRAGMENT_MAIN = "/app_shopping/F";

    public static final String PROVIDER_MAIN = "/app_shopping/P";
    //---------------------------------------------------

    static IShoppingService DEFAULT = new IShoppingService() {
        @Override
        public String getGoodInfo() {
            
            return null;
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
    String getGoodInfo();
}
