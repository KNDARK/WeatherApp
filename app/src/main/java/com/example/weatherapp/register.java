package com.example.weatherapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherapp.Api.ApiUserService;
import com.example.weatherapp.Models.UserModel;
import com.example.weatherapp.databinding.FragmentLocationBinding;
import com.example.weatherapp.databinding.FragmentRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class register extends Fragment {
    FragmentRegisterBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).hidden_auth();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setFragment(new Login());
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiUserService.apiUserService.register(binding.edtEmail.getText().toString(),
                        binding.edtPassword.getText().toString(),binding.edtName.getText().toString(),
                        binding.edtLocation.getText().toString()).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        Log.d("@@@@", "load: ");
                        UserModel user = response.body();
                        if (user != null && user.status){
                            ((MainActivity)getActivity()).setFragment(new Login());
                            Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_LONG).show();
                        }
                        else Toast.makeText(getContext(), "Email đã tồn tại!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi server đăng ký !", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}