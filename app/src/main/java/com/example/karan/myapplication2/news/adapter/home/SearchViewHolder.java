package com.example.karan.myapplication2.news.adapter.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.retrofit.news.general.News;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karan on 5/14/2018.
 */

public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.text_news_headline)
    TextView mHead;
    @BindView(R.id.text_news_author)
    TextView mAuthor;
    @BindView(R.id.text_news_detail)
    TextView mDetail;
    @BindView(R.id.text_news_date)
    TextView mDate;
    @BindView(R.id.image_news)
    ImageView mNewsImage;
    @BindView(android.R.id.progress)
    ProgressBar mProgress;
    News news;
    public onSearchItemClickListener mListener;

    public interface onSearchItemClickListener {
        void onItemClicked(View view, int position, Bundle bundle);
    }

    public SearchViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        //mSource = itemView.findViewById(R.id.text_news_source);
    }

    public void bindData(final News news) {
        this.news = news;
        mProgress.setVisibility(View.VISIBLE);
        mHead.setText(news.title);
        mDetail.setText(news.description);
        mAuthor.setText(news.author);
        mDate.setText(news.date);
        Picasso.get()
                .load(news.urlToImage)
                .placeholder(R.drawable.ic_landscape)
                .into(mNewsImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        mProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        mProgress.setVisibility(View.GONE);
                    }
                });
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
