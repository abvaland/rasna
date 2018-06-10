package com.spiderdevelopers.rasna.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    //private static final String BASE_URL = "http://clinostep.adspresence.com/api/";
    private static final String BASE_URL = "http://www.rasnarestaurant.co.in/api/";

    /**
     * Get Retrofit Instance
     */

    private static Retrofit getRetrofitInstance(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */

    public static RetroService getApiService(OkHttpClient okHttpClient) {

        return getRetrofitInstance(okHttpClient).create(RetroService.class);
    }

}
