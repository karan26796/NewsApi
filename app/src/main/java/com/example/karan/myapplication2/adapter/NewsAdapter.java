package com.example.karan.myapplication2.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.retrofit.news.general.News;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by karan on 5/8/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private List<News> mNewsList;
    private onNewsClickListener mClickListener;
    private int length;

    public NewsAdapter(List<News> mNewsList, onNewsClickListener mClickListener, int length) {
        this.mNewsList = mNewsList;
        this.mClickListener = mClickListener;
        this.length = length;
    }

    public interface onNewsClickListener {
        void onNewsClicked(View view, int position, Bundle bundle);
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news,
                        parent, false);
        return new NewsAdapter.NewsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsHolder holder, int position) {
        holder.mHead.setText(mNewsList.get(position).getTitle());
        if (mNewsList.get(position).getAuthor() != null)
            holder.mAuthor.setText(mNewsList.get(position).getAuthor());
        else
            holder.mAuthor.setText("Anonymous");

        if (mNewsList.get(position).getDescription() != null)
            holder.mDetail.setText(mNewsList.get(position).getDescription());
        else
            holder.mDetail.setText("No data found");

        holder.mDate.setText(mNewsList.get(position).getDate());
        holder.mSource.setText(mNewsList.get(position).getSource().getName());
        holder.mProgress.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(mNewsList.get(position).getUrlToImage())
                .into(holder.mNewsImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.mProgress.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.mProgress.setVisibility(View.INVISIBLE);
                        Log.e(this.toString(), e.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return length;
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mHead, mAuthor, mDetail, mDate, mSource;
        ImageView mNewsImage;
        ProgressBar mProgress;

        NewsHolder(View itemView) {
            super(itemView);

            mProgress = itemView.findViewById(android.R.id.progress);
            mHead = itemView.findViewById(R.id.text_news_headline);
            mAuthor = itemView.findViewById(R.id.text_news_author);
            mDate = itemView.findViewById(R.id.text_news_date);
            mDetail = itemView.findViewById(R.id.text_news_detail);
            mSource = itemView.findViewById(R.id.text_news_source);

            mNewsImage = itemView.findViewById(R.id.image_news);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("news", mNewsList.get(getAdapterPosition()));
            try {
                mClickListener.onNewsClicked(v, getAdapterPosition(), bundle);
            } catch (NullPointerException e) {
                Log.e("news", e.getMessage());
            }
        }
    }
}
