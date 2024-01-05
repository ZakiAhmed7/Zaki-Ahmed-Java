package com.example.zakiahmedjava.News;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.RecyclerViewInterface;
import com.example.zakiahmedjava.RetrofitBuilder;
import com.example.zakiahmedjava.WebscreenFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScienceFragment extends Fragment implements RecyclerViewInterface {
    protected String apiKey = "5836128d584d4cab9c1a3b090dc62980",
            countryCode = "in",
            category = "science";
    protected RecyclerView recyclerView;
    protected ArrayList<NewsModel> newsList;
    protected NewsRecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_science, container, false);

        initViews(view);
        newsList = new ArrayList<>();

        setAdapter();

        fetchNewsFromAPI();

        return view;
    }

    private void fetchNewsFromAPI() {
        RetrofitBuilder builder = new RetrofitBuilder();
        builder.initializeNewsRetrofit().getCategoryNews(countryCode, category, 100, apiKey).enqueue(new Callback<MainNewsModel>() {
            @Override
            public void onResponse(Call<MainNewsModel> call, Response<MainNewsModel> response) {
                if (response.isSuccessful()) {
                    newsList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MainNewsModel> call, Throwable t) {

            }
        });
    }

    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsRecyclerViewAdapter(getContext(), newsList, this);
        recyclerView.setAdapter(adapter);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewScience);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("URL", newsList.get(position).getUrl());

        WebscreenFragment webscreenFragment = new WebscreenFragment();
        webscreenFragment.setArguments(bundle);

        getParentFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainerView, webscreenFragment)
                .addToBackStack("newsDetails")
                .commit();
    }
}