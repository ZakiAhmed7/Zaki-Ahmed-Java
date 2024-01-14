package com.example.zakiahmedjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zakiahmedjava.HomeFragment.HomeFragment;
import com.example.zakiahmedjava.Map.MapsFragment;
import com.example.zakiahmedjava.News.NewsFragment;
import com.example.zakiahmedjava.Settings.SettingsFragment;
import com.example.zakiahmedjava.TodoWithRoom.MainTodoFragment;
import com.example.zakiahmedjava.Weather.WeatherFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FragmentContainerView fragmentView;
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView sideNavigationView;
    AppBarConfiguration configuration;
    int blackColor = 000;
    ActionBarDrawerToggle actionBarDrawerToggle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        sideNavigationViewImplementation();

        bottomAppBar.setBackgroundTint(ColorStateList.valueOf(Color.alpha(blackColor)));

        bottomAppBar.setBackgroundTint(ColorStateList.valueOf(Color.rgb(000, 000, 000)));

        replaceFragment(new HomeFragment());



        bottomNavigationView.setOnItemSelectedListener( item -> {
            if (item.getItemId() == R.id.action_home)
                replaceFragment(new HomeFragment());
             else if (item.getItemId() == R.id.action_weather)
                replaceFragment(new WeatherFragment());
//            else if (item.getItemId() == R.id.action_map)
//                replaceFragment(new MapsFragment());
            else if (item.getItemId() == R.id.action_news)
                replaceFragment(new NewsFragment());
             else if (item.getItemId() == R.id.action_settings)
                replaceFragment(new SettingsFragment());


            return true;
        });
    }

    private void sideNavigationViewImplementation() {
        drawerLayout.setBackgroundColor(blackColor);
        //setting up toolbar for navigation
        setSupportActionBar(toolbar);
        // Adding items in the side navigation

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        sideNavigationView.setBackground(null);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sideNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.side_nav_action_room_db)
                    startActivity(new Intent(MainActivity.this, SideBarActivityHost.class));
//                    replaceFragment(new MainTodoFragment());
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        return NavigationUI.navigateUp(navController, configuration) || super.onSupportNavigateUp();
    }

    private void initViews() {
        fragmentView = findViewById(R.id.fragmentContainerView);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        toolbar = findViewById(R.id.mainToolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        sideNavigationView = findViewById(R.id.sideNavigationView);
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.side_nav_action_room_db) {
            startActivity(new Intent(MainActivity.this, SideBarActivityHost.class));
        }
        return false;
    }

    @Override
    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START))
//            drawerLayout.closeDrawer(GravityCompat.START);
//        else
            super.onBackPressed();
    }
}