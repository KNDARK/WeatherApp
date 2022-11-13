package com.example.weatherapp.Controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.weatherapp.Adapters.WeatherAdapter;
import com.example.weatherapp.Api.ApiService;
import com.example.weatherapp.Models.WeatherModel;
import com.example.weatherapp.Models.WeathersModel;
import com.example.weatherapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Controller {
    public void get_weather_hour(String location, ArrayList<WeatherModel> weathers, WeatherAdapter weatherAdapter, Context context){
        String locationConvert = this.convert_city(location);
        ApiService.apiService.getListWeather(locationConvert, ApiService.KEY_ID, ApiService.LANGUAGE).enqueue(new Callback<WeathersModel>() {
            @Override
            public void onResponse(Call<WeathersModel> call, Response<WeathersModel> response) {
                WeathersModel weather = response.body();
                if (weather != null && weather.cod.equals("200")) {
                    for (WeatherModel i : weather.list){
                        weathers.add(i);
                        Log.d("@@@@", "onResponse: @@@@");
                    }
                    weatherAdapter.setData(weathers);
                }
                else {
                    Toast.makeText( context , R.string.location_notfound, Toast.LENGTH_SHORT).show();
                    Log.d("@@@@", "Error");
                }
            }

            @Override
            public void onFailure(Call<WeathersModel> call, Throwable t) {
                Log.d("@@@@", "Error @@@");
                Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String convert_city(String city){
        return city.contains("Tỉnh") ? city.replace("Tỉnh ","") : city.replace("Thành phố ","");
    }
}
