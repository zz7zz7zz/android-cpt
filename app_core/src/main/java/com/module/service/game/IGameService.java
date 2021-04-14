package com.module.service.game;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.module.service.IService;

public interface IGameService extends IService {

    //---------------------------------------------------
    static final String TAG = "IGameSerivce";

    //---------------------------------------------------
    public static final String MODULE   = ":app_game";

    public static final String ACTIVITY_MAIN = "/app_game/A";

    public static final String FRAGMENT_MAIN = "/app_game/F";

    public static final String PROVIDER_MAIN = "/app_game/P";
    //---------------------------------------------------

    static IGameService DEFAULT = new IGameService() {
        @Override
        public void startGame(String msg) {
            
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
    void startGame(String msg);

      //方法一：使用匿名对象，每个对象手动添加代码

      //方法二：使用动态代理打日志
//    public static IGameService get(){
//        IGameService ret = (IGameService) ARouter.getInstance().build(IGameConsts.Provider.MAIN).navigation();
//        return null != ret ? ret : DefaultComponentProxy.get(TAG,DEFAULT);
//    }
//
//    static final String TAG = "IGameService";
//    static IGameService DEFAULT = new IGameService() {
//        @Override
//        public void startGame(String msg) {
//
//        }
//
//        @Override
//        public void onComponentEnter() {
//
//        }
//
//        @Override
//        public void onComponentExit() {
//
//        }
//
//        @Override
//        public String getComponentName() {
//
//            return null;
//        }
//
//        @Override
//        public int getComponentIconResId() {
//
//            return 0;
//        }
//
//        @Override
//        public View getComponentTabView(Context context, boolean isCreatedIfNull) {
//
//            return null;
//        }
//
//        @Override
//        public Fragment getComponentMainFragment(boolean isCreatedIfNull) {
//
//            return null;
//        }
//
//        @Override
//        public void startComponentMainActivity(Context context) {
//
//        }
//
//        @Override
//        public void init(Context context) {
//
//        }
//    };

    //方法三：使用javassist 对匿名类进行处理

}
