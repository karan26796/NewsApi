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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


/**
 * Created by karan on 5/8/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private List<News> mNewsList;
    private onNewsClickListener mClickListener;
    private int length;
    private DatabaseReference mDatabaseHistory, mDatabaseBookmark;

    public NewsAdapter(List<News> mNewsList, onNewsClickListener mClickListener, int length) {
        this.mNewsList = mNewsList;
        this.mClickListener = mClickListener;
        this.length = length;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mDatabaseHistory = FirebaseDatabase.getInstance().getReference()
                .child("History")
                .child(mAuth.getCurrentUser().getUid());
        mDatabaseBookmark = FirebaseDatabase.getInstance().getReference()
                .child("Bookmarks")
                .child(mAuth.getCurrentUser().getUid());
    }

    public interface onNewsClickListener {
        void onNewsClicked(View view, int position, Bundle bundle);

        void onBookmarkClicked(View view, int position, Bundle bundle);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId;
        if (viewType == 0) {
            layoutId = R.layout.item_news;
        } else layoutId = R.layout.item_news_small;
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutId,
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

        holder.mDate.setText(mNewsList.get(position).getDate().substring(0, 10)
                .concat(" | ").concat(mNewsList.get(position).getSource().getName()));
        //holder.mSource.setText(mNewsList.get(position).getSource().getName());
        holder.mProgress.setVisibility(View.VISIBLE);
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
                    }
                });
    }

    @Override
    public int getItemCount() {
        return length;
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        @BindView(R.id.bookmark_btn)
        ImageButton mBookmarkBtn;

        NewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //mSource = itemView.findViewById(R.id.text_news_source);
            itemView.setOnClickListener(this);
            mBookmarkBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("news", mNewsList.get(getAdapterPosition()));
            if (v == itemView) {
                try {
                    mClickListener.onNewsClicked(v, getAdapterPosition(), bundle);
                    mDatabaseHistory.child(mNewsList.get(getAdapterPosition()).getDate())
                            .setValue(mNewsList.get(getAdapterPosition()));
                } catch (NullPointerException e) {
                    Log.e("news", e.getMessage());
                }
            }
            if (v == mBookmarkBtn) {
                try {
                    mClickListener.onBookmarkClicked(v, getAdapterPosition(), bundle);
                } catch (NullPointerException e) {
                    Log.e("news", e.getMessage());
                }
            }
        }
    }
}
