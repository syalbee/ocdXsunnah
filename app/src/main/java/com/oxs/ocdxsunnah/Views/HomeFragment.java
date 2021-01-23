package com.oxs.ocdxsunnah.Views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.oxs.ocdxsunnah.Database.DatabaseInit;
import com.oxs.ocdxsunnah.R;
import com.oxs.ocdxsunnah.getDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView ivUser;
    ImageButton btSetting;
    TextView tvNamas, tvWaktu, tvbIdeal, tvBeratku, tvRentang, tvDietke;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        final View root = inflater.inflate(R.layout.fragment_home2, container, false);

        getDate gd = new getDate();
        DatabaseInit db = new DatabaseInit();

        ivUser = root.findViewById(R.id.ivUser);
        tvNamas = root.findViewById(R.id.tvNamas);
        btSetting = root.findViewById(R.id.btSetting);

        tvWaktu = root.findViewById(R.id.tvWaktu);
        tvbIdeal = root.findViewById(R.id.tvbIdeal);
        tvBeratku = root.findViewById(R.id.tvberatKu);
        tvRentang = root.findViewById(R.id.tvrentangWaktu);
        tvDietke = root.findViewById(R.id.tvHarike);

        tvWaktu.setText(gd.getDateNow("dd MM YY"));

        db.user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser firebaseUser = db.firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Glide.with(HomeFragment.this)
                    .load(firebaseUser.getPhotoUrl())
                            .into(ivUser);

                    String nama = snapshot.child(firebaseUser.getUid()).child("nama").getValue().toString();
                    String[] value = nama.split(" ");
                    String name = value[0];
                    tvNamas.setText("Hi " + name);

                    tvbIdeal.setText(snapshot.child(firebaseUser.getUid()).child("beratIdeal").getValue().toString());
                    tvBeratku.setText(snapshot.child(firebaseUser.getUid()).child("beratBadan").getValue().toString());
                    tvRentang.setText(snapshot.child(firebaseUser.getUid()).child("lama").getValue().toString());
                    tanggals(snapshot.child(firebaseUser.getUid()).child("tanggalAktif").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

        return root;
    }

    private void tanggals(String waktos){
        getDate dg = new getDate();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String tgl = dg.getDateNow("dd/MM/yyyy");

        try{
            //Konversi dari string ke tanggal
            Date TanggalPinjam =df.parse(waktos);
            Date TanggalKembali = df.parse(tgl);

            //Tgl di konversi ke milidetik
            long Hari1 = TanggalPinjam.getTime();
            long Hari2 = TanggalKembali.getTime();
            long diff = Hari2 - Hari1;
            long Lama = diff / (24 * 60 * 60 * 1000);
            tvDietke.setText(Long.toString(Lama));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}