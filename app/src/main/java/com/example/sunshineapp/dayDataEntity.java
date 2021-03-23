package com.example.sunshineapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.text.SimpleDateFormat;
@Entity(tableName = "dayData")
public class dayDataEntity {
    @NonNull @PrimaryKey
    private final Long date;
    @NonNull
    private final String maxTemp;
    @NonNull
    private final String minTemp;
    @NonNull
    private final String wDescrition;
    @NonNull
    private final String wHumidity;
    @NonNull
    private final String wPressure;
    @NonNull
    private final String wWind;

    public dayDataEntity (Long date, String maxTemp, String minTemp, String wDescrition, String wHumidity, String wPressure, String wWind){
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
