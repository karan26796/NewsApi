package com.example.karan.myapplication2.news.adapter.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.model.TopSources;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class TopSourcesAdapter extends RecyclerView.Adapter<TopSourcesAdapter.SourcesViewHolder> {

    private OnSourceClicked mListener;
    ArrayList<TopSources> mList;
    TopSources topSources = null;

    public interface OnSourceClicked {
        void onItemClilcked(int position);
    }

    public TopSourcesAdapter(OnSourceClicked mListener, ArrayList<TopSources> mList) {
        this.mListener = mListener;
        this.mList = mList;
    }

    @NonNull
    @Override

    public SourcesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_sources, parent, false);
        return new SourcesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SourcesViewHolder holder, int position) {
        if (topSources != null && topSources.equals(mList.get(position)))
            holder.sourceText.setText(mList.get(position).getName());
        else holder.sourceText.setText("This");

        Picasso.get()
                .load(mList.get(position).getDrawable())
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

    public class SourcesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.text_top_source)
        TextView sourceText;
        @BindView(R.id.image_top_source)
        ImageView sourceImage;

        public SourcesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (topSources == null) {
                topSources = mList.get(getAdapterPosition());
            } else
                topSources = mList.get(getAdapterPosition());

            mListener.onItemClilcked(getAdapterPosition());
        }
    }
}
