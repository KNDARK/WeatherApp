package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.weatherapp.Adapters.WeatherAdapter;
import com.example.weatherapp.Api.ApiCityService;
import com.example.weatherapp.Api.ApiService;
import com.example.weatherapp.Controllers.Controller;
import com.example.weatherapp.Models.WeatherModel;
import com.example.weatherapp.Models.WeathersModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView tvTemperature, tvDescription, tvHumidity, tvWindSpeed, tvLocation;
    Button btnGetWeather;
    EditText edtLocation;
    ImageView imvIconWeather;
    RecyclerView rcvWeatherHour;
    WeatherAdapter weatherAdapter;
    ArrayList<WeatherModel> weathers = new ArrayList<WeatherModel>();
    Toolbar toolbars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLocation = findViewById(R.id.tv_location);

        tvTemperature = findViewById(R.id.tv_temperature);
        tvDescription = findViewById(R.id.tv_description);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvWindSpeed = findViewById(R.id.tv_wind_speed);
        edtLocation = findViewById(R.id.edt_location);
        btnGetWeather = findViewById(R.id.btn_get_weather);
        imvIconWeather = findViewById(R.id.imv_icon_weather);

        rcvWeatherHour = findViewById(R.id.rcv_weather_hour);
        weatherAdapter = new WeatherAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvWeatherHour.setLayoutManager(linearLayoutManager);
        rcvWeatherHour.setAdapter(weatherAdapter);
        getWeather("Thành phố Đà Nẵng");
        Controller ct = new Controller();
        ct.get_weather_hour("Thành phố Đà Nẵng", weathers, weatherAdapter, this);


        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeather(edtLocation.getText().toString());
            }
        });

    }

    public void getWeather(String location) {
        Controller x = new Controller();
        String locationConvert = x.convert_city(location);
        ApiService.apiService.getWeather(locationConvert, ApiService.KEY_ID, ApiService.LANGUAGE).enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                WeatherModel weather = response.body();
                if (weather != null && weather.cod == 200) {
                    Log.d("@@@@", "OK: "+weather.name);
                    tvLocation.setText(weather.name);
                    String pic = "https://openweathermap.org/img/wn/" + weather.weather.get(0).icon +"@2x.png";
                    Glide.with(getApplicationContext())
                            .load(pic)
                            .fitCenter()
                            .error(R.drawable.cloud_default)
                            .into(imvIconWeather);
                    int temp = (int) (Double.parseDouble(String.valueOf(weather.main.temp)) - 273.15);
                    String temperature = temp + "°C";
                    tvTemperature.setText(temperature);
                    tvDescription.setText(weather.weather.get(0).description);
                    tvHumidity.setText(String.valueOf(weather.main.humidity));
                    tvWindSpeed.setText(String.valueOf(weather.wind.speed));

                }
                else {
                    Toast.makeText(MainActivity.this, R.string.location_notfound, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
