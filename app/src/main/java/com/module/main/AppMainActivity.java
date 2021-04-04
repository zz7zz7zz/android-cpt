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

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.router.provider.IModuleProvider;

import java.util.ArrayList;

public class AppMainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<IModuleProvider> providers = new ArrayList<>();
    private IModuleProvider currentProvider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);

        //本地默认配置，也可以根据服务器下发的配置进行展示
        ArrayList<String> modules = new ArrayList<>();
        modules.add("/im/P");
        modules.add("/video/P");
        modules.add("/news/P");
        modules.add("/game/P");
        modules.add("/integrate/P");
        modules.add("/shopping/P");
        //初始化操作
        initProvider(modules);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //释放内存操作
        for (int i = 0;i<providers.size();i++){
            IModuleProvider provider = providers.get(i);
            if(null != provider){
                provider.onExit();
            }
        }
        providers = null;
        currentProvider = null;
    }

    //填充Provider
    private void initProvider(ArrayList<String> modules){
        providers.clear();
        for (int i = 0;i<modules.size();i++){
            IModuleProvider provider = (IModuleProvider) ARouter.getInstance().build(modules.get(i)).navigation();
            if(null != provider){
                providers.add(provider);
                provider.onEnter();
                Log.v("MainActivity","provider "+provider.getClass().getSimpleName() + " id " + provider.hashCode());
            }
        }
    }

    //填充UI
    private void initView(){
        LinearLayout app_container = (LinearLayout) findViewById(R.id.app_container);
        LinearLayout app_tabs = (LinearLayout) findViewById(R.id.app_tabs);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels/providers.size();

        for (int i = 0;i<providers.size();i++){

            final IModuleProvider provider = providers.get(i);

            View view = LayoutInflater.from(this).inflate(R.layout.app_module_item,app_container,false);
            ((TextView)(view.findViewById(R.id.moudle_name))).setText(provider.getModuleName());
            ((ImageView)(view.findViewById(R.id.moudle_icon))).setBackgroundResource(provider.getModuleIconResId());
            ((Button)(view.findViewById(R.id.moudle_navigation_main))).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    provider.startMainActivity(AppMainActivity.this);
                }
            });
            app_container.addView(view);

            View tabItemView = provider.getTabView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
            tabItemView.setTag(provider);
            tabItemView.setOnClickListener(this);
            app_tabs.addView(tabItemView,layoutParams);
        }

        onClick(app_tabs.getChildAt(0));
    }

    @Override
    public void onClick(View v) {

        IModuleProvider provider =  (IModuleProvider) v.getTag();
        if(currentProvider == provider){
            return;
        }

        Fragment nextShowFragment = provider.getMainFragment();
        Log.v("MainActivity","nextShowFragment "+nextShowFragment.getClass().getSimpleName() + " id " + nextShowFragment.hashCode());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft          = fragmentManager.beginTransaction();

        //隐藏旧的
        for (int i = 0; i< providers.size(); i++){
            Fragment f = providers.get(i).getMainFragment();
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