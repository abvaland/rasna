package com.spiderdevelopers.rasna.network;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RetroHttpClient extends OkHttpClient {

    public RetroHttpClient(final Context context) {
        new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();

                /*Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                        Credentials.basic("aUsername", "aPassword"));*/

                Request.Builder builder = originalRequest.newBuilder().header("Authorization","");

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();
    }
}
