package com.example.ydemo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpReq {
    @POST("/login")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password, @Field("sign") String sign);
//    Call<ResponseBody> login(@androidx.annotation.NonNull String username, @androidx.annotation.NonNull String password);
}
