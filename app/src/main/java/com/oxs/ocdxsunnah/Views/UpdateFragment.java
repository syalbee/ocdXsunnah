package com.oxs.ocdxsunnah.Views;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;
import com.oxs.ocdxsunnah.Database.DatabaseInit;
import com.oxs.ocdxsunnah.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText edit;
    Button btnUpdate;
    TextView txtPersentase;
    ProgressBar progressBar;
    Dialog dialog;

    DatabaseInit db = new DatabaseInit();

    private String bBadan, bIdeal;
    private String beratUpdate;
    private  String uID;

    public UpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateFragment newInstance(String param1, String param2) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_update, container, false);

        btnUpdate = (Button) root.findViewById(R.id.btnUpdate);
        edit = (EditText) root.findViewById(R.id.txtUpdate);
        progressBar = (ProgressBar) root.findViewById(R.id.progressbar);
        txtPersentase = (TextView)root.findViewById(R.id.textPersentase);
        dialog = new Dialog(getActivity());


        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        db.user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser firebaseUser = db.firebaseAuth.getCurrentUser();
                uID = firebaseUser.getUid();

                bBadan = snapshot.child(firebaseUser.getUid()).child("beratBadan").getValue().toString();
                bIdeal = snapshot.child(firebaseUser.getUid()).child("beratIdeal").getValue().toString();
                beratUpdate = snapshot.child(firebaseUser.getUid()).child("beratUpdate").getValue().toString();

                edit.setText(beratUpdate);

                hitungProgres(beratUpdate, bIdeal, bBadan);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beratUpdate = edit.getText().toString();
                db.user.child(uID).child("beratUpdate").setValue(beratUpdate);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        });

        return root;

    }

    private int hitungProgres(String bU, String bI, String bB){
        float Berat = Float.parseFloat(bB) - Float.parseFloat(bI);
        int berat = (int) Berat;
        float Rumus = Berat - (Float.parseFloat(bU)-Float.parseFloat(bI));
        int rumus = (int) Rumus;
        int Update = 100*rumus/berat;

        if (Integer.valueOf(bU) != 0){
            Update(Update);
        }

        return Update;
    }

    private void Update(int beratUpdate){

        if(beratUpdate >= 100){
            dialogShow();
        }
        progressBar.setProgress(beratUpdate);
        txtPersentase.setText(beratUpdate+"%");
    }

    private void dialogShow(){
        dialog.setContentView(R.layout.dialog_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btOk;
        btOk = dialog.findViewById(R.id.btYes);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(), MenuActivity.class));
            }
        });
        dialog.show();
    }

}