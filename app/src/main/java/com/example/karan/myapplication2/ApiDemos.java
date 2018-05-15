package com.example.karan.myapplication2;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by karan on 5/12/2018.
 */

public class ApiDemos extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF",
                "fonts/core_sans_regular.otf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        FirebaseDatabase.getInstance()
                .setPersistenceEnabled(true);

        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("scores");
        scoresRef.keepSynced(true);
    }
}
