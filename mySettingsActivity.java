package com.example.sunshineapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class mySettingsActivity extends AppCompatActivity {
    public static final String PREF_FAVORITE_LOCATION = "favorite_location";
    public static final String PREF_FAVORITE_MEASUREMENT = "favorite_measurement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);

            onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    if (key.equals(PREF_FAVORITE_LOCATION)){
                        Preference favoriteLocation = findPreference(key);
                        favoriteLocation.setSummary(sharedPreferences.getString(key,"Select your preferred location"));
                    }

                    if (key.equals(PREF_FAVORITE_MEASUREMENT)){
                        Preference favoriteMeasurement = findPreference(key);
                        favoriteMeasurement.setSummary(sharedPreferences.getString(key,"Select your preferred measurement unit"));
                    }
                }
            };
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);

            Preference favoriteLocation = findPreference(PREF_FAVORITE_LOCATION);
            favoriteLocation.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_FAVORITE_LOCATION,"Select your preferred location"));

            Preference favoriteMeasurement = findPreference(PREF_FAVORITE_MEASUREMENT);
            favoriteMeasurement.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_FAVORITE_MEASUREMENT,"Select your preferred measurement unit"));
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        }
    }
}