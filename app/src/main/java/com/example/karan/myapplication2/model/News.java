package com.example.karan.myapplication2.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karan on 5/8/2018.
 */

public class News {
    @SerializedName("author")
    private String author;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("urlToImage")
    private String img;
    @SerializedName("publishedAt")
    private String date;

    public News(String author, String title, String description, String img, String date) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.img = img;
        this.date = date;
    }

    public News() {

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
