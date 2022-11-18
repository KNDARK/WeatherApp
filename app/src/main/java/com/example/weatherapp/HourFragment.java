package com.example.weatherapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherapp.Adapters.HourAdapter;
import com.example.weatherapp.Adapters.WeatherAdapter;
import com.example.weatherapp.Api.ApiService;
import com.example.weatherapp.Controllers.Controller;
import com.example.weatherapp.Models.WeatherModel;
import com.example.weatherapp.Models.WeathersModel;
import com.example.weatherapp.databinding.FragmentHourBinding;
import com.example.weatherapp.databinding.FragmentLoginBinding;
import com.example.weatherapp.databinding.FragmentNowBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourFragment extends Fragment {
    FragmentHourBinding binding;
    ArrayList<WeatherModel> weathers = new ArrayList<WeatherModel>();
    HourAdapter HourAdapter;

    public HourFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHourBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        HourAdapter = new HourAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rcvShow.setLayoutManager(linearLayoutManager);
        binding.rcvShow.setAdapter(HourAdapter);
        get_weather_hour("Thành phố Đà Nẵng");
        Log.d("@@@@@@", "onCreateView: ");
        return view;
    }

    public void get_weather_hour(String location){
        Controller action = new Controller();
        String locationConvert = action.convert_city(location);
        ApiService.apiService.getListWeather(locationConvert, ApiService.KEY_ID, ApiService.LANGUAGE).enqueue(new Callback<WeathersModel>() {
            @Override
            public void onResponse(Call<WeathersModel> call, Response<WeathersModel> response) {
                WeathersModel weather = response.body();
                if (weather != null && weather.cod.equals("200")) {
                    int count = 0;
                    for (WeatherModel i : weather.list){
                        weathers.add(i);
                    }
                    HourAdapter.setData(weathers);
                }
                else {
                    Toast.makeText(getContext() , R.string.location_notfound, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeathersModel> call, Throwable t) {
                Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}