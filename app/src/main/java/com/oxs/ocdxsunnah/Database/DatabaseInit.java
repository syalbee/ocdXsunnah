package com.oxs.ocdxsunnah.Database;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseInit {

    public FirebaseDatabase database;
    public FirebaseAuth firebaseAuth;
    public GoogleSignInClient googleSignInClient;

    public DatabaseReference user;


    public DatabaseInit() {
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        user = database.getReference("user");
    }
}
