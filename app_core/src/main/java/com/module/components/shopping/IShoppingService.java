package com.module.components.shopping;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.module.components.IComponentService;

public interface IShoppingService extends IComponentService {

    //---------------------------------------------------

    static final String TAG = "IShoppingProvider";

    //---------------------------------------------------
    public static final String MODULE   = "shopping";

    public static final String ACTIVITY_MAIN = "/"+MODULE+"/A";

    public static final String FRAGMENT_MAIN = "/"+MODULE+"/F";

    public static final String PROVIDER_MAIN = "/"+MODULE+"/P";
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
