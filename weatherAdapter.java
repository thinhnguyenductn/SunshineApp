package com.example.sunshineapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class weatherAdapter extends ArrayAdapter<dayData> {
    weatherAdapter(Context context, ArrayList<dayData> dayData){
        super(context,0,dayData);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        dayData mdayData = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_forecast,parent,false);
        }

        TextView weekDay = (TextView) convertView.findViewById(R.id.da_WeekDay);
        TextView max = (TextView) convertView.findViewById(R.id.tvHighestTemp);
        TextView min = (TextView) convertView.findViewById(R.id.tvLowestTemp);
        TextView description = (TextView) convertView.findViewById(R.id.tvWeatherDescription);


        weekDay.setText(mdayData.getWeekDay());
        max.setText(mdayData.getMaxTemp());
        min.setText(mdayData.getMinTemp());
        description.setText(mdayData.getwDescrition());

        return convertView;
    }
}
