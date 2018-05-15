package com.example.karan.myapplication2.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.adapter.FirebaseAdapter;
import com.example.karan.myapplication2.adapter.FirebaseSearchAdapter;
import com.example.karan.myapplication2.retrofit.news.general.News;
import com.example.karan.myapplication2.webview.CustomTabActivityHelper;
import com.example.karan.myapplication2.webview.WebViewFallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookmarkActivity extends BaseActivity implements FirebaseAdapter.onNewsHistoryClickListener
        , SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener, TextWatcher
        , FirebaseSearchAdapter.onSearchClickListener {
    RecyclerView mBookmarkRecycler;
    DatabaseReference mDatabase;
    Query mSearchQuery;
    FirebaseAuth mAuth;
    Menu menu;
    FirebaseAdapter adapter;
    FirebaseSearchAdapter searchAdapter;
    SwipeRefreshLayout mSwipeLayout;
    CustomTabActivityHelper mCustomTabActivityHelper;
    CustomTabActivityHelper.CustomTabConnectionCallback mConnectionCallback;
    SearchView searchView;
    EditText inputSearchText;
    ArrayList<News> mNewsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        setUpToolbar(0);
        setupCustomTabHelper();
        mToolbar.inflateMenu(R.menu.activity_bookmark);
        menu = mToolbar.getMenu();
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.isSubmitButtonEnabled();
        //searchView.setSuggestionsAdapter();
        searchView.animate();

        inputSearchText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        inputSearchText.setHint("Search...");

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mDatabase = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Bookmarks")
                    .child(mAuth.getCurrentUser().getUid());

            mSearchQuery = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Bookmarks")
                    .child(mAuth.getCurrentUser().getUid());
        }

        adapter = new FirebaseAdapter(News.class, R.layout.item_news,
                FirebaseAdapter.ViewHolder.class, mDatabase, mNewsList, this);

        searchAdapter = new FirebaseSearchAdapter(News.class, R.layout.item_news,
                FirebaseSearchAdapter.NewsViewHolder.class, mSearchQuery, mNewsList, this);

        mSwipeLayout = findViewById(R.id.swipeRefreshBookmark);
        mBookmarkRecycler = findViewById(R.id.bookmark_recycler);
        mBookmarkRecycler.setLayoutManager(new LinearLayoutManager(this));
        mBookmarkRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //searchView.setOnQueryTextListener(this);
        //inputSearchText.addTextChangedListener(this);

        readData(adapter);
        mSwipeLayout.setOnRefreshListener(this);

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search),
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {

                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {

                        mNewsList.clear();
                        readData(adapter);
                        return true;
                    }
                });
    }

    @Override
    protected int getToolbarID() {
        return R.id.activity_bookmarks_toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_bookmark, menu);
        ImageView closeButton = searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.ic_close);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                //mNewsList.clear();
                //mBookmarkRecycler.setAdapter(searchAdapter);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onNewsClicked(View view, int position, Bundle bundle) {
        try {
            News news = bundle.getParcelable("news");
            launchCustomTab(news.getUrl());
        } catch (NullPointerException e) {
            Log.e("History Activity", e.getMessage());
        }
    }

    @Override
    public void onRefresh() {
        readData(adapter);
    }

    private void launchCustomTab(String url) {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setShowTitle(true);
        CustomTabActivityHelper.openCustomTab(this, intentBuilder.build(), Uri.parse(url), new WebViewFallback(), false);
    }

    private void setupCustomTabHelper() {
        this.mCustomTabActivityHelper = new CustomTabActivityHelper();
        this.mCustomTabActivityHelper.setConnectionCallback(this.mConnectionCallback);
    }

    public void readData(final FirebaseRecyclerAdapter adapter) {
        mSwipeLayout.setRefreshing(true);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mNewsList.clear();
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    if (userDataSnapshot != null) {
                        mNewsList.add(userDataSnapshot.getValue(News.class));
                        adapter.notifyDataSetChanged();
                        mBookmarkRecycler.setAdapter(adapter);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        mNewsList.clear();

        if (query.length() != 0) {
            mSearchQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        News news = dataSnapshot1.getValue(News.class);
                        mNewsList.add(news);
                    }
                    searchAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onSearchClicked(View view, int position, Bundle bundle) {

    }
}
