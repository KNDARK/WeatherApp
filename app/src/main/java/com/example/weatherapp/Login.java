package com.example.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherapp.Api.ApiUserService;
import com.example.weatherapp.Controllers.Controller;
import com.example.weatherapp.Models.UserModel;
import com.example.weatherapp.databinding.FragmentLoginBinding;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {
    FragmentLoginBinding binding;
    private final String fileName = "login.txt";
    private final String filePath = "data";
    File myInternalFile;
    ProgressDialog loading;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading = new ProgressDialog(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((MainActivity)getActivity()).hidden_auth();
        String myData = "";
        ContextWrapper contextWrapper = new ContextWrapper(getContext());
        File directory = contextWrapper.getDir(filePath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, fileName);
        ((MainActivity)getActivity()).layoutMain.setBackground(getResources().getDrawable(R.drawable.background_default));
        ((MainActivity)getActivity()).hidden_auth();
        try {
            FileInputStream fis = new FileInputStream(myInternalFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData += strLine + "\n";
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("@@@@@", "mydata: "+myData);
        String[] data = myData.split("\n");
        if(!myData.equals("") && data.length >= 4){
            loading.show();
            ApiUserService.apiUserService.getUser(data[0], data[1]).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    UserModel user = response.body();
                    if (user != null && user.status){
                        ((MainActivity)getActivity()).setFragment( ((MainActivity)getActivity()).fgNow);
                        ((MainActivity)getActivity()).user = user;
                        ((MainActivity)getActivity()).set_info();
                        ((MainActivity)getActivity()).set_view_location(user.getLocation());
                        loading.hide();
                    }
                    else {
                        Log.d("@@@@@", "else: ");
                        loading.hide();
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Log.d("@@@@@", "back: ");
                    loading.hide();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).layoutMain.setBackground(getResources().getDrawable(R.drawable.register_backgroud));
                ((MainActivity)getActivity()).setFragment(new register());
            }
        });
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.show();
                ApiUserService.apiUserService.getUser(binding.edtEmail.getText().toString(), binding.edtPassword.getText().toString()).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        UserModel user = response.body();
                        if (user != null && user.status){
                            Toast.makeText(getContext(), "Success login", Toast.LENGTH_SHORT).show();
                            try {
                                if (binding.cbRemember.isChecked()){
                                    FileOutputStream fos = new FileOutputStream(myInternalFile);
                                    String textSave = user.getEmail() + "\n" + user.getPassword() + "\n" + user.getName() + "\n" + user.getLocation();
                                    fos.write(textSave.getBytes());
                                    fos.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            ((MainActivity)getActivity()).setFragment(((MainActivity)getActivity()).fgNow);
                            ((MainActivity)getActivity()).user = user;
                            ((MainActivity)getActivity()).set_view_location(user.getLocation());
                            Toast.makeText(getContext(), ""+user.getLocation(), Toast.LENGTH_SHORT).show();
                            ((MainActivity)getActivity()).set_info();
                            loading.hide();
                        }
                        else {
                            loading.hide();
                            Toast.makeText(getContext(), "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed login", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}