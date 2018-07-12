package com.example.karan.myapplication2.adapter;

import android.os.Bundle;
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

public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mHead, mAuthor, mDetail, mDate, mSource;
    private ImageView mNewsImage;
    private ProgressBar mProgress;
    News news;
    public onSearchItemClickListener mListener;

    public interface onSearchItemClickListener {
        void onItemClicked(View view, int position, Bundle bundle);
    }

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

    public void bindData(final News news) {
        this.news = news;
        mHead.setText(news.title);
        mDetail.setText(news.description);
        mAuthor.setText(news.author);
        mDate.setText(news.date.concat(" | ").concat(news.source.name));
        Picasso.get()
                .load(news.urlToImage)
                .placeholder(R.drawable.ic_landscape)
                .into(mNewsImage);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("url", news.url);
//        mListener.onItemClicked(v, getAdapterPosition(), bundle);
        //  Toast.makeText(itemView.getContext(), getAdapterPosition(), Toast.LENGTH_SHORT).show();
    }
}
