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

import com.module.service.ServiceManager;
import com.module.main.config.ComponentConfig;
import com.module.main.net.NetImpl;
import com.module.service.IService;

import java.util.List;
import java.util.ArrayList;

public class AppMainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<IService> services = new ArrayList<>();
    private IService currentService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);

        initAllComponents(ComponentConfig.allComponents);

        findViewById(R.id.components_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initServices(ComponentConfig.localComponents);
                initView();
            }
        });
        findViewById(R.id.components_remote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NetImpl.getModules(new NetImpl.ICallback<List<String>>() {
                    @Override
                    public void onSuccss(List<String> strings) {
                        initServices(strings);
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

        //??????????????????
        for (int i = 0; i< services.size(); i++){
            IService service = services.get(i);
            if(null != service){
                service.onComponentExit();
            }
        }
        services = null;
        currentService = null;
    }

    private void initAllComponents(List<String> components){

        ArrayList<IService> allComponentSerices = new ArrayList<>();
        for (int i = 0;i<components.size();i++){
            IService service = ServiceManager.getService(components.get(i));
            if(null != service){
                allComponentSerices.add(service);
            }
        }

        if(allComponentSerices.size() == 0){
            return;
        }

        LinearLayout app_modules_all = (LinearLayout) findViewById(R.id.app_modules_all);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels/allComponentSerices.size();

        for (int i = 0;i<allComponentSerices.size();i++){

            final IService service = allComponentSerices.get(i);

            View view = LayoutInflater.from(this).inflate(R.layout.app_module_item,app_modules_all,false);
            ((TextView)(view.findViewById(R.id.moudle_name))).setText(service.getComponentName());
            ((ImageView)(view.findViewById(R.id.moudle_icon))).setBackgroundResource(service.getComponentIconResId());
            ((Button)(view.findViewById(R.id.moudle_navigation_main))).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    service.startComponentMainActivity(AppMainActivity.this);
                }
            });
            app_modules_all.addView(view);
        }
    }

    //????????????Service
    private void initServices(List<String> componets){

        //1.????????????????????????
        ArrayList<IService> newServices = new ArrayList<>();
        for (int i = 0;i<componets.size();i++){
            IService service = ServiceManager.getService(componets.get(i));
            if(null != service){
                newServices.add(service);
                if(!services.remove(service)){//???????????????????????????
                    service.onComponentEnter();
                    Log.v("MainActivity","Service "+service.getClass().getSimpleName() + " id " + service.hashCode());
                }else{//????????????????????????
                    //doNothing.
                }
            }
        }

//??????????????????????????????????????????????????????????????????
//        if(newServices.size() == 0){
//            return;
//        }

        //2.????????????
        for (int i = 0; i< services.size(); i++){

            //--------------???????????????????????????????????????Fragment--------------
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft          = fragmentManager.beginTransaction();

            Fragment f = services.get(i).getComponentMainFragment(false);
            if(null != f){
                ft.remove(f);
            }

//            ft.commit();
            ft.commitAllowingStateLoss();

            //--------------???????????????????????????????????????Fragment--------------

            services.get(i).onComponentExit();

            //???????????????????????????
            if(currentService == services.get(i)){
                currentService = null;
            }
        }
        services.clear();

        //3.????????????
        services = newServices;
    }

    //??????UI
    private void initView(){

        LinearLayout app_modules_valid = (LinearLayout) findViewById(R.id.app_modules_valid);
        LinearLayout app_tabs = (LinearLayout) findViewById(R.id.app_tabs);

        app_modules_valid.removeAllViews();
        app_tabs.removeAllViews();

        if(services.size() == 0){
            return;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels/ services.size();

        for (int i = 0; i< services.size(); i++){

            final IService service = services.get(i);

            View view = LayoutInflater.from(this).inflate(R.layout.app_module_item,app_modules_valid,false);
            ((TextView)(view.findViewById(R.id.moudle_name))).setText(service.getComponentName());
            ((ImageView)(view.findViewById(R.id.moudle_icon))).setBackgroundResource(service.getComponentIconResId());
            ((Button)(view.findViewById(R.id.moudle_navigation_main))).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    service.startComponentMainActivity(AppMainActivity.this);
                }
            });
            app_modules_valid.addView(view);

            View tabItemView = service.getComponentTabView(this,true);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
            tabItemView.setTag(service);
            tabItemView.setOnClickListener(this);
            app_tabs.addView(tabItemView,layoutParams);
        }

        setVisibleService(currentService);
    }

    @Override
    public void onClick(View v) {
        IService service =  (IService) v.getTag();
        setVisibleService(service);
    }

    public void setVisibleService(IService service) {
        //?????????????????????????????????
        if(null == service){
            service = services.get(0);
        }

        if(currentService == service){
            return;
        }

        Fragment nextShowFragment = service.getComponentMainFragment(true);
        Log.v("MainActivity","nextShowFragment "+nextShowFragment.getClass().getSimpleName() + " id " + nextShowFragment.hashCode());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft          = fragmentManager.beginTransaction();

        //????????????
        for (int i = 0; i< services.size(); i++){
            Fragment f = services.get(i).getComponentMainFragment(false);
            if(null != f && f.isAdded() && f != nextShowFragment){
                ft.hide(f);
            }
        }

        //????????????
        String tag = service.getComponentName();

        //?????????????????????fragment,????????????
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

        currentService = service;
    }
}