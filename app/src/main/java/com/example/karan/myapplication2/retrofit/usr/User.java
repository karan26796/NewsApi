package com.example.karan.myapplication2.retrofit.usr;

/**
 * Created by karan on 5/5/2018.
 */

public class User {

    private Integer id;
    private String name, email;
    private String[] topics;
    private int age;

    public User(String name, String email, String[] topics, int age) {
        this.name = name;
        this.email = email;
        this.topics = topics;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }
}
