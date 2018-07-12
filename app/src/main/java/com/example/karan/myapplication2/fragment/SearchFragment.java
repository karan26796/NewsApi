package com.example.karan.myapplication2.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.adapter.FirebaseAdapter;
import com.example.karan.myapplication2.adapter.SearchViewHolder;
import com.example.karan.myapplication2.retrofit.news.general.News;
import com.example.karan.myapplication2.webview.CustomTabActivityHelper;
import com.example.karan.myapplication2.webview.WebViewFallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by karan on 5/16/2018.
 */

public class SearchFragment extends Fragment implements FirebaseAdapter.onNewsHistoryClickListener, SearchViewHolder.onSearchItemClickListener {

    TextView mErrorText;
    RecyclerView mSearchRecycler;
    TabLayout tabLayout;
    Menu menu;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    CustomTabActivityHelper mCustomTabActivityHelper;
    CustomTabActivityHelper.CustomTabConnectionCallback mConnectionCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        setupCustomTabHelper();

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
            mDatabase = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("History")
                    .child(mAuth.getCurrentUser().getUid());

        tabLayout = getActivity().findViewById(R.id.main_tab_layout);
        tabLayout.setVisibility(View.GONE);
        mErrorText = view.findViewById(R.id.text_error);
        mSearchRecycler = view.findViewById(R.id.search_recycler);
        mSearchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mSearchRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        menu.clear();
        inflater.inflate(R.menu.activity_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((AppCompatActivity) getActivity()).getSupportActionBar().getThemedContext());
        searchView.setIconified(false);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*if (query.length() != 0) {
                    if (query.length() >= 1)
                        readData(query.substring(0, 1).toUpperCase() +
                                query.substring(2, query.length()).toLowerCase());
                    else {
                        readData(query.toUpperCase());
                    }
                } else {
                    Toast.makeText(getContext(), "Empty Search Field", Toast.LENGTH_SHORT).show();
                }*/
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() != 0) {
                    readData(newText);
                } else {
                    readData(" ");
                }
                return true;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                          }
                                      }
        );

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

    public void readData(String text) {
        Query mQuery = mDatabase.orderByChild("title")
                .startAt(text)
                .endAt(text + "\uf8ff");
        FirebaseRecyclerAdapter<News, SearchViewHolder> adapter = new
                FirebaseRecyclerAdapter<News, SearchViewHolder>(News.class,
                        R.layout.item_news, SearchViewHolder.class, mQuery) {
                    @Override
                    protected void populateViewHolder(SearchViewHolder viewHolder, News model, int position) {
                        viewHolder.bindData(model);
                    }
                };
        mSearchRecycler.setAdapter(adapter);
        if (adapter.getItemCount() == 0) {
            mErrorText.setVisibility(View.GONE);
        } else {
            mErrorText.setVisibility(View.VISIBLE);
        }
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
    public void onItemClicked(View view, int position, Bundle bundle) {
        /*if (bundle.containsKey("url"))
            launchCustomTab(bundle.getString("url"));*/
    }
}
