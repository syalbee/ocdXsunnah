package com.oxs.ocdxsunnah.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.oxs.ocdxsunnah.Adapter.FoodAdapter;
import com.oxs.ocdxsunnah.Models.FoodModels;
import com.oxs.ocdxsunnah.Models.ImsakModels;
import com.oxs.ocdxsunnah.Models.MakananModels;
import com.oxs.ocdxsunnah.R;
import com.oxs.ocdxsunnah.Retrofit.ApiService;
import com.oxs.ocdxsunnah.Retrofit.ApiServiceFood;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity {
    private final String TAG = "FoodActivity";

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<FoodModels.Data> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        getDataFromAPI();

        recyclerView = findViewById(R.id.rvMakanan);

        foodAdapter = new FoodAdapter(results);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(foodAdapter);

    }


    private void getDataFromAPI(){

        ApiServiceFood.endpoint().getData().enqueue(new Callback<FoodModels>() {
            @Override
            public void onResponse(Call<FoodModels> call, Response<FoodModels> response) {
                Log.d(TAG, response.toString());
                if(response.isSuccessful()){
                    ArrayList<FoodModels.Data> results = response.body().getData();
                    Log.d(TAG, results.toString());
                    foodAdapter.setData(results);
                }
            }

            @Override
            public void onFailure(Call<FoodModels> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }
}