package com.example.zakiahmedjava.Weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.zakiahmedjava.ApiInterface;
import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.RetrofitBuilder;
import com.example.zakiahmedjava.databinding.FragmentWeatherBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherFragment extends Fragment {

    // cdd3f5aea1434bd19179afd246c4ea51

    // https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}

    private FragmentWeatherBinding binding;

    private String cityName,temperature, dayName, dayDate, location, maxTemp, minTemp, weatherCondition, windSpeed, sunRise, sunSet, humidity, seaLevel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        binding = FragmentWeatherBinding.bind(view);
        cityName = "Waterloo";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                fetchWeatherData(cityName);
            }
        });

        thread.run();

        binding.weatherSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    query.trim();
                    fetchWeatherData(query);
                    return true;
                } else {
                    Toast.makeText(getContext(), "Please Enter proper city name", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });


        return view;
    }

    private String getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(new Date());
    }

    private String getTime(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void fetchWeatherData(String city) {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

        ApiInterface retrofit = retrofitBuilder.initializeWeatherRetrofit();

        Call<WeatherApp> response = retrofit.getWeatherData(city, "cdd3f5aea1434bd19179afd246c4ea51", "metric");

        response.enqueue(new Callback<WeatherApp>() {
            @Override
            public void onResponse(Call<WeatherApp> call, Response<WeatherApp> response) {
                if (response.isSuccessful())
                    gotResponseAndDisplayData(response, city);
                else
                    Toast.makeText(getContext(), "Response cannot convert", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<WeatherApp> call, Throwable t) {

            }
        });


    }

    private void gotResponseAndDisplayData(Response<WeatherApp> response, String city) {
        WeatherApp responseBody = response.body();

        int temp = (int) Math.round(responseBody.getWeatherAppMain().getTemp());
        int minTemperature = (int) Math.round(responseBody.getWeatherAppMain().getTemp_min());
        int maxTemperature = (int) Math.round(responseBody.getWeatherAppMain().getTemp_max());


        temperature = String.valueOf(responseBody.getWeatherAppMain().getTemp());
        minTemp = String.valueOf(responseBody.getWeatherAppMain().getTemp_min());
        maxTemp = String.valueOf(responseBody.getWeatherAppMain().getTemp_max());
        windSpeed = String.valueOf(responseBody.getWind().getSpeed());
        sunRise = getTime(String.valueOf(responseBody.getSys().getSunrise()));
        sunSet = getTime(String.valueOf(responseBody.getSys().getSunset()));
        seaLevel = String.valueOf(responseBody.getWeatherAppMain().getPressure());
        humidity = String.valueOf(responseBody.getWeatherAppMain().getHumidity());
        List<Weather> weather = responseBody.getWeather();
//        Optional<Weather> we = weather.stream().filter(w -> w != null).findFirst();
//        Log.d("Condition", " Condition List: "+we);
        Log.d("Condition", "0 Index: "+responseBody.getWeather().stream().findFirst().get().getMain());
        weatherCondition = String.valueOf(responseBody.getWeather().stream().findFirst().get().getMain());

        binding.tvTemp.setText(temp+"°C");
        binding.tvMaxTemp.setText("Max: "+maxTemperature+"°C");
        binding.tvMinTemp.setText("Min: "+minTemperature+"°C");
        binding.tvWindSpeed.setText(windSpeed+" m/s");
        binding.tvSunRise.setText(sunRise);
        binding.tvSunSet.setText(sunSet);
        binding.tvHumidity.setText(humidity+" %");
        binding.tvSeaLevel.setText(seaLevel+" hPa");
        binding.tvLocation.setText(city);
        binding.tvDayName.setText(getDay());
        binding.tvTodayDate.setText(getDate());
        binding.tvWeatherCondition.setText(weatherCondition);
        binding.tvWeather.setText(weatherCondition);

        checkWeatherCondition(weatherCondition);
    }

    private void checkWeatherCondition(String weatherCondition) {
        if (weatherCondition == "Clear Sky" || weatherCondition == "Sunny" || weatherCondition == "Clear") {
            binding.weatherConstraintLayout.setBackgroundResource(R.drawable.sunny_background);
            binding.animatedImage.setAnimation(R.raw.sun);
        }
        else if (weatherCondition == "Partly Clouds" || weatherCondition == "Clouds" || weatherCondition == "Overcast" || weatherCondition == "Mist" || weatherCondition == "Foggy") {
            binding.weatherFrameLayout.setBackgroundResource(R.drawable.colud_background);
            binding.weatherConstraintLayout.setBackgroundResource(R.drawable.colud_background);
            binding.animatedImage.setAnimation(R.raw.cloud);
        }
        else if (weatherCondition == "Light Rain" || weatherCondition == "Drizzle" || weatherCondition == "Moderate Rain" || weatherCondition == "Showers" || weatherCondition == "Heavy Rain") {
            binding.weatherConstraintLayout.setBackgroundResource(R.drawable.rain_background);
            binding.animatedImage.setAnimation(R.raw.rain);
        }
        else if (weatherCondition == "Light Snow" || weatherCondition == "Moderate Snow" || weatherCondition == "Heavy Snow" || weatherCondition == "Bilzzard") {
            binding.weatherConstraintLayout.setBackgroundResource(R.drawable.snow_background);
            binding.animatedImage.setAnimation(R.raw.snow);
        } else {
            binding.weatherConstraintLayout.setBackgroundResource(R.color.white);
            binding.animatedImage.setAnimation(R.raw.cloud);
        }
        binding.animatedImage.playAnimation();

    }

}