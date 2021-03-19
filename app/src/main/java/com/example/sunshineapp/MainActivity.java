package com.example.sunshineapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<dayData> dayDataArrayList;
    public static final String DAY_DATA_BUNDLE = "dayDataBundle";
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG,"Activity Created");
        Toast.makeText(this, "Activity Created", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Log.v(LOG_TAG,"Setup Toolbar successful");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_setting){
            Intent intent = new Intent(MainActivity.this, mySettingsActivity.class);
            startActivity(intent);
            Log.v(LOG_TAG,"Setting activity started successfully");
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        weatherViewModel model = new ViewModelProvider(this).get(weatherViewModel.class);
        Log.v(LOG_TAG,"ViewModel Connected successful");

        URL = urlConstructor();
        model.getWeatherData(URL).observe(this,new Observer<ArrayList<dayData>>() {
            @Override
            public void onChanged(ArrayList<dayData> dayData) {
                dayDataArrayList = dayData;
                weatherAdapter wAdapdater = new weatherAdapter(MainActivity.this,dayDataArrayList);
                ListView mainListView = findViewById(R.id.mainListView);
                mainListView.setAdapter(wAdapdater);
                mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dayData itemDayData = wAdapdater.getItem(position);
                        Bundle dayDataBundle = new Bundle();
                        dayDataBundle.putString("weekDay",itemDayData.getWeekDay());
                        dayDataBundle.putString("date",itemDayData.getDate());
                        dayDataBundle.putString("max",itemDayData.getMaxTemp());
                        dayDataBundle.putString("min",itemDayData.getMinTemp());
                        dayDataBundle.putString("weatherDescription",itemDayData.getwDescrition());
                        dayDataBundle.putString("humidity",itemDayData.getwHumidity());
                        dayDataBundle.putString("pressure",itemDayData.getwPressure());
                        dayDataBundle.putString("wind",itemDayData.getwWind());
                        Intent intent = new Intent(MainActivity.this,DetailActivity.class)
                                .putExtra(DAY_DATA_BUNDLE,dayDataBundle);
                        startActivity(intent);
                    }
                });

            }
        });
        Toast.makeText(this, "Acitivity Resumed URL Reconstructed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(LOG_TAG,"Acitivity Paused");
        Toast.makeText(this, "Acitivity Paused", Toast.LENGTH_SHORT).show();
    }

    public String urlConstructor(){

        String baseURL1 = "https://api.openweathermap.org/data/2.5/onecall?";
        String baseURL2 = "&exclude=minutely,hourly&cnt=7&appid=08a57928710a8283d1f893209cbac19c";
        String locationInfo = "&lat=21.0159052&lon=105.7900819";
        String measurementUnitInfo = "&units=metric";
        String URL;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);


        String locationPreference = sharedPreferences.getString(mySettingsActivity.PREF_FAVORITE_LOCATION,"Ha Noi");
        String measurementUnitPreference = sharedPreferences.getString(mySettingsActivity.PREF_FAVORITE_MEASUREMENT,"Metric Celcius Unit");

        if (locationPreference.equals("Da Nang"))
        {
            locationInfo = "&lat=16.0544&lon=108.2022";
        }

        else if (locationPreference.equals("Ho Chi Minh city"))
        {
            locationInfo = "&lat=10.8231&lon=106.6297";
        }


        if (measurementUnitPreference.equals("Imperial Fahrenheit Unit"))
        {
            measurementUnitInfo = "&units=imperial";
        }

        URL = baseURL1+locationInfo+measurementUnitInfo+baseURL2;
        return URL;
}
    /*public static class PlaceHolderFragement extends Fragment{
        public PlaceHolderFragement(){
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main,container,false);
            return rootView;
        }
    } */
}