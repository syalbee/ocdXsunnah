package com.oxs.ocdxsunnah.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.oxs.ocdxsunnah.Database.DatabaseInit;
import com.oxs.ocdxsunnah.R;

public class MenuActivity extends AppCompatActivity {

    MeowBottomNavigation nabar;
    DatabaseInit db = new DatabaseInit();
    private String ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        nabar = findViewById(R.id.navbare) ;

        nabar.add(new MeowBottomNavigation.Model(1, R.drawable.ic_update_64));
        nabar.add(new MeowBottomNavigation.Model(2, R.drawable.ic_home_24));
        nabar.add(new MeowBottomNavigation.Model(3, R.drawable.ic_list_24));


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentLayout, new HomeFragment()).commit();


        nabar.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        nabar.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });

        nabar.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment selectedFragment = null;
                switch (item.getId()){
                    case 2:
                        selectedFragment = new HomeFragment();
                        break;
                    case 3:
                        selectedFragment = new RekomenFragment();
                        break;
                    case 1:
                        selectedFragment = new UpdateFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayout
                                ,selectedFragment).commit();
            }
        });
    }
}