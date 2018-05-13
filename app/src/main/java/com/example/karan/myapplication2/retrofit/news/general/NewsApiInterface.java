package com.example.karan.myapplication2.retrofit.news.general;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by karan on 5/11/2018.
 */

public interface NewsApiInterface {

    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("country") String country,
                                       @Query("category") String category,
                                       @Query("apiKey") String apiKey);

}
