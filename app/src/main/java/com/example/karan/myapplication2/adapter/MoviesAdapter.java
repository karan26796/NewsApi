package com.example.karan.myapplication2.adapter;

import android.annotation.SuppressLint;
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
import com.example.karan.myapplication2.retrofit.moviedb.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by karan on 5/10/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private List<Movie> movies;
    private movieClickListener onMovieClicked;


    public MoviesAdapter(List<Movie> movies, movieClickListener onMovieClicked) {
        this.movies = movies;
        this.onMovieClicked = onMovieClicked;
    }

    public interface movieClickListener {
        void onMovieClicked(MoviesAdapter.MoviesViewHolder viewHolder, int position, Bundle bundle);
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MoviesAdapter.MoviesViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, int position) {
        holder.mMovieName.setText(movies.get(position).getTitle());
        holder.mMovieOverview.setText(movies.get(position).getOverview());
        holder.mMovieDate.setText(movies.get(position).getReleaseDate());
        holder.mMovieVoteAverage.setText(movies.get(position).getVoteAverage().toString());
        holder.progressBar.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(movies.get(position).getBackdropPath())
                .into(holder.mMoviePoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        holder.mMoviePoster.setImageResource(R.drawable.ic_menu_camera);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mMovieName, mMovieDate, mMovieOverview, mMovieVoteAverage;
        ImageView mMoviePoster;
        ProgressBar progressBar;

        public MoviesViewHolder(View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(android.R.id.progress);
            mMovieName = itemView.findViewById(R.id.text_movie_title);
            mMovieOverview = itemView.findViewById(R.id.text_movie_overview);
            mMovieDate = itemView.findViewById(R.id.text_movie_release_date);
            mMovieVoteAverage = itemView.findViewById(R.id.text_movie_rating);

            mMoviePoster = itemView.findViewById(R.id.image_movie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("movie", movies.get(getAdapterPosition()));
            try {
                onMovieClicked.onMovieClicked(this, getAdapterPosition(), bundle);
            } catch (NullPointerException e) {
                Log.d("Adapter", e.getMessage());
            }
        }
    }
}
