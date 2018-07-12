package com.example.karan.myapplication2.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.utils.Constants;

/**
 * Created by karan on 4/8/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Toolbar mToolbar;
    String theme;
    SharedPreferences sharedPreferences;
    SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setUpTheme();
        super.onCreate(savedInstanceState);
        sharedPreferencesListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // register shared preferences listener
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(prefListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // unregister shared preferences listener
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(prefListener);
    }

    protected void setUpToolbar(int i) {

        mToolbar = findViewById(getToolbarID());
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            if (i == 0)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (i == 1)
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setUpTheme() {

        // fetches shared preferences for user selected app theme
        loadPreferences();

        // set app theme
        if (theme.equals(getString(R.string.theme_light))) {
            setTheme(R.style.AppTheme);
        } else if (theme.equals(getString(R.string.theme_dark))) {
            setTheme(R.style.AppDarkTheme);
        }
    }

    protected void loadPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getString(Constants.KEY_APP_THEME, getString(R.string.theme_light));

    }

    protected abstract int getToolbarID();

    protected void sharedPreferencesListener() {

        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                /*The activity_settings activity too is given the app theme, therefore it is recreated
                in case of change in app theme*/
                /*In case of other two customisations, they are to be applied in the details page only.*/
                if (key.equals(Constants.KEY_APP_THEME)) {
                    recreate();
                }
            }
        };
    }
}
