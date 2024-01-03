package com.example.zakiahmedjava.HomeFragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.WebscreenFragment;

public class HomeFragment extends Fragment {
    ImageButton linkedInButton, githubButton, myWebsiteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        linkedInButton = view.findViewById(R.id.linkedIn_btn);
        githubButton = view.findViewById(R.id.github_btn);
        myWebsiteButton = view.findViewById(R.id.instagram_btn);

        WebscreenFragment webscreenFragment = new WebscreenFragment();
        Bundle bundle = new Bundle();

        linkedInButton.setOnClickListener(v -> {
            bundle.putString("URL","https://www.linkedin.com/in/zakiahmed7/");
            webscreenFragment.setArguments(bundle);
            if (savedInstanceState == null) {
                getParentFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragmentContainerView, webscreenFragment)
                        .addToBackStack("main")
                        .commit();
            }
        });

        githubButton.setOnClickListener(v -> {
            bundle.putString("URL", "https://github.com/ZakiAhmed7");
            webscreenFragment.setArguments(bundle);
            if (savedInstanceState == null) {
                getParentFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragmentContainerView, webscreenFragment)
                        .addToBackStack("main")
                        .commit();
            }
        });

        myWebsiteButton.setOnClickListener(v -> {
            bundle.putString("URL", "https://zakiahmed7.github.io/");
            webscreenFragment.setArguments(bundle);
            if (savedInstanceState == null) {
                getParentFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragmentContainerView, webscreenFragment)
                        .addToBackStack("main")
                        .commit();
            }
        });

        return view;
    }

}