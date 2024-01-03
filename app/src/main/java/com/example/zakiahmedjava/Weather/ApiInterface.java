package com.example.zakiahmedjava.Weather;

import com.example.zakiahmedjava.News.MainNewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("weather")
    Call<WeatherApp> getWeatherData (
        @Query("q") String city,
        @Query("appid") String appid,
        @Query("units") String units
    );

    @GET("top-headlines") Call<MainNewsModel> getNews(@Query("country") String country, @Query("pageSize") int pageSize, @Query("apiKey") String apiKey);

    @GET("top-headlines") Call<MainNewsModel> getCategoryNews (@Query("country") String country, @Query("category") String category, @Query("pageSize") int pageSize, @Query("apiKey") String apiKey);

}
