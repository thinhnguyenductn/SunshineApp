package com.example.sunshineapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class dayDataRepository {
    private dayDataDao dayDataDao;
    private LiveData<List<dayDataEntity>> allDayData;
    public dayDataRepository(Application application){
        dayDataDatabase dayDataDatabase = com.example.sunshineapp.dayDataDatabase.getInstance(application);
        dayDataDao dayDataDao = dayDataDatabase.dayDataDao();
        allDayData = dayDataDao.getAllDayData();
    }

    
}
