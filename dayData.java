package com.example.sunshineapp;

import android.util.Log;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class dayData {
    public final Long date;
    public final String maxTemp;
    public final String minTemp;
    public final String wDescrition;
    public final String wHumidity;
    public final String wPressure;
    public final String wWind;

    public dayData(Long date, String maxTemp, String minTemp, String wDescrition, String wHumidity, String wPressure, String wWind){
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.wDescrition = wDescrition;
        this.wHumidity = wHumidity;
        this.wPressure = wPressure;
        this.wWind = wWind;
    }

    public String getWeekDay() {
        Date date = new Date(this.date*1000L);
        Log.d("dayData", date.toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        return simpleDateFormat.format(date);
    }

    public String getDate() {
        Date date = new Date(this.date*1000L);
        Log.d("dayData", date.toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        return simpleDateFormat.format(date);
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getwDescrition() {
        return wDescrition;
    }

    public String getwHumidity() {
        return wHumidity;
    }

    public String getwPressure() {
        return wPressure;
    }

    public String getwWind() {
        return wWind;
    }
}
