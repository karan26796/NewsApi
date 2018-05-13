package com.example.karan.myapplication2.retrofit.github;

import com.example.karan.myapplication2.model.News;
import com.example.karan.myapplication2.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by karan on 5/5/2018.
 */

public interface NewsAPIClient {

    @GET("/top-headlines?country=us/category=business&apiKey=" + Constants.NEWS_API_KEY + "/articles/")
    Call<List<News>> newsList();
    //user mentioned in {} is the same as user string
}
