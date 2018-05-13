package com.example.karan.myapplication2.retrofit.github;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by karan on 5/5/2018.
 */

public interface GitHubClient {

    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String user);
    //user mentioned in {} is the same as user string
}
