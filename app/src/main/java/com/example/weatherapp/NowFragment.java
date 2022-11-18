package com.example.weatherapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weatherapp.Adapters.WeatherAdapter;
import com.example.weatherapp.Api.ApiService;
import com.example.weatherapp.Controllers.Controller;
import com.example.weatherapp.Models.WeatherModel;
import com.example.weatherapp.Models.WeathersModel;
import com.example.weatherapp.databinding.FragmentNowBinding;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class NowFragment extends Fragment {
    FragmentNowBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<WeatherModel> weathers = new ArrayList<WeatherModel>();
    WeatherAdapter weatherAdapter;
    public boolean run = false;
    int threadCount = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowFragment newInstance(String param1, String param2) {
        NowFragment fragment = new NowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NowFragment() {
        // Required empty public constructor
    }

    public void getWeather(String location) {
        Controller x = new Controller();
        String locationConvert = x.convert_city(location);
        ApiService.apiService.getWeather(locationConvert, ApiService.KEY_ID, ApiService.LANGUAGE).enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                Locale locale = new Locale("vi", "VN");
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
                String date = dateFormat.format(new Date());

                Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                WeatherModel weather = response.body();
                if (weather != null && weather.cod == 200) {
                    Log.d("@@@@", "OK: "+weather.name);
                    ((MainActivity)getActivity()).toolbar.setTitle(weather.name);
                    String pic = "https://openweathermap.org/img/wn/" + weather.weather.get(0).icon +"@2x.png";
                    Glide.with(getContext())
                            .load(pic)
                            .fitCenter()
                            .error(R.drawable.cloud_default)
                            .into(binding.imvIconWeather);
                    int temp = (int) (Double.parseDouble(String.valueOf(weather.main.temp)) - 273.15);
                    String temperature = temp + "°C";
                    binding.tvDate.setText(date);
                    binding.tvTemperature.setText(temperature);
                    binding.tvDescription.setText(weather.weather.get(0).description);
                    binding.tvHumidity.setText(String.valueOf(weather.main.humidity));
                    binding.tvWindSpeed.setText(String.valueOf(weather.wind.speed));

                }
                else {
                    Toast.makeText(getContext(), R.string.location_notfound, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
            }
        });
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
                        if (count < 6) weathers.add(i);
                        else break;
                        count++;
                    }
                    weatherAdapter.setData(weathers);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((MainActivity)getActivity()).show_auth();
        ((MainActivity)getActivity()).layoutMain.setBackground(getResources().getDrawable(R.drawable.background_main));
    }

    public void worker(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (run){
                    try {
                        threadCount++;
                        getWeather("Thành phố Đà Nẵng");
                        get_weather_hour("Thành phố Đà Nẵng");
                        Log.d("@@@@", "run: "+ threadCount);
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Binding view
        binding = FragmentNowBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        // Inflate the layout for this fragment
        weatherAdapter = new WeatherAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.rcvWeatherHour.setLayoutManager(linearLayoutManager);
        binding.rcvWeatherHour.setAdapter(weatherAdapter);
        getWeather("Thành phố Đà Nẵng");
        get_weather_hour("Thành phố Đà Nẵng");

        binding.btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeather(binding.edtLocation.getText().toString());
            }
        });
        worker();
        return view;
    }
}