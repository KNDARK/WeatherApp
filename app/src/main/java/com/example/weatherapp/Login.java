package com.example.weatherapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherapp.Controllers.Controller;
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
    String myData = "";
    public boolean checkLogin = false;
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((MainActivity)getActivity()).hidden_auth();
        if (((MainActivity)getActivity()).check_login())
            ((MainActivity)getActivity()).setFragment(new NowFragment());
        else {
            ((MainActivity)getActivity()).layoutMain.setBackground(getResources().getDrawable(R.drawable.background_default));
            ContextWrapper contextWrapper = new ContextWrapper(getContext());
            File directory = contextWrapper.getDir(filePath, Context.MODE_PRIVATE);
            myInternalFile = new File(directory, fileName);
            ((MainActivity)getActivity()).hidden_auth();
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
                Controller action = new Controller();
                checkLogin = action.check_login(binding.edtEmail.getText().toString(), binding.edtPassword.getText().toString());
                if (checkLogin){
                    Toast.makeText(getContext(), "Success login", Toast.LENGTH_SHORT).show();
                    try {
                        FileOutputStream fos = new FileOutputStream(myInternalFile);
                        fos.write("OKKKKK".getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ((MainActivity)getActivity()).setFragment(new NowFragment());
                }
                else Toast.makeText(getContext(), "Failed login", Toast.LENGTH_SHORT).show();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}