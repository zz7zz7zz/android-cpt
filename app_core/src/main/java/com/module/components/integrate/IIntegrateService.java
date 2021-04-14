package com.module.components.integrate;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.module.components.IComponentService;

public interface IIntegrateService extends IComponentService {

    //---------------------------------------------------

    static final String TAG = "IIntegrateSerivce";

    //---------------------------------------------------
    public static final String MODULE   = ":app_integrate";

    public static final String ACTIVITY_MAIN = "/app_integrate/A";

    public static final String FRAGMENT_MAIN = "/app_integrate/F";

    public static final String PROVIDER_MAIN = "/app_integrate/P";
    //---------------------------------------------------

    static IIntegrateService DEFAULT = new IIntegrateService() {
        @Override
        public String getIntegrateTasks() {
            
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
    String getIntegrateTasks();
}
