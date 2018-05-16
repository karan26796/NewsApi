package com.example.karan.myapplication2.adapter;

import android.content.Context;
import android.os.Bundle;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by karan on 5/14/2018.
 */

public class FirebaseSearchAdapter extends FirebaseRecyclerAdapter<News, FirebaseSearchAdapter.NewsViewHolder> {

    private onSearchClickListener mListener;
    private ArrayList<News> mNewsList;

    public FirebaseSearchAdapter(Class<News> modelClass, int modelLayout,
                                 Class<NewsViewHolder> viewHolderClass, Query ref,
                                 ArrayList<News> newsArrayList, onSearchClickListener mListener) {
        super(modelClass, modelLayout, viewHolderClass, ref);

        this.mListener = mListener;
        this.mNewsList = newsArrayList;
    }

    public interface onSearchClickListener {
        void onSearchClicked(View view, int position, Bundle bundle);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);

        return new FirebaseSearchAdapter.NewsViewHolder(itemView);
    }

    @Override
    protected void populateViewHolder(final NewsViewHolder viewHolder, News model, int position) {
        viewHolder.mHead.setText(mNewsList.get(position).getTitle());
        viewHolder.mDetail.setText(mNewsList.get(position).getDescription());
        viewHolder.mDate.setText(mNewsList.get(position).getDate().concat(" |")
                .concat(mNewsList.get(position).getSource().getName()));
        if (mNewsList.get(position).getAuthor() != null)
            viewHolder.mAuthor.setText(mNewsList.get(position).getAuthor());
        else viewHolder.mAuthor.setText("Anonymous");
        viewHolder.mProgress.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(mNewsList.get(position).getUrlToImage())
                .into(viewHolder.mNewsImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.mProgress.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        viewHolder.mProgress.setVisibility(View.INVISIBLE);
                    }
                });
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mHead, mAuthor, mDetail, mDate, mSource;
        ImageView mNewsImage;
        ProgressBar mProgress;

        public NewsViewHolder(View itemView) {
            super(itemView);

            mProgress = itemView.findViewById(android.R.id.progress);
            mHead = itemView.findViewById(R.id.text_news_headline);
            mAuthor = itemView.findViewById(R.id.text_news_author);
            mDate = itemView.findViewById(R.id.text_news_date);
            mDetail = itemView.findViewById(R.id.text_news_detail);
            //mSource = itemView.findViewById(R.id.text_news_source);

            mNewsImage = itemView.findViewById(R.id.image_news);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("news", mNewsList.get(getAdapterPosition()));
            try {
                mListener.onSearchClicked(v, getAdapterPosition(), bundle);
            } catch (NullPointerException e) {
                Log.e("History News", e.getMessage());
            }
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
}
