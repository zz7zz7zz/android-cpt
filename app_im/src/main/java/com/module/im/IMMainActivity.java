package com.module.im;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.app.base.net.http.Http;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.lib.pay.Pay;
import com.lib.pay.core.service.IPayConsts;
import com.lib.pay.core.service.IPayResult;
import com.lib.pay.core.service.PayOrder;
import com.module.im.api.ImApi;
import com.module.im.bean.FiddlerResponse;
import com.module.im.proto.ChatMessageText;
import com.module.service.ServiceManager;
import com.module.service.game.IGameService;
import com.module.service.integrate.IIntegrateService;
import com.module.service.news.INewsService;
import com.module.service.shopping.IShoppingService;
import com.module.service.video.IVideoService;
import com.squareup.wire.WireTypeAdapterFactory;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        analysis();

        protoBuffer();


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

    private void analysis(){
//        Analysis.getInstance().init(getApplicationContext(), BaseApplication.getInstance().getChannel(),BaseApplication.getInstance().getVersionName(),BaseApplication.getInstance().getPackageName());
    }

    private void protoBuffer(){
        ChatMessageText chatMessageText = new ChatMessageText("abcdefg");
        byte[] bytes = ChatMessageText.ADAPTER.encode(chatMessageText);

        ChatMessageText chatMessageText2 = null;
        try {
            chatMessageText2 = ChatMessageText.ADAPTER.decode(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v(TAG,"resp "+chatMessageText2.toString());
        Log.v(TAG,"protoBuffer "+ Thread.currentThread().getName());

        //GSON 与 protobuffer 对象互转
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new WireTypeAdapterFactory()).create();
        TypeAdapter typeAdapter = gson.getAdapter(ChatMessageText.class);
        String strJson = typeAdapter.toJson(chatMessageText);
        Log.v(TAG,"Obj->Json "+strJson);
        try {
            ChatMessageText chatMessageText3 = (ChatMessageText) typeAdapter.fromJson(strJson);
            Log.v(TAG,"Json->Obj "+chatMessageText3.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Http.create(ImApi.class).testFiddlerApi().enqueue(new Callback<List<FiddlerResponse>>() {
            @Override
            public void onResponse(Call<List<FiddlerResponse>> call, Response<List<FiddlerResponse>> response) {
                Log.v(TAG,"testFiddlerApi onResponse "+ response.body().toString());
                Log.v(TAG,"testFiddlerApi onSuccess "+ Thread.currentThread().getName());
            }

            @Override
            public void onFailure(Call<List<FiddlerResponse>> call, Throwable t) {
                Log.v(TAG,"testFiddlerApi onFailure "+ Thread.currentThread().getName());
            }
        });
//
//        Http.create(ImApi.class).testFiddlerApi2().enqueue(new Callback<ChatMessageText>() {
//            @Override
//            public void onResponse(Call<ChatMessageText> call, Response<ChatMessageText> response) {
//                Log.v(TAG,"testFiddlerApi2 onResponse "+ response.body().toString());
//                Log.v(TAG,"testFiddlerApi2 onResponse "+ Thread.currentThread().getName());
//            }
//
//            @Override
//            public void onFailure(Call<ChatMessageText> call, Throwable t) {
//                Log.v(TAG,"testFiddlerApi2 onFailure "+ t.getMessage());
//                Log.v(TAG,"testFiddlerApi2 onFailure "+ Thread.currentThread().getName());
//            }
//        });

//        Http.create(ImApi.class).getChatSession().enqueue(new Callback<ChatMessageText>() {
//            @Override
//            public void onResponse(Call<ChatMessageText> call, Response<ChatMessageText> response) {
//                Log.v(TAG,"getChatSession onResponse "+ response.body().toString());
//                Log.v(TAG,"getChatSession onResponse "+ Thread.currentThread().getName());
//            }
//
//            @Override
//            public void onFailure(Call<ChatMessageText> call, Throwable t) {
//                Log.v(TAG,"getChatSession onFailure "+ Thread.currentThread().getName());
//            }
//        });

//        Http.create(ImApi.class).getChatSession(chatMessageText).enqueue(new Callback<ChatMessageText>() {
//            @Override
//            public void onResponse(Call<ChatMessageText> call, Response<ChatMessageText> response) {
//                Log.v(TAG,"getChatSession onResponse "+ response.body().toString());
//                Log.v(TAG,"getChatSession onResponse "+ Thread.currentThread().getName());
//            }
//
//            @Override
//            public void onFailure(Call<ChatMessageText> call, Throwable t) {
//                Log.v(TAG,"getChatSession onFailure "+ Thread.currentThread().getName());
//            }
//        });

    }
}