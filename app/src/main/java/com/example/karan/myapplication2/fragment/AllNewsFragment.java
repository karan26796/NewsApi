package com.example.karan.myapplication2.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.adapter.NewsAdapter;
import com.example.karan.myapplication2.retrofit.news.general.News;
import com.example.karan.myapplication2.retrofit.news.general.NewsApiClient;
import com.example.karan.myapplication2.retrofit.news.general.NewsApiInterface;
import com.example.karan.myapplication2.retrofit.news.general.NewsResponse;
import com.example.karan.myapplication2.utils.Constants;
import com.example.karan.myapplication2.webview.CustomTabActivityHelper;
import com.example.karan.myapplication2.webview.WebViewFallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karan on 5/3/2018.
 */

@SuppressLint("ValidFragment")
public class AllNewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefresh;
    CustomTabActivityHelper mCustomTabActivityHelper;
    CustomTabActivityHelper.CustomTabConnectionCallback mConnectionCallback;
    String country, category;

    public AllNewsFragment() {
    }

    public AllNewsFragment(String country, String category) {
        this.country = country;
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment's layout
        View view = inflater.inflate(R.layout.fragment_all_news, container, false);
        setupCustomTabHelper();
        mSwipeRefresh = view.findViewById(R.id.allNewsRefresh);

        recyclerView = view.findViewById(R.id.recycler_news_all);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        getNews(country, category, recyclerView);
        mSwipeRefresh.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getNews(String country, String category, final RecyclerView recyclerView) {
        mSwipeRefresh.setRefreshing(true);
        NewsApiInterface apiInterface = NewsApiClient.getClient()
                .create(NewsApiInterface.class);
        Call<NewsResponse> responseCall = apiInterface.getTopHeadlines(country, category, Constants.NEWS_API_KEY);

        responseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                List<News> newsList = new ArrayList<>();
                try {
                    newsList = response.body().getArticles();
                } catch (NullPointerException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                recyclerView.setAdapter(new NewsAdapter(newsList, new NewsAdapter.onNewsClickListener() {
                    @Override
                    public void onNewsClicked(View view, int position, Bundle bundle) {
                        News news = bundle.getParcelable("news");
                        launchCustomTab(news.getUrl());
                    }

                    @Override
                    public void onBookmarkClicked(View view, int position, Bundle bundle) {
                        Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                    }
                }, newsList.size()));
                mSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    private void launchCustomTab(String url) {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setShowTitle(true);
        CustomTabActivityHelper.openCustomTab(getActivity(), intentBuilder.build(), Uri.parse(url), new WebViewFallback(), false);
    }

    private void setupCustomTabHelper() {
        this.mCustomTabActivityHelper = new CustomTabActivityHelper();
        this.mCustomTabActivityHelper.setConnectionCallback(this.mConnectionCallback);
    }

    @Override
    public void onRefresh() {
        getNews(country, category, recyclerView);
    }
}