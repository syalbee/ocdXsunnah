package com.oxs.ocdxsunnah.Views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oxs.ocdxsunnah.Models.MakananModels;
import com.oxs.ocdxsunnah.R;
import com.oxs.ocdxsunnah.Retrofit.ApiServiceMakanan;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakananFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakananFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView txtNama, txtUlasan, txtNama2, txtUlasan2, txtNama3, txtUlasan3, txtNama4, txtUlasan4;

    public MakananFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakananFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakananFragment newInstance(String param1, String param2) {
        MakananFragment fragment = new MakananFragment();
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
        final View root = inflater.inflate(R.layout.fragment_makanan, container, false);
        txtNama = (TextView) root.findViewById(R.id.txtNama);
        txtUlasan = (TextView) root.findViewById(R.id.txtUlasan);
        txtNama2 = (TextView) root.findViewById(R.id.txtNama2);
        txtUlasan2 = (TextView) root.findViewById(R.id.txtUlasan2);
        txtNama3 = (TextView) root.findViewById(R.id.txtNama3);
        txtUlasan3 = (TextView) root.findViewById(R.id.txtUlasan3);
        txtNama4 = (TextView) root.findViewById(R.id.txtNama4);
        txtUlasan4 = (TextView) root.findViewById(R.id.txtUlasan4);

        ImageView imageView1 = root.findViewById(R.id.image_url1);

        Picasso.get().load("https://www.makanabis.com/bima-content/2020/07/01/l-oats-oatmeal-1296x728-header3e443b82ee11dcf3f2d44d1ca48d92d520200701170511-bimacms.jpg").into(imageView1);

        ImageView imageView2 = root.findViewById(R.id.image_url2);

        Picasso.get().load("https://i.pinimg.com/originals/a8/b8/4f/a8b84fcdeba88dd2ddf17ea18459e192.jpg").into(imageView2);

        ImageView imageView3 = root.findViewById(R.id.image_url3);

        Picasso.get().load("https://cms.sehatq.com/public/img/article_img/ingin-kulit-wajah-kencang-tak-perlu-facelift-coba-saja-masker-putih-telur-1570505842.jpg").into(imageView3);

        ImageView imageView4 = root.findViewById(R.id.image_url4);

        Picasso.get().load("https://www.honestdocs.id/system/blog_articles/main_hero_images/000/000/782/original/Ini_Dia__Manfaat_Brokoli_Yang_Luar_Biasa.jpg").into(imageView4);

        getDataFromApi();

        return root;
    }

    private void getDataFromApi() {
        ApiServiceMakanan.endpoint().getData()
                .enqueue(new Callback<MakananModels>() {
                    @Override
                    public void onResponse(Call<MakananModels> call, Response<MakananModels> response) {
                        if (response.isSuccessful()) {
                            List<MakananModels.Data> results = response.body().getData();
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
                    public void onFailure(Call<MakananModels> call, Throwable t) {
                        Log.d("MenuActivity", t.toString());
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