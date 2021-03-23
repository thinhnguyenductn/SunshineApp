package com.example.sunshineapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    TextView weekDay, date, max, min, weatherDescription, humidity, pressure, wind = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_setting:
                Intent intent = new Intent(DetailActivity.this, mySettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_viewLocation:
                openPreferredLocationInMap();
                return true;
            case R.id.action_shareWeatherInfo:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, weekDay.getText()+" "+date.getText()+" "+max.getText()+" "+min.getText());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void openPreferredLocationInMap(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DetailActivity.this);
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
}