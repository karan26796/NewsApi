package com.example.karan.myapplication2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.retrofit.github.GitHubClient;
import com.example.karan.myapplication2.retrofit.github.GitHubRepo;
import com.example.karan.myapplication2.retrofit.github.GitHubRepoAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by karan on 5/5/2018.
 */

public class ThirdFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_third, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);

        listView = view.findViewById(R.id.github_list);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setRefreshing(true);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        final GitHubClient gitHubClient = retrofit.create(GitHubClient.class);
        Call<List<GitHubRepo>> listCall = gitHubClient.reposForUser("karan26796");

        listCall.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                List<GitHubRepo> body = response.body();
                listView.setAdapter(new GitHubRepoAdapter(getContext(), body));
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Done, not", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
