package com.example.karan.myapplication2.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.adapter.FirebaseAdapter;
import com.example.karan.myapplication2.adapter.FirebaseSearchAdapter;
import com.example.karan.myapplication2.adapter.SearchViewHolder;
import com.example.karan.myapplication2.retrofit.news.general.News;
import com.example.karan.myapplication2.webview.CustomTabActivityHelper;
import com.example.karan.myapplication2.webview.WebViewFallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Created by karan on 5/16/2018.
 */

public class SearchFragment extends Fragment implements FirebaseAdapter.onNewsHistoryClickListener,
        TextWatcher {

    TextView mErrorText;
    EditText editText;
    RecyclerView mSearchRecycler;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    CustomTabActivityHelper mCustomTabActivityHelper;
    CustomTabActivityHelper.CustomTabConnectionCallback mConnectionCallback;

    ArrayList<News> mNewsList = new ArrayList<>();

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


        editText = view.findViewById(R.id.search_text);
        mErrorText = view.findViewById(R.id.text_error);
        mSearchRecycler = view.findViewById(R.id.search_recycler);
        mSearchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mSearchRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        editText.addTextChangedListener(this);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                readData(editText.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
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

    public void readData(String text) {
        Query mQuery = mDatabase.orderByChild("title")
                .startAt(text)
                .endAt(text + "\uf8ff");
        FirebaseRecyclerAdapter<News, SearchViewHolder> adapter = new
                FirebaseRecyclerAdapter<News, SearchViewHolder>(News.class,
                        R.layout.item_news, SearchViewHolder.class, mQuery) {
                    @Override
                    protected void populateViewHolder(SearchViewHolder viewHolder, News model, int position) {
                        viewHolder.bindData(getContext(), model);
                    }
                };
        mSearchRecycler.setAdapter(adapter);
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() != 0) {
            readData(s.toString());
        } else {
            readData(" ");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
