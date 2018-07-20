package com.example.karan.myapplication2.news.fragments.home;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.news.adapter.home.FirebaseAdapter;
import com.example.karan.myapplication2.retrofit.news.general.News;
import com.example.karan.myapplication2.webview.CustomTabActivityHelper;
import com.example.karan.myapplication2.webview.WebViewFallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karan on 5/16/2018.
 */

public class CommonFragment extends Fragment implements FirebaseAdapter.onNewsHistoryClickListener
        , SwipeRefreshLayout.OnRefreshListener {

    String databaseRef;
    DatabaseReference mDatabase, mDeleteReference;
    FirebaseAuth mAuth;
    FirebaseAdapter adapter;

    @BindView(R.id.favorites_recycler)
    RecyclerView mHistoryRecycler;

    @BindView(R.id.swipeRefreshFavorites)
    SwipeRefreshLayout mSwipeLayout;

    CustomTabActivityHelper mCustomTabActivityHelper;
    CustomTabActivityHelper.CustomTabConnectionCallback mConnectionCallback;

    Bundle bundle;
    ArrayList<News> mNewsList = new ArrayList<>();

    public static CommonFragment newInstance(String databaseRef) {

        Bundle args = new Bundle();
        args.putString("dbref", databaseRef);
        CommonFragment fragment = new CommonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CommonFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if (bundle != null && bundle.containsKey("dbref"))
            this.databaseRef = bundle.getString("dbref");
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
            mDatabase = FirebaseDatabase.getInstance()
                    .getReference()
                    .child(databaseRef)
                    .child(mAuth.getCurrentUser().getUid());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);
        setupCustomTabHelper();

        adapter = new FirebaseAdapter(News.class, R.layout.item_news_bookmark,
                FirebaseAdapter.ViewHolder.class, mDatabase, mNewsList, this);
        mHistoryRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));

        mSwipeLayout.setOnRefreshListener(this);
        readData(adapter);
        return view;
    }

    @Override
    public void onRefresh() {
        readData(adapter);
    }

    @Override
    public void onNewsClicked(FirebaseAdapter.ViewHolder viewHolder, int position, Bundle bundle) {
        try {
            News news = bundle.getParcelable("news");
            launchCustomTab(news.getUrl());
        } catch (NullPointerException e) {
            Log.e("History Activity", e.getMessage());
        }
    }

    public void readData(final FirebaseRecyclerAdapter adapter) {
        mSwipeLayout.setRefreshing(true);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //mNewsList.clear();
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    if (userDataSnapshot != null) {
                        mNewsList.add(userDataSnapshot.getValue(News.class));
                        adapter.notifyDataSetChanged();
                        mHistoryRecycler.setAdapter(adapter);
                        mSwipeLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                adapter.notifyDataSetChanged();
                mSwipeLayout.setRefreshing(false);
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
}
