package com.example.sunshineapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
    SharedPreferences mPreferences;
    public final String SHARED_PREFS_FILE =
            "com.example.sunshineapp.sharedprefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mPreferences = getSharedPreferences(SHARED_PREFS_FILE, MODE_PRIVATE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_setting:
                Intent intent = new Intent(MainActivity.this, mySettingsActivity.class);
                startActivity(intent);
                Log.v(LOG_TAG,"Setting activity started successfully");
                return true;
            case R.id.action_viewLocation:
                openPreferredLocationInMap();
                return true;
            case R.id.action_shareWeatherInfo:
                shareWeatherInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openPreferredLocationInMap(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String locationPreference = sharedPreferences.getString(mySettingsActivity.PREF_FAVORITE_LOCATION,"Ha Noi");
        Uri geoLocation = Uri.parse("geo:0.0?q="+locationPreference);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        //intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        } else {
            Toast.makeText(this, "Cannot open map!", Toast.LENGTH_SHORT).show();
        }

    }
    public void shareWeatherInfo(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onPause() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String locationPreference = sharedPreferences.getString(mySettingsActivity.PREF_FAVORITE_LOCATION,"Ha Noi");
        String measurementUnitPreference = sharedPreferences.getString(mySettingsActivity.PREF_FAVORITE_MEASUREMENT,"Metric Celcius Unit");
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(mySettingsActivity.PREF_FAVORITE_LOCATION,locationPreference);
        preferencesEditor.putString(mySettingsActivity.PREF_FAVORITE_MEASUREMENT,measurementUnitPreference);
        preferencesEditor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateInfo();
        Toast.makeText(this, "Acitivity Resumed URL Reconstructed", Toast.LENGTH_SHORT).show();
    }

    public void updateInfo(){
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
    }

    public String urlConstructor(){

        String baseURL1 = "https://api.openweathermap.org/data/2.5/onecall?";
        String baseURL2 = "&exclude=minutely,hourly&cnt=7&appid=08a57928710a8283d1f893209cbac19c";
        String locationInfo = "&lat=21.0159052&lon=105.7900819";
        String measurementUnitInfo = "&units=metric";
        String URL;

        String locationPreference = mPreferences.getString(mySettingsActivity.PREF_FAVORITE_LOCATION,"Ha Noi");
        String measurementUnitPreference = mPreferences.getString(mySettingsActivity.PREF_FAVORITE_MEASUREMENT,"Metric Celcius Unit");

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

}
