package com.example.karan.myapplication2.retrofit;

import com.example.karan.myapplication2.model.Hero;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by karan on 5/3/2018.
 */

public interface Api {
    String BASE_URL = "https://simplifiedcoding.net/demos/";

    @GET("marvel")
    Call<List<Hero>> getHeroes();
}
