package com.example.karan.myapplication2.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by karan on 4/8/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

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

    protected abstract int getToolbarID();
}
