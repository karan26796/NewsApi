package com.example.karan.myapplication2.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.retrofit.news.general.News;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends BaseActivity {
    TextView mNewsHead, mNewsDetail, mNewsDate, mNewsAuthor;
    ImageView mNewsImage;

    Bundle bundle;
    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        setUpToolbar(0);

        mNewsHead = findViewById(R.id.text_news_detail_title);
        mNewsDate = findViewById(R.id.text_news_detail_date);
        mNewsAuthor = findViewById(R.id.text_news_detail_author);
        mNewsDetail = findViewById(R.id.text_news_detail_description);

        mNewsImage = findViewById(R.id.image_news_detail);

        bundle = getIntent().getExtras();
        if (bundle.getParcelable("news") != null) {
            news = bundle.getParcelable("news");
            mNewsHead.setText(news.getTitle());
            mNewsAuthor.setText(news.getAuthor());
            mNewsDate.setText(news.getDate());
            mNewsDetail.setText(news.getDescription());
            mToolbar.setTitle("");

            Picasso.get()
                    .load(news.getUrlToImage())
                    .into(mNewsImage, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(NewsDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    protected int getToolbarID() {
        return R.id.activity_news_detail_toolbar;
    }
}
