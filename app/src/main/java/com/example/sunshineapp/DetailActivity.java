package com.example.sunshineapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView weekDay, date, max, min, weatherDescription, humidity, pressure, wind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        weekDay = (TextView) findViewById(R.id.da_WeekDay);
        date = (TextView) findViewById(R.id.da_Date);
        max = (TextView) findViewById(R.id.da_Max);
        min = (TextView) findViewById(R.id.da_Min);
        weatherDescription = (TextView) findViewById(R.id.da_WeatherDescription);
        humidity = (TextView) findViewById(R.id.da_Humidity);
        pressure = (TextView) findViewById(R.id.da_Pressure);
        wind = (TextView) findViewById(R.id.da_Wind);

        Bundle dayData = getIntent().getBundleExtra(MainActivity.DAY_DATA_BUNDLE);
        weekDay.setText(dayData.getString("weekDay"));
        date.setText(dayData.getString("date"));
        max.setText(dayData.getString("max"));
        min.setText(dayData.getString("min"));
        weatherDescription.setText(dayData.getString("weatherDescription"));
        humidity.setText(getString(R.string.daHumidity) + dayData.getString("humidity"));
        pressure.setText(getString(R.string.daPressure) +dayData.getString("pressure"));
        wind.setText(getString(R.string.daWind) + dayData.getString("wind"));

    }
}