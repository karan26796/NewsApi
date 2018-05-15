package com.example.karan.myapplication2.model;

/**
 * Created by karan on 5/15/2018.
 */

public class Options {
    String title;
    int drawable;

    public Options(String title, int drawable) {
        this.title = title;
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
