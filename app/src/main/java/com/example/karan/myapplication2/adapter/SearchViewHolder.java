package com.example.karan.myapplication2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.retrofit.news.general.News;
import com.squareup.picasso.Picasso;

/**
 * Created by karan on 5/14/2018.
 */

public class SearchViewHolder extends RecyclerView.ViewHolder {
    private TextView mHead, mAuthor, mDetail, mDate, mSource;
    private ImageView mNewsImage;
    private ProgressBar mProgress;

    public SearchViewHolder(View itemView) {
        super(itemView);

        mProgress = itemView.findViewById(android.R.id.progress);
        mHead = itemView.findViewById(R.id.text_news_headline);
        mAuthor = itemView.findViewById(R.id.text_news_author);
        mDate = itemView.findViewById(R.id.text_news_date);
        mDetail = itemView.findViewById(R.id.text_news_detail);
        //mSource = itemView.findViewById(R.id.text_news_source);

        mNewsImage = itemView.findViewById(R.id.image_news);
    }

    public void bindData(final Context context, final News user) {
        mHead.setText(user.title);
        mDetail.setText(user.description);
        Picasso.get()
                .load(user.urlToImage)
                .placeholder(R.drawable.author_dot)
                .into(mNewsImage);

    }
}
