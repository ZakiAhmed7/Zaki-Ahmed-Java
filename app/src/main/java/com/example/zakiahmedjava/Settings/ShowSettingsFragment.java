package com.example.zakiahmedjava.Settings;

import static com.example.zakiahmedjava.R.id.fragmentContainerView;

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
        binding = FragmentShowSettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String name = sharedPreferences.getString("prefName", "");
        String email = sharedPreferences.getString("prefEmail", "");
        String category = sharedPreferences.getString("prefCategory", "");
        boolean mode = sharedPreferences.getBoolean("switchTheme", false);
        boolean liked = sharedPreferences.getBoolean("checkBoxLike",false);

        binding.etSName.setText("Name: "+name);
        binding.etSEmail.setText("Email: "+email);
        binding.etSCategory.setText("Category: "+category);
        if (mode)
            binding.etSDarkMode.setText("Applied Theme: Dark Mode");
        else
            binding.etSDarkMode.setText("Applied Theme: Light Mode");

        if (liked)
            binding.etSLiked.setText("You liked the application");
        else
            binding.etSLiked.setText("You did not like the application");


        binding.fabEdit.setOnClickListener(v -> {

            getParentFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainerView, new SettingsPreferenceFragment())
                    .commit();
        });
        return view;
    }
}
