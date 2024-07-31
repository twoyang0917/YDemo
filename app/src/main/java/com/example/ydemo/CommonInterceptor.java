package com.example.ydemo;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String timestampSign = Utils.md5(timestamp + "magic");

        Request request = chain.request().newBuilder()
                .addHeader("ts", timestamp)
                .addHeader("xs", timestampSign)
                .build();

        Response response = chain.proceed(request);
        // 也可以对响应进行统一处理，如decrypt, decode...
        return response;
    }
}
