package com.example.karan.myapplication2;

import android.app.Application;

/**
 * Created by karan on 5/12/2018.
 */

public class ApiDemos extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF",
                "font/core_sans_medium.otf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }
}
