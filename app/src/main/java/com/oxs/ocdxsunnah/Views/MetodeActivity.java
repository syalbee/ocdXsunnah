package com.oxs.ocdxsunnah.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.oxs.ocdxsunnah.Database.DatabaseInit;
import com.oxs.ocdxsunnah.Models.UserModels;
import com.oxs.ocdxsunnah.R;

public class MetodeActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btNext2, btKolab, btOcd, btBack;
    DatabaseInit db = new DatabaseInit();


    private String uID;
    private String metode;
    private String nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metode);

        btNext2 = findViewById(R.id.btNext2);
        btOcd = findViewById(R.id.btOcd);
        btKolab = findViewById(R.id.btKolab);
        btBack = findViewById(R.id.btBack);

        FirebaseUser firebaseUser = db.firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            uID = firebaseUser.getUid();
            nama = firebaseUser.getDisplayName();
        }

        btNext2.setOnClickListener(this);
        btKolab.setOnClickListener(this);
        btOcd.setOnClickListener(this);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(MetodeActivity.this, LoginActivity.class);
                startActivity(back);
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        String bIdeal = "0";
        String jekel = "-";
        Double bBadan = 0.0;
        Double tBadan = 0.0;
        int lama = 0;

        if(i == R.id.btNext2){
            if(metode == null){
                Toast.makeText(this, "Pilih salah satu", Toast.LENGTH_SHORT).show();
            } else {
                UserModels um = new UserModels();
                db.user.child(uID).setValue(new UserModels(bIdeal, metode, nama, uID,jekel,bBadan,tBadan,lama))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent biodata = new Intent(MetodeActivity.this, BiodataActivity.class);
                                startActivity(biodata);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MetodeActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if(i == R.id.btKolab){
            metode = "kolab";
            Toast.makeText(this, "Anda memilih OCD x Sunnah", Toast.LENGTH_SHORT).show();
        } else if(i == R.id.btOcd){
            metode = "ocd";
            Toast.makeText(this, "Anda memilih OCD", Toast.LENGTH_SHORT).show();
        }
    }


}