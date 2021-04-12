package com.module.components.game;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.components.IConsts;
import com.module.components.IComponentsProvider;

public interface IGameProvider extends IComponentsProvider {

    void startGame(String msg);

    public static IGameProvider get(){
        IGameProvider ret = (IGameProvider) ARouter.getInstance().build(IGameConsts.Provider.MAIN).navigation();
        return null != ret ? ret : DEFAULT;
    }

    static final String TAG = "IGameProvider";
    static IGameProvider DEFAULT = new IGameProvider() {
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

      //方法一：使用匿名对象，每个对象手动添加代码

      //方法二：使用动态代理打日志
//    public static IGameProvider get(){
//        IGameProvider ret = (IGameProvider) ARouter.getInstance().build(IGameConsts.Provider.MAIN).navigation();
//        return null != ret ? ret : DefaultComponentProxy.get(TAG,DEFAULT);
//    }
//
//    static final String TAG = "IGameProvider";
//    static IGameProvider DEFAULT = new IGameProvider() {
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
