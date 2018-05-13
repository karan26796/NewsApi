package com.example.karan.myapplication2.retrofit.moviedb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by karan on 5/10/2018.
 */

public interface MovieDBInterface {
    @GET("movie/popular")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{category}")
    Call<MoviesResponse> getMovies(@Path("category") String category, @Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
