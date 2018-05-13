package com.example.karan.myapplication2.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.retrofit.moviedb.Movie;
import com.example.karan.myapplication2.webview.CustomTabActivityHelper;
import com.example.karan.myapplication2.webview.WebViewFallback;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends BaseActivity {
    TextView mMovieTitle, mMovieDetail, mMovieDate;
    ImageView mMoviePoster;
    Bundle bundle;
    Movie movie;
    CustomTabActivityHelper mCustomTabActivityHelper;
    private CustomTabActivityHelper.CustomTabConnectionCallback mConnectionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setUpToolbar(0);
        setupCustomTabHelper();

        mMovieDetail = findViewById(R.id.text_detail_detail);
        mMovieDate = findViewById(R.id.text_detail_date);
        mMovieTitle = findViewById(R.id.text_detail_title);
        mMoviePoster = findViewById(R.id.image_movie_poster);

        bundle = getIntent().getExtras();
        if (bundle.getParcelable("movie") != null) {
            movie = bundle.getParcelable("movie");
            mToolbar.setTitle(movie.getTitle());
            Picasso.get()
                    .load(movie.getBackdropPath())
                    .into(mMoviePoster);
            mMovieTitle.setText(movie.getTitle());
            mMovieDetail.setText(movie.getOverview());
            mMovieDate.setText(movie.getReleaseDate());
        }
    }

    @Override
    protected int getToolbarID() {
        return R.id.activity_movie_detail_toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_url:

                String url = "https://api.themoviedb.org/3/movie/" + movie.getId() + "?api_key=7e8f60e325cd06e164799af1e317d7a7";
                assert movie.getId() != null;
                launchCustomTab(url);
                break;
        }
        return super.onOptionsItemSelected(item);
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
}
