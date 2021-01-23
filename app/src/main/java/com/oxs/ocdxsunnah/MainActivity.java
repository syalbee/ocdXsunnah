package com.oxs.ocdxsunnah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.oxs.ocdxsunnah.Views.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent awal = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(awal);
                finish();
            }
        }, 3000);

    }
}