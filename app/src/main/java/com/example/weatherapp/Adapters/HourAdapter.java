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

import com.bumptech.glide.Glide;
import com.example.weatherapp.Models.WeatherModel;
import com.example.weatherapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.WeatherViewHolder>{

    private Context mContext;
    private ArrayList<WeatherModel> listWeather;

    public HourAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<WeatherModel> list){
        this.listWeather = list;
        Log.d("@@@@", "SetData: "+ list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_item, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherModel weather = listWeather.get(position);
        if (weather == null) return;
        int temp = (int) (Double.parseDouble(String.valueOf(weather.main.temp)) - 273.15);
        String temperature = temp + "°C";
        Log.d("@@@@", "onBindViewHolder: "+ temperature);
        String pic = "https://openweathermap.org/img/wn/" + weather.weather.get(0).icon +"@2x.png";
        Glide.with(mContext)
                .load(pic)
                .fitCenter()
                .error(R.drawable.cloud_default)
                .into(holder.imvIconWeather);
        Locale locale = new Locale("vi", "VN");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        String dateStr = weather.dt_txt.split(" ")[0];
        Date getDate = parseDate(dateStr);
        String date = dateFormat.format(getDate);
        String[] arr_hour = weather.dt_txt.split(" ")[1].split(":");
        String hour = arr_hour[0] + ":" + arr_hour[1];
        holder.tvHour.setText(hour);
        holder.tvDate.setText(date);
        holder.tvDescription.setText(weather.weather.get(0).description);
    }

    @Override
    public int getItemCount() {
        if (listWeather != null) return listWeather.size();
        return 0;
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate, tvHour, tvDescription;
        ImageView imvIconWeather;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvHour = itemView.findViewById(R.id.tv_hour);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imvIconWeather = itemView.findViewById(R.id.imv_icon_weather);
        }
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
