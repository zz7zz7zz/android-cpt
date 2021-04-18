package com.module.im;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.lib.pay.Pay;
import com.lib.pay.core.service.IPayConsts;
import com.lib.pay.core.service.IPayResult;
import com.lib.pay.core.service.PayOrder;
import com.module.service.ServiceManager;
import com.module.service.game.IGameService;
import com.module.service.integrate.IIntegrateService;
import com.module.service.news.INewsService;
import com.module.service.shopping.IShoppingService;
import com.module.service.video.IVideoService;

public class IMMainActivity extends AppCompatActivity implements IPayResult {

    private static final String TAG = "IMMainActivity";
    static {
        System.loadLibrary("signature");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_main);

        communicateWithOtherComponents1();

        communicateWithOtherComponents2();

        pay();
    }

    private void communicateWithOtherComponents1(){

        Log.v("IMMainActivity","----------from getService(componentName)----------");

        {
            IGameService provider = (IGameService) ServiceManager.getService(":app_game");
            if(null != provider){
                provider.startGame("H H H-1");
            }
        }

        {
            IIntegrateService provider = (IIntegrateService) ServiceManager.getService(":app_integrate");
            if(null != provider){
                provider.getIntegrateTasks();
            }
        }

        {
            IShoppingService provider = (IShoppingService) ServiceManager.getService(":app_shopping");
            if(null != provider){
                provider.getGoodInfo();
            }
        }

        {
            INewsService provider = (INewsService) ServiceManager.getService(":app_news");
            if(null != provider){
                provider.getNewsList();
            }
        }

        {
            IVideoService provider = (IVideoService) ServiceManager.getService(":app_video");
            if(null != provider){
                provider.playVideo(this, "http://www.baidu.com/video");
            }
        }
    }

    private void communicateWithOtherComponents2(){

        Log.v("IMMainActivity","----------from getService(IxxxService.class)----------");

        {
            IGameService provider = ServiceManager.getService(IGameService.class);
            if(null != provider){
                provider.startGame("H H H-2");
            }
        }

        {
            IIntegrateService provider = ServiceManager.getService(IIntegrateService.class);
            if(null != provider){
                provider.getIntegrateTasks();
            }
        }

        {
            IShoppingService provider = ServiceManager.getService(IShoppingService.class);
            if(null != provider){
                provider.getGoodInfo();
            }
        }

        {
            INewsService provider = ServiceManager.getService(INewsService.class);
            if(null != provider){
                provider.getNewsList();
            }
        }

        {
            IVideoService provider = ServiceManager.getService(IVideoService.class);
            if(null != provider){
                provider.playVideo(this, "http://www.baidu.com/video");
            }
        }
    }


    private void pay(){
        Pay.getInstance().pay(IPayConsts.PAY_ALI, new PayOrder("sku.coin.100"), this);
        Pay.getInstance().pay(IPayConsts.PAY_WECHAT, new PayOrder("sku.coin.100"), this);
        Pay.getInstance().pay(IPayConsts.PAY_HUAWEI, new PayOrder("sku.coin.100"), this);
        Pay.getInstance().pay(IPayConsts.PAY_GOOGLE, new PayOrder("sku.coin.100"), this);
    }

    @Override
    public void onPaySuccess() {
        Log.v(TAG,"------------- onPaySuccess -------------");
    }

    @Override
    public void onPayFailed(int errCode, String errMessage) {
        Log.e(TAG,String.format("------------- onPayFailed errCode %d errMessage %s ",errCode,errMessage));
    }
}