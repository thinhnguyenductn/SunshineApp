package com.example.sunshineapp;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class weatherRepository {
    public static final String LOG_TAG = weatherRepository.class.getSimpleName();
    private final ExecutorService service = Executors.newFixedThreadPool(2);
    public MutableLiveData<ArrayList<dayData>> mutableLiveData = new MutableLiveData<>();

    /*public  MutableLiveData<ArrayList<dayData>> getLiveWeatherData(){
        Log.v("Repository","Passing data to mutablevariable result: "+mutableLiveData.toString());
        return mutableLiveData;
    }*/

    public LiveData<ArrayList<dayData>> getWeatherData(String URL) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                String rawJSONString = makeInfoRequest(URL);
                ArrayList<dayData> result = extractDayDataFromJson(rawJSONString);
                Log.v("Repository","Parsing result: "+result.toString());

                mutableLiveData.postValue(result);
            }
        });
        if (mutableLiveData==null) {
            Log.v("Repository","Null result");
        } else{
            Log.v("Repository","Mutable data result: "+ mutableLiveData.toString());}
        return mutableLiveData;
    }

    public String makeInfoRequest(String URL) {
        String rawJsonString ="";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(URL);
            //Create request and open connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            Log.v("Repository","Connected successful");

            //Read the input stream into a String
            if (urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                rawJsonString = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: "+urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //If still have connection then disconnect it
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            //If still have inputStream then close it
            if (inputStream !=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  rawJsonString;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        Log.v("Repository","Reading successful");
        return output.toString();
    }

    private static ArrayList<dayData> extractDayDataFromJson(String rawJsonString){
        ArrayList<dayData> dayDataArrayList = new ArrayList<dayData>();
        if (TextUtils.isEmpty(rawJsonString)){
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(rawJsonString);
            JSONArray dailyArray = jsonObject.getJSONArray("daily");
            if (dailyArray.length()>0){
                for(int i = 0; i<dailyArray.length(); i++){
                    JSONObject dayData = dailyArray.getJSONObject(i);
                    Long date = Long.parseLong(dayData.getString("dt"));
                    String pressure = dayData.getString("pressure");
                    String humidity = dayData.getString("humidity");
                    String wind = dayData.getString("wind_speed");
                    JSONObject temperature = dayData.getJSONObject("temp");
                    String min = temperature.getString("min");
                    String max = temperature.getString("max");
                    JSONObject weather = dayData.getJSONArray("weather").getJSONObject(0);
                    String description = weather.getString("description");
                    dayDataArrayList.add(new dayData(date,max,min,description,humidity,pressure,wind));
                }
                Log.v("Repository","Parsing Json Data successful");
                return  dayDataArrayList;
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG,"Problem parsing raw JSON String",e);
        }
    return null;
    }
}
