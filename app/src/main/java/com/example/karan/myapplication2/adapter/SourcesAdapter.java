package com.example.karan.myapplication2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.model.Sources;

import java.util.ArrayList;
import java.util.List;

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.SourcesViewHolder> {
    private List<Sources> mSourcesList;
    private List<Sources> mSelectedSourcesList = new ArrayList<>();

    public SourcesAdapter(List<Sources> mSourcesList) {
        this.mSourcesList = mSourcesList;
    }

    @NonNull
    @Override

    public SourcesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_source, parent, false);
        return new SourcesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SourcesViewHolder holder, int position) {
        holder.sourceText.setText(mSourcesList.get(position).getSource());
    }

    @Override
    public int getItemCount() {
        return mSourcesList.size();
    }

    public class SourcesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView sourceText;

        public SourcesViewHolder(@NonNull View itemView) {
            super(itemView);

            sourceText = itemView.findViewById(R.id.text_source);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == itemView) {
                if (mSelectedSourcesList.contains(mSourcesList.get(getAdapterPosition()))) {
                    mSelectedSourcesList.remove(mSourcesList.get(getAdapterPosition()));
                    view.setBackgroundResource(R.drawable.sources_bg_selected);
                } else if (!mSelectedSourcesList.contains(mSourcesList.get(getAdapterPosition()))) {
                    mSelectedSourcesList.add(mSourcesList.get(getAdapterPosition()));
                    view.setBackgroundResource(R.drawable.sources_bg);
                }
            }
        }
    }
}
