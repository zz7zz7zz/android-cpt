package com.module.im.api;

import com.module.im.proto.ChatMessageText;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ImApi {

    @POST("/")
    Call<List<ChatMessageText>> getPrivateChatMessage(@Body String userId);//获取聊天记录

    @POST("/")
    Call<List<ChatMessageText>> getChatSession();//获取和所有用户的最后一条聊天记录
}
