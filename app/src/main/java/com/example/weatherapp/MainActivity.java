package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.Api.ApiUserService;
import com.example.weatherapp.Models.UserModel;
import com.example.weatherapp.Models.WeatherModel;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public NowFragment fgNow = new NowFragment();
    public HourFragment fgHour = new HourFragment();
    public WeatherModel dataSend;
    TextView tvName;
    public UserModel user = new UserModel();
    final int LOADING_TIME = 1000;


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
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.toString()){
                    case "location":
                        setAnimation();
                        show_item_detail(new LocationFragment());
                        break;
                    case "About":
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100007498090194"));
                        startActivity(browserIntent);
                        break;
                }
                return false;
            }
        });
        menuLeft = layoutMain.findViewById(R.id.menu_left);
        menuLeft.setVisibility(View.INVISIBLE);
        menuLeft.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                menuLeft.setCheckedItem(item);
                hide_menu_left();
                switch (item.toString()){
                    case "Đăng xuất":
                        logout();
                        break;
                    case "About":
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100007498090194"));
                        startActivity(browserIntent);
                        break;
                    case "Thông tin user":
                        String text = "Email: " + user.getEmail()+ "\nTên: "+ user.getName()+"\nThành phố: "+user.getLocation();
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                        break;
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
                        setFragment(fgNow);
                        break;
                    case "Hour":
                        setAnimation();
                        setFragment(fgHour);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {

    }


    public void set_info(){
        tvName = findViewById(R.id.tv_name);
        tvName.setText(user.getName());
    }

    public void set_view_location(String city){
        setFragment(fgNow);
        fgNow.setLocation(city);
        fgNow.getWeather();
        fgNow.get_weather_hour();
        Log.d("@@@@", "set_view_location: "+city);
        fgHour.setLocation(city);
    }

    public void call_back(Fragment fragment){
        setAnimation();
        removeFragment(fragment);
    }

    public void refresh_toolbar(){
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.menu_icon));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_menu_left();
            }
        });
    }
    public void show_item_detail(Fragment fragment) {
        setAnimation();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    public void setAnimation(){
        Transition explore = new Explode();
        explore.setDuration(300);
        explore.setStartDelay(100);
        TransitionManager.beginDelayedTransition(layoutMain, explore);
    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
    public void removeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }


    public void show_menu_left() {
        Transition slide = new Slide();
        slide.setDuration(300);
        slide.setStartDelay(100);
        TransitionManager.beginDelayedTransition(layoutMain, slide);
        menuLeft.setVisibility(View.VISIBLE);
    }

    public void hide_menu_left() {
        Transition slide = new Slide();
        slide.setDuration(300);
        slide.setStartDelay(100);
        TransitionManager.beginDelayedTransition(layoutMain, slide);
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
