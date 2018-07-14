package com.example.karan.myapplication2.news.fragments.home;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.news.adapter.home.HeadlineNewsAdapter;
import com.example.karan.myapplication2.news.adapter.home.TopSourcesAdapter;
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

public class TopNewsFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ShimmerRecyclerView topNewsRecycler, topSourceRecycler;
    CustomTabActivityHelper mCustomTabActivityHelper;
    CustomTabActivityHelper.CustomTabConnectionCallback mConnectionCallback;
    SwipeRefreshLayout mSwipeRefresh;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_top_news,
                container, false);
        setupCustomTabHelper();
        //NestedScrollView nestedScrollView = view.findViewById(R.id.nested);
        //nestedScrollView.setNestedScrollingEnabled(true);

        tabLayout = getActivity().findViewById(R.id.main_tab_layout);
        tabLayout.setVisibility(View.GONE);
        topSourceRecycler = view.findViewById(R.id.recycler_top_sources);
        topSourceRecycler.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false) {

            @Override

            public boolean canScrollVertically() {

                return false;

            }

        });
        topSourceRecycler.setAdapter(new TopSourcesAdapter());

        topNewsRecycler = view.findViewById(R.id.recycler_top_news);
        topNewsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        topNewsRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mSwipeRefresh = view.findViewById(R.id.swipeTopNews);
        mSwipeRefresh.setNestedScrollingEnabled(false);
        getNews(topNewsRecycler);
        mSwipeRefresh.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private void getNews(final ShimmerRecyclerView recyclerView) {
        mSwipeRefresh.setRefreshing(true);
        recyclerView.showShimmerAdapter();
        NewsApiInterface apiInterface = NewsApiClient.getClient()
                .create(NewsApiInterface.class);
        Call<NewsResponse> responseCall = apiInterface.getTopHeadlines("in", "general",
                Constants.NEWS_API_KEY);

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
                mSwipeRefresh.setRefreshing(false);
                recyclerView.hideShimmerAdapter();
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                mSwipeRefresh.setRefreshing(false);
                recyclerView.hideShimmerAdapter();
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
        getNews(topNewsRecycler);
    }

}
