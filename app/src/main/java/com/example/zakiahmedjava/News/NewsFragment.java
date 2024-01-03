package com.example.zakiahmedjava.News;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.RecyclerViewInterface;
import com.example.zakiahmedjava.databinding.FragmentNewsBinding;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements RecyclerViewInterface {

//    protected FragmentNewsBinding binding;
    protected TabLayout tabLayout;
    protected TabItem generalNewsTabItem, healthTabItem, entertainmentTabItem, scienceTabItem, sportsTabItem, technologyTabItem;
    protected ViewPager viewPager;

    protected ArrayList<NewsModel> newsList;

    protected String API_KEY = "5836128d584d4cab9c1a3b090dc62980";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
//        binding = FragmentNewsBinding.bind(view);
        initViews(view);
        PagerAdapter pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), 6);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                Log.d("viewPager", "General New Fragment changed");
                if (tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2 || tab.getPosition() == 3 || tab.getPosition() == 4 || tab.getPosition() == 5 )
                    pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return view;
    }

    private void initViews(View view) {
        tabLayout = view.findViewById(R.id.tabLayout);
        generalNewsTabItem = view.findViewById(R.id.generalTabItem);
        healthTabItem = view.findViewById(R.id.healthTabItem);
        entertainmentTabItem = view.findViewById(R.id.entertainmentTabItem);
        scienceTabItem = view.findViewById(R.id.scienceTabItem);
        sportsTabItem = view.findViewById(R.id.sportsTabItem);
        technologyTabItem = view.findViewById(R.id.technologyTabItem);
        viewPager = view.findViewById(R.id.viewPager);
    }

    @Override
    public void onItemClick(int position) {

    }
}