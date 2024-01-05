package com.example.zakiahmedjava.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.databinding.FragmentShowSettingsBinding;

public class ShowSettingsFragment extends Fragment {

    protected FragmentShowSettingsBinding binding;
    protected SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_settings, container);
        binding = FragmentShowSettingsBinding.bind(view);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String name = sharedPreferences.getString("", "");
        String email = sharedPreferences.getString("", "");
        String category = sharedPreferences.getString("", "");
        boolean mode = sharedPreferences.getBoolean("", false);
        boolean liked = sharedPreferences.getBoolean("",false);

        binding.fabEdit.setOnClickListener(v -> {

        });
        return view;
    }
}
