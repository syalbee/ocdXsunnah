package com.oxs.ocdxsunnah.Retrofit;

import com.oxs.ocdxsunnah.Models.ImsakModels;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndpoint {
    @GET("today.json?city=bandung")
    Call<ImsakModels> getData();
}
