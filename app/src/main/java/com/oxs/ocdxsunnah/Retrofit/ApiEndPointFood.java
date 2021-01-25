package com.oxs.ocdxsunnah.Retrofit;

import com.oxs.ocdxsunnah.Models.FoodModels;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndPointFood {
        @GET("api/food")
        Call<FoodModels> getData();
}
