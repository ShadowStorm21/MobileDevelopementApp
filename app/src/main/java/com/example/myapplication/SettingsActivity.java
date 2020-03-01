package com.example.myapplication;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
        private static SwitchPreferenceCompat switchPreferenceCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            switchPreferenceCompat = findPreference("sync");
            Preference preference = findPreference("text");
            Date time = Calendar.getInstance().getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
            String mTime = simpleDateFormat.format(time);
            System.currentTimeMillis();
            preference.setTitle(mTime);

            if(!switchPreferenceCompat.isChecked())
            {
                initThemeListener();
            }
            else
            {
                initThemeListener1();
            }
        }
    }

    private static void initThemeListener()
    {
        switchPreferenceCompat.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                 if(preference.isSelectable())
                {
                    settheme(AppCompatDelegate.MODE_NIGHT_YES);
                    return true;

                }
              return false;

            }

        });
    }

    private static void initThemeListener1()
    {
        switchPreferenceCompat.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if(preference.isSelectable())
                {
                    settheme(AppCompatDelegate.MODE_NIGHT_NO);
                    return true;

                }
                return false;
            }

        });
    }

    private static void settheme(int themeMode) {
        AppCompatDelegate.setDefaultNightMode(themeMode);

    }
}