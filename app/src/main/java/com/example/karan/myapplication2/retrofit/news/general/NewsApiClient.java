package com.example.karan.myapplication2.retrofit.news.general;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.karan.myapplication2.utils.Constants.NEWS_BASE_URL;

/**
 * Created by karan on 5/11/2018.
 */

public class NewsApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(NEWS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
