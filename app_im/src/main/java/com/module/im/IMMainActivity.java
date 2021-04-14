package com.module.im;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.module.components.ComponentServiceManager;
import com.module.components.game.IGameService;
import com.module.components.integrate.IIntegrateService;
import com.module.components.news.INewsService;
import com.module.components.shopping.IShoppingService;
import com.module.components.video.IVideoService;

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
            IGameService provider = (IGameService) ComponentServiceManager.getComponentByName(":app_game");
            if(null != provider){
                provider.startGame("H H H-1");
            }
        }

        {
            IIntegrateService provider = (IIntegrateService) ComponentServiceManager.getComponentByName(":app_integrate");
            if(null != provider){
                provider.getIntegrateTasks();
            }
        }

        {
            IShoppingService provider = (IShoppingService) ComponentServiceManager.getComponentByName(":app_shopping");
            if(null != provider){
                provider.getGoodInfo();
            }
        }

        {
            INewsService provider = (INewsService) ComponentServiceManager.getComponentByName(":app_news");
            if(null != provider){
                provider.getNewsList();
            }
        }

        {
            IVideoService provider = (IVideoService) ComponentServiceManager.getComponentByName(":app_video");
            if(null != provider){
                provider.playVideo(this, "http://www.baidu.com/video");
            }
        }
    }

    private void communicateWithOtherComponents2(){

        Log.v("IMMainActivity","----------from communicateWithOtherComponents2()----------");

        {
            IGameService provider = ComponentServiceManager.getComponentByClass(IGameService.class);
            if(null != provider){
                provider.startGame("H H H-2");
            }
        }

        {
            IIntegrateService provider = ComponentServiceManager.getComponentByClass(IIntegrateService.class);
            if(null != provider){
                provider.getIntegrateTasks();
            }
        }

        {
            IShoppingService provider = ComponentServiceManager.getComponentByClass(IShoppingService.class);
            if(null != provider){
                provider.getGoodInfo();
            }
        }

        {
            INewsService provider = ComponentServiceManager.getComponentByClass(INewsService.class);
            if(null != provider){
                provider.getNewsList();
            }
        }

        {
            IVideoService provider = ComponentServiceManager.getComponentByClass(IVideoService.class);
            if(null != provider){
                provider.playVideo(this, "http://www.baidu.com/video");
            }
        }
    }
}