package com.oxs.ocdxsunnah.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceFood {

    private static String BASE_URL = "http://rpl.aksyalabee.my.id/";
    private static Retrofit retrofit;

    public static ApiEndPointFood endpoint() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiEndPointFood.class);
    }


}
