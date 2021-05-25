package com.module.im.api;

import com.module.im.proto.ChatMessageText;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ImApi {

    @POST("/")
    Call<List<ChatMessageText>> getPrivateChatMessage(@Body String userId);//获取聊天记录

    @POST("/")
    Call<ChatMessageText> getChatSession();//获取和所有用户的最后一条聊天记录

    @GET("/content/GetArticles?clientId=6582AA488E3B4A2F30D03E2B4BB2921313CB79E5350F8EF88AA56C5C3B1C72D8")
    Call<List<FiddlerResponse>> testFiddlerApi();

}
