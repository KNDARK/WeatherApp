package com.example.weatherapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.Models.results;
import com.example.weatherapp.R;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {
    private Context mContext;
    private ArrayList<results> listCity;

    public CityAdapter(Context mcontext){
        this.mContext = mcontext;
    }

    public void setData(ArrayList<results> list){
        this.listCity = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, @SuppressLint("RecyclerView") int position) {
        results city = listCity.get(position);
        if (city == null) return;
        holder.tvCity.setText(listCity.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x = listCity.get(position).name;
                ((MainActivity)mContext).set_view_location(x);
                ((MainActivity)mContext).refresh_toolbar();
                ((MainActivity)mContext).bottomNav.setSelectedItemId(0);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listCity != null) return listCity.size();
        return 0;
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCity;
        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tv_item_city);
        }
    }
}
