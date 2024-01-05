package com.example.zakiahmedjava;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    public ApiInterface initializeWeatherRetrofit() {

        ApiInterface retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .build().create(ApiInterface.class);

        return retrofit;
    }

    public ApiInterface initializeNewsRetrofit() {

        ApiInterface retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://newsapi.org/v2/")
                .build()
                .create(ApiInterface.class);

        return retrofit;
    }

}
