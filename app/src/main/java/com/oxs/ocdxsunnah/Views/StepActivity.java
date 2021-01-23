package com.oxs.ocdxsunnah.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.oxs.ocdxsunnah.R;

public class StepActivity extends AppCompatActivity {

    ImageButton btNext1, btBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        btNext1 = findViewById(R.id.btNext1);
        btBack = findViewById(R.id.btBack);

        btNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(StepActivity.this, LoginActivity.class);
                next.putExtra("statuse", 1);
                startActivity(next);
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(StepActivity.this, AwalActivity.class);
                startActivity(back);
            }
        });

    }
}