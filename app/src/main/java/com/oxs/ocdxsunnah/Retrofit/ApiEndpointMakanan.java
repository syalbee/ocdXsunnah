package com.oxs.ocdxsunnah.Retrofit;

import com.oxs.ocdxsunnah.Models.MakananModels;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndpointMakanan {
    @GET("api/makanan")
    Call<MakananModels> getData();
}
