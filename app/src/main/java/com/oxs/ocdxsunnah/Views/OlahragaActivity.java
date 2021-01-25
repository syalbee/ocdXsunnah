package com.oxs.ocdxsunnah.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.oxs.ocdxsunnah.Adapter.FoodAdapter;
import com.oxs.ocdxsunnah.Adapter.OlahragaAdapter;
import com.oxs.ocdxsunnah.Models.FoodModels;
import com.oxs.ocdxsunnah.Models.OlahragaModels;
import com.oxs.ocdxsunnah.R;
import com.oxs.ocdxsunnah.Retrofit.ApiServiceFood;
import com.oxs.ocdxsunnah.Retrofit.ApiServiceOlahraga;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OlahragaActivity extends AppCompatActivity {

    private final String TAG = "OlahragaActivity";

    private RecyclerView recyclerView;
    private OlahragaAdapter olahragaAdapter;
    private List<OlahragaModels.Data> results = new ArrayList<>();

    ImageButton btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olahraga);


        getDataFromAPI();

        recyclerView = findViewById(R.id.rvOlahraga);
        btBack = findViewById(R.id.btBack);


        olahragaAdapter = new OlahragaAdapter(results);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(olahragaAdapter);


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(OlahragaActivity.this, MenuActivity.class);
                startActivity(next);
            }
        });

    }

    private void getDataFromAPI(){

        ApiServiceOlahraga.endpoint().getData().enqueue(new Callback<OlahragaModels>() {
            @Override
            public void onResponse(Call<OlahragaModels> call, Response<OlahragaModels> response) {
                Log.d(TAG, response.toString());
                if(response.isSuccessful()){
                    ArrayList<OlahragaModels.Data> results = response.body().getData();
                    Log.d(TAG, results.toString());
                    olahragaAdapter.setData(results);
                }
            }

            @Override
            public void onFailure(Call<OlahragaModels> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }
}