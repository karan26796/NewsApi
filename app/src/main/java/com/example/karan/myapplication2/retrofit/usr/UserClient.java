package com.example.karan.myapplication2.retrofit.usr;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by karan on 5/5/2018.
 */

public interface UserClient {

    @POST("user")
    Call<User> createAccount(@Body User user);

}
