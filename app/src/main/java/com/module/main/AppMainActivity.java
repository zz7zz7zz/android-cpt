package com.module.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.components.ProvierFactory;
import com.module.main.net.NetImpl;
import com.module.components.IModuleProvider;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class AppMainActivity extends AppCompatActivity implements View.OnClickListener{

    //本地代码自带的组件(代码打进来的组件)
    private static final List<String> allModules = Arrays.asList(com.module.main.BuildConfig.modules);

    //组件配置来自：本地
    private List<String> localModules = Arrays.asList(":app_im",":app_video",":app_game",":app_integrate",":app_news");

    private ArrayList<IModuleProvider> providers = new ArrayList<>();
    private IModuleProvider currentProvider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);

        initAllModels(allModules);

        findViewById(R.id.module_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProvider(localModules);
                initView();
            }
        });
        findViewById(R.id.module_remote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NetImpl.getModules(new NetImpl.ICallback<List<String>>() {
                    @Override
                    public void onSuccss(List<String> strings) {
                        initProvider(strings);
                        initView();
                    }

                    @Override
                    public void onFailed(int err) {

                    }
                });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //组件释放操作
        for (int i = 0;i<providers.size();i++){
            IModuleProvider provider = providers.get(i);
            if(null != provider){
                provider.onModuleExit();
            }
        }
        providers = null;
        currentProvider = null;
    }

    private void initAllModels(List<String> modules){

        ArrayList<IModuleProvider> allProvider = new ArrayList<>();
        for (int i = 0;i<modules.size();i++){
            IModuleProvider provider = ProvierFactory.create(modules.get(i));
            if(null != provider){
                allProvider.add(provider);
            }
        }

        LinearLayout app_modules_all = (LinearLayout) findViewById(R.id.app_modules_all);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels/allProvider.size();

        for (int i = 0;i<allProvider.size();i++){

            final IModuleProvider provider = allProvider.get(i);

            View view = LayoutInflater.from(this).inflate(R.layout.app_module_item,app_modules_all,false);
            ((TextView)(view.findViewById(R.id.moudle_name))).setText(provider.getModuleName());
            ((ImageView)(view.findViewById(R.id.moudle_icon))).setBackgroundResource(provider.getModuleIconResId());
            ((Button)(view.findViewById(R.id.moudle_navigation_main))).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    provider.startMainActivity(AppMainActivity.this);
                }
            });
            app_modules_all.addView(view);
        }
    }

    //填充Provider
    private void initProvider(List<String> modules){

        //1.添加或者重用新的
        ArrayList<IModuleProvider> newProviders = new ArrayList<>();
        for (int i = 0;i<modules.size();i++){
            IModuleProvider provider = ProvierFactory.create(modules.get(i));
            if(null != provider){
                newProviders.add(provider);
                if(!providers.remove(provider)){//说明原来组件不包含
                    provider.onModuleEnter();
                    Log.v("MainActivity","provider "+provider.getClass().getSimpleName() + " id " + provider.hashCode());
                }else{//说明包含，不处理
                    //doNothing.
                }
            }
        }

//新的组件集合为空，则不处理，要不然导致空页面
//        if(newProviders.size() == 0){
//            return;
//        }

        //2.释放旧的
        for (int i = 0;i<providers.size();i++){

            //--------------同时删除多余的已经初始化的Fragment--------------
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft          = fragmentManager.beginTransaction();

            Fragment f = providers.get(i).getModuleMainFragment(false);
            if(null != f){
                ft.remove(f);
            }

//            ft.commit();
            ft.commitAllowingStateLoss();

            //--------------同时删除多余的已经初始化的Fragment--------------

            providers.get(i).onModuleExit();

            //重置当前显示的组件
            if(currentProvider == providers.get(i)){
                currentProvider = null;
            }
        }
        providers.clear();

        //3.重新赋值
        providers = newProviders;
    }

    //填充UI
    private void initView(){

        LinearLayout app_modules_valid = (LinearLayout) findViewById(R.id.app_modules_valid);
        LinearLayout app_tabs = (LinearLayout) findViewById(R.id.app_tabs);

        app_modules_valid.removeAllViews();
        app_tabs.removeAllViews();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels/providers.size();

        for (int i = 0;i<providers.size();i++){

            final IModuleProvider provider = providers.get(i);

            View view = LayoutInflater.from(this).inflate(R.layout.app_module_item,app_modules_valid,false);
            ((TextView)(view.findViewById(R.id.moudle_name))).setText(provider.getModuleName());
            ((ImageView)(view.findViewById(R.id.moudle_icon))).setBackgroundResource(provider.getModuleIconResId());
            ((Button)(view.findViewById(R.id.moudle_navigation_main))).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    provider.startMainActivity(AppMainActivity.this);
                }
            });
            app_modules_valid.addView(view);

            View tabItemView = provider.getModuleTabView(this,true);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
            tabItemView.setTag(provider);
            tabItemView.setOnClickListener(this);
            app_tabs.addView(tabItemView,layoutParams);
        }

        setVisibleProvider(currentProvider);
    }

    @Override
    public void onClick(View v) {
        IModuleProvider provider =  (IModuleProvider) v.getTag();
        setVisibleProvider(provider);
    }

    public void setVisibleProvider(IModuleProvider provider) {
        //没有的化默认展示第一个
        if(null == provider){
            provider = providers.get(0);
        }

        if(currentProvider == provider){
            return;
        }

        Fragment nextShowFragment = provider.getModuleMainFragment(true);
        Log.v("MainActivity","nextShowFragment "+nextShowFragment.getClass().getSimpleName() + " id " + nextShowFragment.hashCode());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft          = fragmentManager.beginTransaction();

        //隐藏旧的
        for (int i = 0; i< providers.size(); i++){
            Fragment f = providers.get(i).getModuleMainFragment(false);
            if(null != f && f.isAdded() && f != nextShowFragment){
                ft.hide(f);
            }
        }

        //添加新的
        String tag = provider.getModuleName();

        //如果不是同一个fragment,删除旧的
        Fragment foundFragment = fragmentManager.findFragmentByTag(tag);
        if(null != foundFragment && foundFragment != nextShowFragment){
            ft.remove(foundFragment);
        }

        boolean isAdd  = nextShowFragment.isAdded();
        if(!isAdd){
            ft.add(R.id.app_fragments,nextShowFragment,tag).show(nextShowFragment);
        }else{
            ft.show(nextShowFragment);
        }

//            ft.commit();
        ft.commitAllowingStateLoss();

        currentProvider = provider;
    }
}