package com.example.sunshineapp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class weatherViewModel extends ViewModel {
    private final dayDataFetching mWeatherRepository = new dayDataFetching();
    public LiveData<ArrayList<dayData>> mutableLiveData;


    /*public MutableLiveData<ArrayList<dayData>> getLiveWeatherData(){
        mutableLiveData = mWeatherRepository.getLiveWeatherData();

        if (mutableLiveData==null) {
            Log.v("ViewModel","Null result");
        }

        Log.v("ViewModel","Saved mutable data successful: ");
        return mutableLiveData;
    }*/

    public LiveData<ArrayList<dayData>> getWeatherData(String URL){
        mutableLiveData = mWeatherRepository.getWeatherData(URL);
        Log.v("ViewModel","Fetching data successful");
        if (mutableLiveData==null) {
            Log.v("ViewModel","Null result");
        }
        return mutableLiveData;
    }

}
