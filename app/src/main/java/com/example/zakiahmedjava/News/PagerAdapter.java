package com.example.zakiahmedjava.News;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    protected int tabCount;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount = behavior;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                Log.d("viewPager", "General New Fragment changed");
                return new GeneralNewsFragment();

            }
            case 1:
                return new HealthFragment();
            case 2:
                return new EntertainmetFragment();
            case 3:
                return new ScienceFragment();
            case 4:
                return new SportsFragment();
            case 5:
                return new TechnologyFragment();
            default: {
                Log.d("PageAdapter", "Default block executed");
                return new GeneralNewsFragment();
            }
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
