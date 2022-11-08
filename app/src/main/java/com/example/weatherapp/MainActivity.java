package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.weatherapp.Api.ApiService;
import com.example.weatherapp.Models.WeatherModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView txtWeather;
    Button btnGetWeather;
    EditText edtLocation;
    Toolbar toolbars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtWeather = findViewById(R.id.txt_weather);
        edtLocation = findViewById(R.id.edt_location);
        btnGetWeather = findViewById(R.id.btn_get_weather);
        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeather(edtLocation.getText().toString());
            }
        });
        getWeather("Đà Nẵng");
    }

    public void getWeather(String location) {
        ApiService.apiService.getWeather(location, ApiService.KEY_ID).enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                WeatherModel weather = response.body();
                if (weather != null && weather.cod != 404) {
                    String text = weather.weather.get(0).description + "\n"
                            + weather.weather.get(0).icon + "\n"
                            + "Tốc độ gió: "+ weather.wind.speed + "\n"
                            + "Nhiệt độ: "+ weather.main.temp + "\n"
                            + "Quốc gia: "+ weather.sys.country + "\n"
                            + "Thành phố: "+ weather.name + "\n";
                    txtWeather.setText(text);
                }
                else {
                    txtWeather.setText(R.string.location_notfound);
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
