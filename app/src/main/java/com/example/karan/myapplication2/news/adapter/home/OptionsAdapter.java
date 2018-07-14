package com.example.karan.myapplication2.news.adapter.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.model.Options;

import java.util.ArrayList;

/**
 * Created by karan on 5/15/2018.
 */

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.OptionsViewHolder> {
    private ArrayList<Options> mOptionsList;
    private optionsClickListener mListener;

    public OptionsAdapter(ArrayList<Options> mOptionsList, optionsClickListener mListener) {
        this.mOptionsList = mOptionsList;
        this.mListener = mListener;
    }

    public interface optionsClickListener {
        void onOptionsClicked(View view, int position, OptionsViewHolder viewHolder);
    }

    @NonNull
    @Override
    public OptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_options,
                parent, false);
        return new OptionsAdapter.OptionsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsViewHolder holder, int position) {
        holder.mTitle.setText(mOptionsList.get(position).getTitle());
        holder.mOptionImage.setImageResource(mOptionsList.get(position).getDrawable());
    }

    @Override
    public int getItemCount() {
        return mOptionsList.size();
    }

    public class OptionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;
        ImageView mOptionImage;

        public OptionsViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.text_option);
            mOptionImage = itemView.findViewById(R.id.image_options);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onOptionsClicked(v, getAdapterPosition(), this);
        }
    }
}
