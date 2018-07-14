package com.example.karan.myapplication2.news.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by karan on 4/8/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Toolbar mToolbar;

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
