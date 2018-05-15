package com.example.karan.myapplication2.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.activities.AllNewsActivity;
import com.example.karan.myapplication2.adapter.HeadlineNewsAdapter;
import com.example.karan.myapplication2.adapter.NewsAdapter;
import com.example.karan.myapplication2.retrofit.news.general.News;
import com.example.karan.myapplication2.retrofit.news.general.NewsApiClient;
import com.example.karan.myapplication2.retrofit.news.general.NewsApiInterface;
import com.example.karan.myapplication2.retrofit.news.general.NewsResponse;
import com.example.karan.myapplication2.utils.Constants;
import com.example.karan.myapplication2.utils.StartSnapHelper;
import com.example.karan.myapplication2.webview.CustomTabActivityHelper;
import com.example.karan.myapplication2.webview.WebViewFallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karan on 5/10/2018.
 */

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
        , View.OnClickListener {
    ShimmerRecyclerView mTopNewsRecycler;
    RecyclerView mNewsBusiness, mNewsHealth, mNewsSports, mNewsTechnology;
    SwipeRefreshLayout newsRefresh;
    StartSnapHelper snapHelper;
    TextView mBusiness, mSports, mTopNews, mHealth, mTechnology;
    View view;

    CustomTabActivityHelper mCustomTabActivityHelper;
    CustomTabActivityHelper.CustomTabConnectionCallback mConnectionCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_news, container, false);
        setupCustomTabHelper();


        newsRefresh = view.findViewById(R.id.newsRefresh);
        mNewsBusiness = view.findViewById(R.id.recycler_news_business);
        mNewsHealth = view.findViewById(R.id.recycler_news_health);
        mNewsSports = view.findViewById(R.id.recycler_news_sports);
        mNewsTechnology = view.findViewById(R.id.recycler_news_technology);

        mTopNewsRecycler = view.findViewById(R.id.recycler_news_headlines);

        mNewsBusiness.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mNewsBusiness.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        mNewsSports.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mNewsSports.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        mNewsHealth.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mNewsHealth.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        mNewsTechnology.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mNewsTechnology.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        mTopNewsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        snapHelper = new StartSnapHelper();
        snapHelper.attachToRecyclerView(mTopNewsRecycler);

        newsRefresh.setOnRefreshListener(this);


        newsRefresh.setRefreshing(true);
        if (Constants.NEWS_API_KEY.isEmpty()) {
            Toast.makeText(getContext(), "Please obtain your API KEY from newsapi.org first!", Toast.LENGTH_LONG).show();
        }
        getTopNews("in", "general", mTopNewsRecycler);
        getNews("in", "business", mNewsBusiness);
        getNews("in", "health", mNewsHealth);
        getNews("in", "sports", mNewsSports);
        getNews("in", "technology", mNewsTechnology);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //setupCustomTabHelper();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHealth = view.findViewById(R.id.text_health_news);
        mBusiness = view.findViewById(R.id.text_business_news);
        mSports = view.findViewById(R.id.text_sports_news);
        mTechnology = view.findViewById(R.id.text_technology_news);
        mTopNews = view.findViewById(R.id.text_top_news);

        mSports.setOnClickListener(this);
        mBusiness.setOnClickListener(this);
        mTopNews.setOnClickListener(this);
        mHealth.setOnClickListener(this);
        mTechnology.setOnClickListener(this);
    }

    @Override
    public void onRefresh() {
        newsRefresh.setRefreshing(false);
    }

    private void getTopNews(String country, String category, final ShimmerRecyclerView recyclerView) {
        NewsApiInterface apiInterface = NewsApiClient.getClient()
                .create(NewsApiInterface.class);
        Call<NewsResponse> responseCall = apiInterface.getTopHeadlines(country, category, Constants.NEWS_API_KEY);
        recyclerView.showShimmerAdapter();
        responseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                List<News> newsList = new ArrayList<>();
                try {
                    newsList = response.body().getArticles();
                } catch (NullPointerException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                recyclerView.setAdapter(new HeadlineNewsAdapter(newsList, new HeadlineNewsAdapter.onNewsClickListener() {
                    @Override
                    public void onNewsClicked(View view, int position, Bundle bundle) {
                        News news = bundle.getParcelable("news");
                        launchCustomTab(news.getUrl());
                    }
                }));
                newsRefresh.setRefreshing(false);
                recyclerView.hideShimmerAdapter();
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getNews(String country, String category, final RecyclerView recyclerView) {
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
                }, 3));
                newsRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void onClick(View v) {
        String category = null;
        switch (v.getId()) {
            case R.id.text_health_news:
                category = Categories.HEALTH.toString();
                break;
            case R.id.text_top_news:
                category = Categories.TOP_NEWS.toString();
                break;
            case R.id.text_sports_news:
                category = Categories.SPORTS.toString();
                break;
            case R.id.text_business_news:
                category = Categories.BUSINESS.toString();
                break;
            case R.id.text_technology_news:
                category = Categories.TECHNOLOGY.toString();
                break;
        }
        startActivity(new Intent(getContext(), AllNewsActivity.class)
                .putExtra("category", category));
    }

    public enum Categories {
        SPORTS, TOP_NEWS, BUSINESS, HEALTH, TECHNOLOGY
    }
}
