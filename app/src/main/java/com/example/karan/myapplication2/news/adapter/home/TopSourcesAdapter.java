package com.example.karan.myapplication2.news.adapter.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class TopSourcesAdapter extends RecyclerView.Adapter<TopSourcesAdapter.SourcesViewHolder> {

    public TopSourcesAdapter() {
    }

    @NonNull
    @Override

    public SourcesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_sources, parent, false);
        return new SourcesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SourcesViewHolder holder, int position) {
        holder.sourceText.setText("News Source");
        Picasso.get()
                .load(R.drawable.ic_account_circle)
                .transform(new CropCircleTransformation())
                .into(holder.sourceImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class SourcesViewHolder extends RecyclerView.ViewHolder {
        TextView sourceText;
        ImageView sourceImage;

        public SourcesViewHolder(@NonNull View itemView) {
            super(itemView);

            sourceText = itemView.findViewById(R.id.text_top_source);
            sourceImage = itemView.findViewById(R.id.image_top_source);
        }

    }
}
