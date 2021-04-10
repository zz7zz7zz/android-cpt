package com.module.im;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.module.components.ProvierFactory;
import com.module.components.game.IGameProvider;
import com.module.components.integrate.IIntegrateProvider;
import com.module.components.news.INewsProvider;
import com.module.components.shopping.IShoppingProvider;
import com.module.components.video.IVideoProvider;

public class IMMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_main);


        communicateWithOtherComponents();

        communicateWithOtherComponents2();
    }

    private void communicateWithOtherComponents(){
        {
            IGameProvider provider = IGameProvider.get();
            provider.startGame("H H H");
        }

        {
            IIntegrateProvider provider = IIntegrateProvider.get();
            provider.getIntegrateTasks();
        }

        {
            IShoppingProvider provider = IShoppingProvider.get();
            provider.getGoodInfo();
        }

        {
            INewsProvider provider = INewsProvider.get();
            provider.getNewsList();
        }

        {
            IVideoProvider provider = IVideoProvider.get();
            provider.playVideo(this, "http://www.baidu.com/video");
        }
    }

    private void communicateWithOtherComponents2(){
        {
            IGameProvider provider = ProvierFactory.get(IGameProvider.class);
            provider.startGame("H H H 2");
        }

        {
            IIntegrateProvider provider = ProvierFactory.get(IIntegrateProvider.class);
            provider.getIntegrateTasks();
        }

        {
            IShoppingProvider provider = ProvierFactory.get(IShoppingProvider.class);
            provider.getGoodInfo();
        }

        {
            INewsProvider provider = ProvierFactory.get(INewsProvider.class);
            provider.getNewsList();
        }

        {
            IVideoProvider provider = ProvierFactory.get(IVideoProvider.class);
            provider.playVideo(this, "http://www.baidu.com/video");
        }
    }
}