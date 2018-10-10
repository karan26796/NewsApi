package com.example.karan.myapplication2.model;

public class TopSources {
    private String name;
    private int drawable;
    public long id;

    public TopSources(String name, int drawable) {
        this.name = name;
        this.drawable = drawable;
    }

    public TopSources() {

    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
