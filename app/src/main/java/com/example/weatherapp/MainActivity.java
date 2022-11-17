package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public LinearLayout layoutMain;
    private final String fileName = "login.txt";
    private final String filePath = "data";
    public boolean isRunning = true;
    public BottomNavigationView bottomNav;
    public FrameLayout layout;
    File myInternalFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_nav);
        layout = findViewById(R.id.fragment);
        layoutMain = findViewById(R.id.layout_main);
        setFragment(new Login());
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir(filePath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, fileName);
    }

    protected void setFragment(Fragment fragment) {
        bottomNav.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    public boolean check_login(){
        String myData = "";
        try {
            FileInputStream fis = new FileInputStream(myInternalFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData += strLine;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, myData, Toast.LENGTH_SHORT).show();
        if (myData.equals("OKKKKK")) return true;
        return false;
    }

    public void setRunning(boolean isRun){
        this.isRunning = isRun;
    }

    public void logout(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir(filePath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, fileName);
        boolean deleted = myInternalFile.delete();
        if (deleted) Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
        setFragment(new Login());
    }
}
