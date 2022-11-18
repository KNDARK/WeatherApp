package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public MaterialToolbar toolbar;
    public ConstraintLayout layoutMain;
    private final String fileName = "login.txt";
    private final String filePath = "data";
    public boolean isRunning = true;
    public BottomNavigationView bottomNav;
    public FrameLayout layout;
    public NavigationView menuLeft;
    File myInternalFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_nav);
        layout = findViewById(R.id.fragment);
        layoutMain = findViewById(R.id.layout_main);
        toolbar = findViewById(R.id.tool_bar);
        setFragment(new Login());
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir(filePath, Context.MODE_PRIVATE);
        myInternalFile = new File(directory, fileName);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_menu_left();
            }
        });
        menuLeft = layoutMain.findViewById(R.id.menu_left);
        menuLeft.setVisibility(View.INVISIBLE);
        menuLeft.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                menuLeft.setCheckedItem(item);
                Log.d("@@@@@", "choose: "+ item);
                hide_menu_left();
                if (item.toString().equals("Đăng xuất")) {
                    Log.d("@@@@@", "logout");
                    logout();
                }
                return true;
            }
        });

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("@@@@@", "id: "+item);
                String action = item.toString();
                switch (action) {
                    case "Home":
                        setAnimation();
                        setFragment(new NowFragment());
                        break;
                    case "Hour":
                        setAnimation();
                        setFragment(new HourFragment());
                        break;
                    case "Date":
                        setAnimation();
                        setFragment(new DateFragment());
                        break;
                }
                return true;
            }
        });
    }

    public void setAnimation(){
        Transition explore = new Explode();
        explore.setDuration(300);
        explore.setStartDelay(100);
        TransitionManager.beginDelayedTransition(layoutMain, explore);
    }

    protected void setFragment(Fragment fragment) {
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

    public void show_menu_left() {
        setAnimation();
        menuLeft.setVisibility(View.VISIBLE);
    }

    public void hide_menu_left() {
        setAnimation();
        menuLeft.setVisibility(View.GONE);
    }

    public void show_auth() {
        toolbar.setVisibility(View.VISIBLE);
        bottomNav.setVisibility(View.VISIBLE);
    }
    public void hidden_auth() {
        toolbar.setVisibility(View.GONE);
        bottomNav.setVisibility(View.GONE);
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
