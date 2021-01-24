package com.oxs.ocdxsunnah.Views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxs.ocdxsunnah.Models.OlahragaModels;
import com.oxs.ocdxsunnah.R;
import com.oxs.ocdxsunnah.Retrofit.ApiService;
import com.oxs.ocdxsunnah.Retrofit.ApiServiceOlahraga;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RekomenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RekomenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView txtNama, txtUlasan, txtNama2, txtUlasan2, txtNama3, txtUlasan3, txtNama4, txtUlasan4;

    public RekomenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RekomenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RekomenFragment newInstance(String param1, String param2) {
        RekomenFragment fragment = new RekomenFragment();
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
        final View root = inflater.inflate(R.layout.fragment_rekomen, container, false);
        txtNama = (TextView) root.findViewById(R.id.txtNama);
        txtUlasan = (TextView) root.findViewById(R.id.txtUlasan);
        txtNama2 = (TextView) root.findViewById(R.id.txtNama2);
        txtUlasan2 = (TextView) root.findViewById(R.id.txtUlasan2);
        txtNama3 = (TextView) root.findViewById(R.id.txtNama3);
        txtUlasan3 = (TextView) root.findViewById(R.id.txtUlasan3);
        txtNama4 = (TextView) root.findViewById(R.id.txtNama4);
        txtUlasan4 = (TextView) root.findViewById(R.id.txtUlasan4);

        return root;
    }

    private void getDataFromApi() {
        ApiServiceOlahraga.endpoint().getData()
                .enqueue(new Callback<OlahragaModels>() {
                    @Override
                    public void onResponse(Call<OlahragaModels> call, Response<OlahragaModels> response) {
                        if (response.isSuccessful()) {
                            List<OlahragaModels.Data> results = response.body().getData();
                            tampilNama(results.get(0).getNama());
                            tampilUlasan(results.get(0).getUlasan());
                            tampilNama2(results.get(1).getNama());
                            tampilUlasan2(results.get(1).getUlasan());
                            tampilNama3(results.get(2).getNama());
                            tampilUlasan3(results.get(2).getUlasan());
                            tampilNama4(results.get(3).getNama());
                            tampilUlasan4(results.get(3).getUlasan());
                        }
                    }

                    @Override
                    public void onFailure(Call<OlahragaModels> call, Throwable t) {
                        Log.d("RekomenFragment", t.toString());
                    }
                });
    }

    public void tampilNama(String nama) {

        txtNama.setText(nama);
    }

    public void tampilUlasan(String ulasan) {

        txtUlasan.setText(ulasan);
    }

    public void tampilNama2(String nama) {

        txtNama2.setText(nama);
    }

    public void tampilUlasan2(String ulasan) {

        txtUlasan2.setText(ulasan);
    }

    public void tampilNama3(String nama) {

        txtNama3.setText(nama);
    }

    public void tampilUlasan3(String ulasan) {

        txtUlasan3.setText(ulasan);
    }

    public void tampilNama4(String nama) {

        txtNama4.setText(nama);
    }

    public void tampilUlasan4(String ulasan) {

        txtUlasan4.setText(ulasan);
    }
}