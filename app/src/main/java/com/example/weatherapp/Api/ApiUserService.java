package com.example.weatherapp.Api;

import com.example.weatherapp.Models.CityModel;
import com.example.weatherapp.Models.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiUserService {
    // Api User:
    // Login: https://iloveyoubabe99.000webhostapp.com/getUser.php param: email,password
    // register: https://iloveyoubabe99.000webhostapp.com/register.php param: email,password,name,location

    Gson gson3 = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiUserService apiUserService = new Retrofit.Builder()
            .baseUrl("https://iloveyoubabe99.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson3))
            .build()
            .create(ApiUserService.class);

    @GET("getUser.php")
    Call<UserModel> getUser(@Query("email")String email, @Query("password")String password);

    @GET("register.php")
    Call<UserModel> register(@Query("email")String email,
                             @Query("password")String password,
                             @Query("name")String name,
                             @Query("location")String location);
}
