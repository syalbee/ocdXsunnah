package com.oxs.ocdxsunnah.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceOlahraga {
    private static String BASE_URL = "http://rpl.aksyalabee.my.id/";
    private static Retrofit retrofit;

    public static ApiEndpointOlahraga endpoint() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiEndpointOlahraga.class);
    }
}
