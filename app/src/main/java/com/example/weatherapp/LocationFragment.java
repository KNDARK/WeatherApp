package com.example.weatherapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.Adapters.CityAdapter;
import com.example.weatherapp.Api.ApiCityService;
import com.example.weatherapp.Models.CityModel;
import com.example.weatherapp.Models.results;
import com.example.weatherapp.databinding.FragmentLocationBinding;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment {
    FragmentLocationBinding binding;
    CityAdapter cityAdapter;
    ArrayList<results> listData = new ArrayList<results>();
    RecyclerView rcvCity;
    ArrayList<results> filter = new ArrayList<results>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_icon));
        ((MainActivity)getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("@@@@@", "okcccc ");
                ((MainActivity)getActivity()).refresh_toolbar();
                ((MainActivity)getActivity()).call_back(LocationFragment.this);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        cityAdapter = new CityAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rcvCity.setLayoutManager(linearLayoutManager);
        binding.rcvCity.setAdapter(cityAdapter);
        getAllCity();
        binding.edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter = new ArrayList<results>();
                if (!binding.edtInput.getText().toString().equals("")){
                    for(results x : listData){
                        if (x.name.toLowerCase(Locale.ROOT).contains(
                                binding.edtInput.getText().toString().toLowerCase(Locale.ROOT))) filter.add(x);
                        else filter.remove(x);
                    }
                    cityAdapter.setData(filter);
                }
                else cityAdapter.setData(listData);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }
    public void getAllCity(){
        ApiCityService.apiCityService.getCity().enqueue(new Callback<CityModel>() {
            @Override
            public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                CityModel dataGet = response.body();
                Log.d("@@@@@", "xxx");
                if (dataGet != null && dataGet.status.equals("Success")){
                    listData = dataGet.results;
                    cityAdapter.setData(listData);
                    Log.d("@@@@@", "City 2");
                }
            }

            @Override
            public void onFailure(Call<CityModel> call, Throwable t) {
                Log.d("@@@@@", "City 3");
            }
        });
    }
}