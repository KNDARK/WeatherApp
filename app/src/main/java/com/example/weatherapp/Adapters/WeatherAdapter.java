package com.example.weatherapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.Models.WeatherModel;
import com.example.weatherapp.R;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>{

    private Context mContext;
    private ArrayList<WeatherModel> listWeather;

    public WeatherAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<WeatherModel> list){
        this.listWeather = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_hour_main, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherModel weather = listWeather.get(position);
        if (weather == null) return;
        int temp = (int) (Double.parseDouble(String.valueOf(weather.main.temp)) - 273.15);
        String temperature = temp + "°C";
        holder.tvTemperature.setText(temperature);
        holder.tvHour.setText(weather.dt_txt.split(" ")[1]);
        Log.d("@@@@", "onBindViewHolder: "+ listWeather.get(position).main.temp);
    }

    @Override
    public int getItemCount() {
        if (listWeather != null) return listWeather.size();
        return 0;
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTemperature, tvHour;
        ImageView imvIconWeather;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTemperature = itemView.findViewById(R.id.tv_temperature);
            tvHour = itemView.findViewById(R.id.tv_hour);
            imvIconWeather = itemView.findViewById(R.id.imv_icon_weather);
        }
    }
}
