package com.oxs.ocdxsunnah.Retrofit;

import com.oxs.ocdxsunnah.Models.OlahragaModels;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndpointOlahraga {
    @GET("api/olahraga")
    Call<OlahragaModels> getData();
}
