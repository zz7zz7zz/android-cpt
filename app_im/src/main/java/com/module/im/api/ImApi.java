package com.module.im.api;

import com.module.im.proto.ChatMessageText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface ImApi {

    Call<ResponseBody> getMessage(@Body ChatMessageText chatMessageText);//登陆
}
