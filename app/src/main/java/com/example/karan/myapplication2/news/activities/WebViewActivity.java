package com.example.karan.myapplication2.news.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.karan.myapplication2.R;

public class WebViewActivity extends BaseActivity {

    WebView webView;
    String url, urlNews;
    int id;
    Bundle bundle;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        setUpToolbar(0);
        bundle = getIntent().getExtras();
        /*if (bundle.get("news") != null) {
            News news = bundle.getParcelable("news");
            url = news.getUrl();
        } else {*/
        id = getIntent().getIntExtra("movieID", 0);


        webView = findViewById(R.id.webView);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
        webView.setWebViewClient(new MyBrowser());
        url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=7e8f60e325cd06e164799af1e317d7a7";

        webView.loadUrl(url);

        setupActionBar(webView.getUrl());
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!url.startsWith("https://") || url.startsWith("http://")) {
                return false;
            }
            WebViewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            return true;
        }

    }

    private void setupActionBar(String url) {
        setSupportActionBar(this.mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_close);
            actionBar.setTitle("");
        }
    }

    @Override
    protected int getToolbarID() {
        return R.id.activity_web_view_toolbar;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
