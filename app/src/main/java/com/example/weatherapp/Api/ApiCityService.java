package com.example.weatherapp.Api;

import com.example.weatherapp.Models.CityModel;
import com.example.weatherapp.Models.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCityService {
    // Api thành phố: https://api.mysupership.vn/v1/partner/areas/province

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://api.mysupership.vn/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("v1/partner/areas/province")
    Call<CityModel> getCity();
}
