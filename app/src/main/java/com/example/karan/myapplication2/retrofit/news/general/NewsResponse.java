package com.example.karan.myapplication2.retrofit.news.general;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 5/11/2018.
 */

public class NewsResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("articles")
    private List<News> articles;
    @SerializedName("totalResults")
    private int totalResults;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<News> getArticles() {
        return articles;
    }

    public void setArticles(List<News> articles) {
        this.articles = articles;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
