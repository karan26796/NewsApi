package com.example.karan.myapplication2.webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.example.karan.myapplication2.news.activities.WebViewActivity;

/**
 * Created by karan on 4/1/2018.
 */

public class WebViewFallback implements CustomTabActivityHelper.CustomTabFallback {
    private static final String EXTRA_URL = "extra.url";

    @Override
    public void openUri(Activity activity, Uri uri) {
        Intent webViewIntent = new Intent(activity, WebViewActivity.class);
        webViewIntent.putExtra(EXTRA_URL, uri.toString());
        activity.startActivity(webViewIntent);
    }
}
