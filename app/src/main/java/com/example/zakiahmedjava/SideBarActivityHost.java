package com.example.zakiahmedjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.zakiahmedjava.TodoWithRoom.MainTodoFragment;
import com.example.zakiahmedjava.databinding.ActivitySideBarHostBinding;

public class SideBarActivityHost extends AppCompatActivity {
    protected ActivitySideBarHostBinding binding;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_bar_host);

        replaceFragment(new MainTodoFragment());




    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.sideFrameLayout, fragment);
        fragmentTransaction.commit();
    }
}