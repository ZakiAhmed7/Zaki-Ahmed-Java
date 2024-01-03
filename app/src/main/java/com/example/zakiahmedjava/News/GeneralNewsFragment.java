package com.example.zakiahmedjava.News;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.RecyclerViewInterface;
import com.example.zakiahmedjava.RetrofitBuilder;
import com.example.zakiahmedjava.Weather.ApiInterface;
import com.google.android.gms.common.AccountPicker;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralNewsFragment extends Fragment implements RecyclerViewInterface {

    private String apiKey = "5836128d584d4cab9c1a3b090dc62980";
    private RecyclerView recyclerView;
    private ArrayList<NewsModel> newsList;

    private NewsRecyclerViewAdapter newsAdapter;

    private String countryCode = "in";

    private TextView tvError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_news, container, false);
        Log.d("viewPager", "General Fragment Executed");
        initViews(view);
        newsList = new ArrayList<>();

        setAdapter();

        fetchNewsFromAPI();

        return view;

    }

    private void fetchNewsFromAPI() {
        RetrofitBuilder builder = new RetrofitBuilder();

        ApiInterface apiInterface = builder.initializeNewsRetrofit();
        apiInterface.getNews(countryCode, 100, apiKey).enqueue(new Callback<MainNewsModel>() {
            @Override
            public void onResponse(Call<MainNewsModel> call, Response<MainNewsModel> response) {
                if (response.isSuccessful()) {
                    newsList.addAll(response.body().getArticles());
                    newsAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<MainNewsModel> call, Throwable t) {

            }
        });

    }

    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsAdapter = new NewsRecyclerViewAdapter(getContext(), newsList, this);
        recyclerView.setAdapter(newsAdapter);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewGeneral);
        tvError = view.findViewById(R.id.tv_error_general);
    }

    @Override
    public void onItemClick(int position) {

    }
}