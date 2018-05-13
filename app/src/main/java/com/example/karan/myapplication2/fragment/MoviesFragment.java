package com.example.karan.myapplication2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.karan.myapplication2.R;
import com.example.karan.myapplication2.activities.MovieDetailActivity;
import com.example.karan.myapplication2.adapter.MoviesAdapter;
import com.example.karan.myapplication2.retrofit.moviedb.Movie;
import com.example.karan.myapplication2.retrofit.moviedb.MovieDBClient;
import com.example.karan.myapplication2.retrofit.moviedb.MovieDBInterface;
import com.example.karan.myapplication2.retrofit.moviedb.MoviesResponse;
import com.example.karan.myapplication2.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karan on 5/10/2018.
 */

public class MoviesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ShimmerRecyclerView mMovieRecycler;
    SwipeRefreshLayout movieRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        movieRefresh = view.findViewById(R.id.movieRefresh);
        mMovieRecycler = view.findViewById(R.id.recycler_movies);
        mMovieRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mMovieRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        movieRefresh.setOnRefreshListener(this);
        movieRefresh.setRefreshing(true);
        if (Constants.MOVIE_API_KEY.isEmpty()) {
            Toast.makeText(getContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
        }
        if (savedInstanceState == null) {
            getMovies("top_rated");
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_top_rated:
                getMovies("now_playing");
                Toast.makeText(getContext(), "hey! there", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onRefresh() {
        movieRefresh.setRefreshing(true);
        getMovies("top_rated");
        movieRefresh.setRefreshing(false);
    }

    private void getMovies(String category) {
        mMovieRecycler.showShimmerAdapter();
        final MovieDBInterface movieDBInterface = MovieDBClient.getClient()
                .create(MovieDBInterface.class);
        Call<MoviesResponse> call = movieDBInterface.getMovies(category, Constants.MOVIE_API_KEY);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
                mMovieRecycler.setAdapter(new MoviesAdapter(movies, new MoviesAdapter.movieClickListener() {
                    @Override
                    public void onMovieClicked(MoviesAdapter.MoviesViewHolder viewHolder, int position, Bundle bundle) {
                        Pair<View, String> p1 = Pair.create(viewHolder.itemView.findViewById(R.id.image_movie), "poster");
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                p1);
                        startActivity(new Intent(getContext(), MovieDetailActivity.class)
                                .putExtras(bundle), optionsCompat.toBundle());
                    }
                }));
                movieRefresh.setRefreshing(false);
                mMovieRecycler.hideShimmerAdapter();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
