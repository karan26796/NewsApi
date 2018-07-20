package com.example.karan.myapplication2.news.fragments.home;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.news.adapter.home.NewsAdapter;
import com.example.karan.myapplication2.retrofit.news.general.News;
import com.example.karan.myapplication2.retrofit.news.general.NewsApiClient;
import com.example.karan.myapplication2.retrofit.news.general.NewsApiInterface;
import com.example.karan.myapplication2.retrofit.news.general.NewsResponse;
import com.example.karan.myapplication2.utils.Constants;
import com.example.karan.myapplication2.utils.ItemDivider;
import com.example.karan.myapplication2.webview.CustomTabActivityHelper;
import com.example.karan.myapplication2.webview.WebViewFallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karan on 5/3/2018.
 */

public class AllNewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, BottomSheetDialog.OnOptionClicked {
    @BindView(R.id.recycler_news_all)
    RecyclerView recyclerView;
    @BindView(R.id.allNewsRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    BottomSheetDialog bottomSheetDialog;
    CustomTabActivityHelper mCustomTabActivityHelper;
    CustomTabActivityHelper.CustomTabConnectionCallback mConnectionCallback;
    Bundle bundle;
    String country, category;

    public static AllNewsFragment newInstance(String country, String category) {

        Bundle args = new Bundle();
        args.putString("country", country);
        args.putString("category", category);
        AllNewsFragment fragment = new AllNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bottomSheetDialog = new BottomSheetDialog();
        bottomSheetDialog.setmListener(this);
        bundle = getArguments();
        assert bundle != null;
        if (bundle.containsKey("category") && bundle.containsKey("country")) {
            this.category = bundle.getString("category");
            this.country = bundle.getString("country");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment's layout
        View view = inflater.inflate(R.layout.fragment_all_news, container, false);
        ButterKnife.bind(this, view);
        setupCustomTabHelper();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new ItemDivider(ContextCompat.getDrawable(Objects.requireNonNull(getContext()),
                R.drawable.divider), 0, 0));
        getNews(country, category, recyclerView);
        mSwipeRefresh.setOnRefreshListener(this);
        return view;
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
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                List<News> newsList = new ArrayList<>();
                try {
                    assert response.body() != null;
                    newsList = response.body().getArticles();
                } catch (NullPointerException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                recyclerView.setAdapter(new NewsAdapter(newsList, mListener, newsList.size()));
                mSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    private NewsAdapter.onNewsClickListener mListener = new NewsAdapter.onNewsClickListener() {
        @Override
        public void onNewsClicked(View view, int position, Bundle bundle) {
            News news = bundle.getParcelable("news");
            assert news != null;
            launchCustomTab(news.getUrl());
        }

        @Override
        public void onBookmarkClicked(View view, int position, Bundle bundle) {
            bottomSheetDialog.setArguments(bundle);
            bottomSheetDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "");
        }
    };


    private void launchCustomTab(String url) {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setShowTitle(true);
        CustomTabActivityHelper.openCustomTab(getActivity(), intentBuilder.build(), Uri.parse(url),
                new WebViewFallback(), false);
    }

    private void setupCustomTabHelper() {
        this.mCustomTabActivityHelper = new CustomTabActivityHelper();
        this.mCustomTabActivityHelper.setConnectionCallback(this.mConnectionCallback);
    }

    @Override
    public void onRefresh() {
        getNews(country, category, recyclerView);
    }

    @Override
    public void onOptionClicked() {
        Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
        if (bottomSheetDialog.isVisible()) {
            bottomSheetDialog.dismiss();
        }
    }
}