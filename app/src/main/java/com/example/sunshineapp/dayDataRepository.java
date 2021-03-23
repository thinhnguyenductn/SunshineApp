package com.example.sunshineapp;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class dayDataRepository {
    private dayDataDao dayDataDao;
    private LiveData<List<dayDataEntity>> allDayData;
    private final ExecutorService service = Executors.newFixedThreadPool(2);

    public dayDataRepository(Application application) {
        dayDataDatabase dayDataDatabase = com.example.sunshineapp.dayDataDatabase.getInstance(application);
        dayDataDao dayDataDao = dayDataDatabase.dayDataDao();
        allDayData = dayDataDao.getAllDayDataEntity();
    }

    public void insert(dayDataEntity dayDataEntity) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                dayDataDao.insert(dayDataEntity);
            }
        });
    }

    public void update(dayDataEntity dayDataEntity) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                dayDataDao.update(dayDataEntity);
            }
        });
    }

    public void delete(dayDataEntity dayDataEntity) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                dayDataDao.delete(dayDataEntity);
            }
        });
    }

    public void deleteAll() {
        service.execute(new Runnable() {
            @Override
            public void run() {
                dayDataDao.deleteAllDayData();
            }
        });
    }

    public LiveData<List<dayDataEntity>> getAllDayDataEntity() {
        return allDayData;
    }
}
