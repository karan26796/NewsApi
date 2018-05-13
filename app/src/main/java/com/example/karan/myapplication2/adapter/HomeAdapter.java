package com.example.karan.myapplication2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.model.Home;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by karan on 5/3/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private ArrayList<Home> mHomeArrayList;
    private onClickListener mClickListener;

    public HomeAdapter(ArrayList<Home> mHomeArrayList, onClickListener mClickListener) {
        this.mHomeArrayList = mHomeArrayList;
        this.mClickListener = mClickListener;
    }

    public interface onClickListener {
        void onItemClicked(View v, int position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new HomeAdapter.HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, int position) {
        holder.mHeadTv.setText(mHomeArrayList.get(position).getHead());
        holder.mSubHeadTv.setText(mHomeArrayList.get(position).getSubHead());
        holder.mDetailTv.setText(mHomeArrayList.get(position).getDetail());
        holder.progressBar.setVisibility(View.VISIBLE);

        Picasso.get()
                .load("https://www.jqueryscript.net/images/Simplest-Responsive-jQuery-Image-Lightbox-Plugin-simple-lightbox.jpg")
                .into(holder.mHomeIv, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mHomeArrayList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mHeadTv, mSubHeadTv, mDetailTv;
        ImageView mHomeIv;
        ProgressBar progressBar;

        public HomeViewHolder(View itemView) {
            super(itemView);
            mHeadTv = itemView.findViewById(R.id.text_head);
            mSubHeadTv = itemView.findViewById(R.id.text_subhead);
            mDetailTv = itemView.findViewById(R.id.text_detail);

            mHomeIv = itemView.findViewById(R.id.image_home);
            progressBar = itemView.findViewById(android.R.id.progress);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClicked(v, getAdapterPosition());
        }
    }
}
