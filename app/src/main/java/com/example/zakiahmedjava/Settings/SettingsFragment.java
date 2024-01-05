package com.example.zakiahmedjava.Settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    protected FragmentSettingsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        binding = FragmentSettingsBinding.bind(view);

        binding.profileLayout.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentContainerView, new ProfileFragment())
                    .addToBackStack("settingsFragment")
                    .commit();
        });

        binding.settingsLayout.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainerView, new ShowSettingsFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack("showSettingsFragment")
                    .commit();
        });

        return view;
    }
}