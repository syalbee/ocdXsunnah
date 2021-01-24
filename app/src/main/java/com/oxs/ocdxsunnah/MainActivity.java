package com.oxs.ocdxsunnah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.oxs.ocdxsunnah.Service.AlarmAkhirService;
import com.oxs.ocdxsunnah.Service.AlarmAwalService;
import com.oxs.ocdxsunnah.Service.AlarmSahurService;
import com.oxs.ocdxsunnah.Views.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopService();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent awal = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(awal);
                finish();
            }
        }, 3000);

    }

    private void stopService() {
        stopService(new Intent(this, AlarmAwalService.class));
        stopService(new Intent(this, AlarmSahurService.class));
        stopService(new Intent(this, AlarmAkhirService.class));
    }
}