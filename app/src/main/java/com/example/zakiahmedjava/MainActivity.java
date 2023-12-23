package com.example.zakiahmedjava;

import static com.example.zakiahmedjava.R.id.action_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.zakiahmedjava.HomeFragment.HomeFragment;
import com.example.zakiahmedjava.Map.MapFragment;
import com.example.zakiahmedjava.Profile.ProfileFragment;
import com.example.zakiahmedjava.Settings.SettingsFragment;
import com.example.zakiahmedjava.Wheather.WeatherFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    FragmentContainerView fragmentView;
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;

    int blackColor = 000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentView = findViewById(R.id.fragmentContainerView);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomNavigationView = findViewById(R.id.bottomNavigationBar);

        bottomAppBar.setBackgroundTint(ColorStateList.valueOf(Color.alpha(blackColor)));

        bottomAppBar.setBackgroundTint(ColorStateList.valueOf(Color.rgb(000, 000, 000)));


        bottomNavigationView.setOnItemSelectedListener( item -> {
            if (item.getItemId() == action_home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.action_weather) {
                replaceFragment(new WeatherFragment());
            } else if (item.getItemId() == R.id.action_map) {
                replaceFragment(new MapFragment());
            } else if (item.getItemId() == R.id.action_profile) {
                replaceFragment(new ProfileFragment());
            } else if (item.getItemId() == R.id.action_settings) {
                replaceFragment(new SettingsFragment());
            }

//            switch (item.getItemId()) {
//
//                case R.id.action_home:
//                    replaceFragment(new HomeFragment());
//                    break;
//
//                case R.id.action_weather: {
//                    replaceFragment(new WeatherFragment());
//                    break;
//                }
//                case R.id.action_map: {
//                    replaceFragment(new MapFragment());
//                    break;
//                }
//                case R.id.action_profile: {
//                    replaceFragment(new ProfileFragment());
//                    break;
//                }
//                case R.id.action_settings: {
//                    replaceFragment(new SettingsFragment());
//                    break;
//                }
//
//                default: {
//                    replaceFragment(new HomeFragment());
//                    break;
//                }
//            }
            return true;
        });
    }



    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}