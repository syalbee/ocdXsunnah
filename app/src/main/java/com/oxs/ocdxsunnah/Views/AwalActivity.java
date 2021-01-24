package com.oxs.ocdxsunnah.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.oxs.ocdxsunnah.R;
import com.oxs.ocdxsunnah.Service.AlarmAkhirService;
import com.oxs.ocdxsunnah.Service.AlarmAwalService;
import com.oxs.ocdxsunnah.Service.AlarmSahurService;

public class AwalActivity extends AppCompatActivity {

    Button btMulai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awal);

        btMulai = findViewById(R.id.btYumulai);

        btMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(AwalActivity.this, StepActivity.class);
                startActivity(next);
                finish();
            }
        });


    }

}