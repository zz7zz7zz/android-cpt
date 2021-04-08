package com.module.im;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
}