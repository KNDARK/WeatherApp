package com.example.weatherapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.Controllers.Controller;
import com.example.weatherapp.Models.WeatherModel;
import com.example.weatherapp.databinding.FragmentHourBinding;
import com.example.weatherapp.databinding.FragmentItemDetailBinding;

public class ItemDetailFragment extends Fragment {
    FragmentItemDetailBinding binding;
    WeatherModel data;

    public ItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_icon));
        if (getArguments() != null) {

        }
        data = ((MainActivity)getActivity()).dataSend;
        ((MainActivity)getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("@@@@@", "okcccc ");
                ((MainActivity)getActivity()).refresh_toolbar();
                ((MainActivity)getActivity()).call_back(ItemDetailFragment.this);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentItemDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.itemHour.setText(data.dt_txt.split(" ")[1]);
        binding.itemHumidity.setText(String.valueOf(data.main.humidity));
        Controller action = new Controller();
        binding.itemTemp.setText(action.prc_temp(data.main.temp));
        binding.itemWind.setText(String.valueOf(data.wind.speed));
        binding.itemDescription.setText(data.weather.get(0).description);
        return view;
    }
}