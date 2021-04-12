package com.module.im;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.module.components.ProviderFactory;
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
            IGameProvider provider = (IGameProvider) ProviderFactory.getComponentByName(":app_game");
            if(null != provider)
                provider.startGame("H H H-1");
        }

        {
            IIntegrateProvider provider = (IIntegrateProvider) ProviderFactory.getComponentByName(":app_integrate");
            if(null != provider)
                provider.getIntegrateTasks();
        }

        {
            IShoppingProvider provider = (IShoppingProvider) ProviderFactory.getComponentByName(":app_shopping");
            if(null != provider)
                provider.getGoodInfo();
        }

        {
            INewsProvider provider = (INewsProvider) ProviderFactory.getComponentByName(":app_news");
            if(null != provider)
                provider.getNewsList();
        }

        {
            IVideoProvider provider = (IVideoProvider) ProviderFactory.getComponentByName(":app_video");
            if(null != provider)
                provider.playVideo(this, "http://www.baidu.com/video");
        }
    }

    private void communicateWithOtherComponents2(){

        Log.v("IMMainActivity","----------from communicateWithOtherComponents2()----------");

        {
            IGameProvider provider = ProviderFactory.getComponentByClass(IGameProvider.class);
            provider.startGame("H H H-2");
        }

        {
            IIntegrateProvider provider = ProviderFactory.getComponentByClass(IIntegrateProvider.class);
            provider.getIntegrateTasks();
        }

        {
            IShoppingProvider provider = ProviderFactory.getComponentByClass(IShoppingProvider.class);
            provider.getGoodInfo();
        }

        {
            INewsProvider provider = ProviderFactory.getComponentByClass(INewsProvider.class);
            provider.getNewsList();
        }

        {
            IVideoProvider provider = ProviderFactory.getComponentByClass(IVideoProvider.class);
            provider.playVideo(this, "http://www.baidu.com/video");
        }
    }
}