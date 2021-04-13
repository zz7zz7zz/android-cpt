package com.module.im;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.module.components.ProviderManager;
import com.module.components.game.IGameProvider;
import com.module.components.integrate.IIntegrateProvider;
import com.module.components.news.INewsProvider;
import com.module.components.shopping.IShoppingProvider;
import com.module.components.video.IVideoProvider;

public class IMMainActivity extends AppCompatActivity {


    static {
        System.loadLibrary("signature");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_main);

        communicateWithOtherComponents1();

        communicateWithOtherComponents2();
    }

    private void communicateWithOtherComponents1(){

        Log.v("IMMainActivity","----------from getComponentByName()----------");

        {
            IGameProvider provider = (IGameProvider) ProviderManager.getComponentByName(":app_game");
            if(null != provider)
                provider.startGame("H H H-1");
        }

        {
            IIntegrateProvider provider = (IIntegrateProvider) ProviderManager.getComponentByName(":app_integrate");
            if(null != provider)
                provider.getIntegrateTasks();
        }

        {
            IShoppingProvider provider = (IShoppingProvider) ProviderManager.getComponentByName(":app_shopping");
            if(null != provider)
                provider.getGoodInfo();
        }

        {
            INewsProvider provider = (INewsProvider) ProviderManager.getComponentByName(":app_news");
            if(null != provider)
                provider.getNewsList();
        }

        {
            IVideoProvider provider = (IVideoProvider) ProviderManager.getComponentByName(":app_video");
            if(null != provider)
                provider.playVideo(this, "http://www.baidu.com/video");
        }
    }

    private void communicateWithOtherComponents2(){

        Log.v("IMMainActivity","----------from communicateWithOtherComponents2()----------");

        {
            IGameProvider provider = ProviderManager.getComponentByClass(IGameProvider.class);
            provider.startGame("H H H-2");
        }

        {
            IIntegrateProvider provider = ProviderManager.getComponentByClass(IIntegrateProvider.class);
            provider.getIntegrateTasks();
        }

        {
            IShoppingProvider provider = ProviderManager.getComponentByClass(IShoppingProvider.class);
            provider.getGoodInfo();
        }

        {
            INewsProvider provider = ProviderManager.getComponentByClass(INewsProvider.class);
            provider.getNewsList();
        }

        {
            IVideoProvider provider = ProviderManager.getComponentByClass(IVideoProvider.class);
            provider.playVideo(this, "http://www.baidu.com/video");
        }
    }
}