package com.example.weatherapp.Api;


import com.example.weatherapp.Models.WeatherModel;
import com.example.weatherapp.Models.WeathersModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    // api image :  http://openweathermap.org/img/wn/10d@2x.png
    // api 1 weather: https://api.openweathermap.org/data/2.5/weather?q=Hanoi&appid=32f70b1ec05577d8f0a6759b421515d7
    // api list weather: https://api.openweathermap.org/data/2.5/forecast?q=Hanoi&appid=32f70b1ec05577d8f0a6759b421515d7
    final String KEY_ID = "32f70b1ec05577d8f0a6759b421515d7";
    final String LANGUAGE = "vi";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("data/2.5/weather")
    Call<WeatherModel> getWeather(@Query("q") String q, @Query("appid") String appid, @Query("lang") String lang);

    @GET("data/2.5/forecast")
    Call<WeathersModel> getListWeather(@Query("q") String q, @Query("appid") String appid, @Query("lang") String lang);
}
