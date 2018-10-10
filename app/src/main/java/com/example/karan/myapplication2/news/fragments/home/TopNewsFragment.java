package com.example.karan.myapplication2.news.fragments.home;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.model.TopSources;
import com.example.karan.myapplication2.news.adapter.home.HeadlineNewsAdapter;
import com.example.karan.myapplication2.news.adapter.home.TopSourcesAdapter;
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

public class TopNewsFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener, TopSourcesAdapter.OnSourceClicked {

    @BindView(R.id.recycler_top_news)
    ShimmerRecyclerView topNewsRecycler;
    @BindView(R.id.recycler_top_sources)
    ShimmerRecyclerView topSourceRecycler;
    @BindView(R.id.swipeTopNews)
    SwipeRefreshLayout mSwipeRefresh;

    TopSourcesAdapter adapter;
    ArrayList<TopSources> mListSources = new ArrayList<>();

    CustomTabActivityHelper mCustomTabActivityHelper;
    CustomTabActivityHelper.CustomTabConnectionCallback mConnectionCallback;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_top_news,
                container, false);
        setupCustomTabHelper();
        ButterKnife.bind(this, view);
        tabLayout = getActivity().findViewById(R.id.main_tab_layout);
        tabLayout.setVisibility(View.GONE);
        topSourceRecycler.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);

        setmListSources(mListSources);
        adapter = new TopSourcesAdapter(this, mListSources);
        topSourceRecycler.setAdapter(adapter);

        topNewsRecycler.setLayoutAnimation(animation);
        topNewsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        topNewsRecycler.addItemDecoration(new ItemDivider(ContextCompat.getDrawable(Objects.requireNonNull(getContext()),
                R.drawable.divider), 0, 0));
        mSwipeRefresh.setOnRefreshListener(this);

        getNews(topNewsRecycler);
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
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                List<News> newsList = new ArrayList<>();
                try {
                    assert response.body() != null;
                    newsList = response.body().getArticles();
                } catch (NullPointerException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                recyclerView.setAdapter(new HeadlineNewsAdapter(newsList, mListener));
                mSwipeRefresh.setRefreshing(false);
                recyclerView.hideShimmerAdapter();
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, Throwable t) {
                mSwipeRefresh.setRefreshing(false);
                recyclerView.hideShimmerAdapter();
            }
        });
    }

    private HeadlineNewsAdapter.onNewsClickListener mListener = new HeadlineNewsAdapter.onNewsClickListener() {
        @Override
        public void onNewsClicked(View view, int position, Bundle bundle) {
            News news = bundle.getParcelable("news");
            assert news != null;
            launchCustomTab(news.getUrl());
        }
    };

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

    @Override
    public void onItemClicked(int position) {
        adapter.notifyDataSetChanged();
    }

    public void setmListSources(ArrayList<TopSources> mListSources) {
        this.mListSources = mListSources;
        for (int i = 0; i < 4; i++) {
            mListSources.add(new TopSources("News", R.id.action_account));
        }
    }
}
