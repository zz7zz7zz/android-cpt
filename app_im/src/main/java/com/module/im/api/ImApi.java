package com.module.im.api;

import com.module.im.bean.FiddlerResponse;
import com.module.im.proto.ChatMessageText;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;

public interface ImApi {

    @GET("/content")
    Call<ChatMessageText> getChatSession();//获取和所有用户的最后一条聊天记录

    @HTTP(method = "POSt", path = "/content", hasBody = true)
    Call<ChatMessageText> getChatSession(@Body ChatMessageText chatMessageText);//获取和所有用户的最后一条聊天记录

    @GET("/content")
    Call<List<ChatMessageText>> getPrivateChatMessage(@Body String userId);//获取聊天记录

    @GET("/content/GetArticles?clientId=6582AA488E3B4A2F30D03E2B4BB2921313CB79E5350F8EF88AA56C5C3B1C72D8")
    Call<List<FiddlerResponse>> testFiddlerApi();

    @GET("/content/GetArticles?clientId=6582AA488E3B4A2F30D03E2B4BB2921313CB79E5350F8EF88AA56C5C3B1C72D8")
    Call<ChatMessageText> testFiddlerApi2();

}
