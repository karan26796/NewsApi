package com.example.karan.myapplication2.news.adapter.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.retrofit.news.general.News;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


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

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId;
        View itemView;
        if (viewType == 0)
            layoutId = R.layout.item_news_headlines;
        else layoutId = R.layout.item_news_headlines_small;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutId,
                        parent, false);
        return new HeadlineNewsAdapter.NewsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsHolder holder, final int position) {
        holder.mHead.setText(mNewsList.get(position).getTitle());
        holder.mDate.setText(mNewsList.get(position).getSource().getName());
        holder.mProgress.setVisibility(View.VISIBLE);
        if (mNewsList.get(position).isBookmarked == 1)
            holder.bookmarkBtn.setImageResource(R.drawable.ic_bookmark_filled);
        else holder.bookmarkBtn.setImageResource(R.drawable.ic_bookmark);

        Picasso.get()
                .load(mNewsList.get(position).getUrlToImage())
                .transform(new RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL))
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

        holder.bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNewsList.get(position).isBookmarked == 1) {
                    mNewsList.get(position).setIsBookmarked(0);
                    notifyItemChanged(position);
                } else if (mNewsList.get(position).isBookmarked == 0) {
                    mNewsList.get(position).setIsBookmarked(1);
                    notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.text_news_headline_top)
        TextView mHead;
        @BindView(R.id.text_news_date_top)
        TextView mDate;
        @BindView(R.id.image_news_top)
        ImageView mNewsImage;
        @BindView(android.R.id.progress)
        ProgressBar mProgress;
        @BindView(R.id.bookmark_btn)
        ImageButton bookmarkBtn;

        NewsHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            //mAuthor = itemView.findViewById(R.id.text_news_author_top);
            //mDetail = itemView.findViewById(R.id.text_news_detail_top);
            //mSource = itemView.findViewById(R.id.text_news_source_top);

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
