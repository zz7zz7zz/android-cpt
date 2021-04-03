package com.module.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.router.provider.IModuleProvider;

import java.util.ArrayList;

public class AppMainActivity extends AppCompatActivity {

    public ArrayList<IModuleProvider> providers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);

        initProvider();

    }

    private void initProvider(){

        //默认配置
        ArrayList<String> modules = new ArrayList<>();
        modules.add("/im/P");
        modules.add("/game/P");
        modules.add("/integrate/P");
        modules.add("/news/P");
        modules.add("/shopping/P");
        modules.add("/video/P");

        //填充Provider
        providers.clear();
        for (int i = 0;i<modules.size();i++){
            IModuleProvider provider = (IModuleProvider) ARouter.getInstance().build(modules.get(i)).navigation();
            if(null != provider){
                providers.add(provider);
                Log.v("MainActivity","provider "+provider.getClass().getSimpleName() + " id " + provider.hashCode());
            }
        }

        //填充View
        LinearLayout app_container = (LinearLayout) findViewById(R.id.app_container);
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
        }

    }
}