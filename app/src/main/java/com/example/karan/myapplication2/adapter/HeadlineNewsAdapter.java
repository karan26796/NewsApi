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

public class HeadlineNewsAdapter extends RecyclerView.Adapter<HeadlineNewsAdapter.NewsHolder> {

    private List<News> mNewsList;
    private onNewsClickListener mClickListener;

    public HeadlineNewsAdapter(List<News> mNewsList, onNewsClickListener mClickListener) {
        this.mNewsList = mNewsList;
        this.mClickListener = mClickListener;
    }

    public interface onNewsClickListener {
        void onNewsClicked(View view, int position, Bundle bundle);
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_headlines,
                        parent, false);
        return new HeadlineNewsAdapter.NewsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsHolder holder, int position) {
        holder.mHead.setText(mNewsList.get(position).getTitle());
        //holder.mAuthor.setText(mNewsList.get(position).getAuthor());
        //holder.mDetail.setText(mNewsList.get(position).getDescription());
        //holder.mSource.setText(mNewsList.get(position).getSource().getName());
        String source = "Source: ";
        holder.mDate.setText(source.concat(mNewsList.get(position).getSource()
                .getName().concat(" | ")
                .concat(mNewsList.get(position).getDate().substring(0, 10))));
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
                        holder.mNewsImage.setImageResource(R.drawable.ic_menu_share);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mHead, mAuthor, mDetail, mDate, mSource;
        ImageView mNewsImage;
        ProgressBar mProgress;

        NewsHolder(View itemView) {
            super(itemView);

            mProgress = itemView.findViewById(android.R.id.progress);
            mHead = itemView.findViewById(R.id.text_news_headline_top);
            //mAuthor = itemView.findViewById(R.id.text_news_author_top);
            mDate = itemView.findViewById(R.id.text_news_date_top);
            //mDetail = itemView.findViewById(R.id.text_news_detail_top);
            //mSource = itemView.findViewById(R.id.text_news_source_top);

            mNewsImage = itemView.findViewById(R.id.image_news_top);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("news", mNewsList.get(getAdapterPosition()));
            mClickListener.onNewsClicked(v, getAdapterPosition(), bundle);
        }
    }
}
