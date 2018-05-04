package com.example.prav.moviesapp.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.prav.moviesapp.Adapters.OnItemClickListener;
import com.example.prav.moviesapp.Adapters.ReviewAdapter;
import com.example.prav.moviesapp.Adapters.TrailerAdapter;
import com.example.prav.moviesapp.Interfaces.ResponseCallback;
import com.example.prav.moviesapp.Model.JSONResponse;
import com.example.prav.moviesapp.Model.Movie;
import com.example.prav.moviesapp.R;
import com.example.prav.moviesapp.Services.MovieService;
import com.example.prav.moviesapp.Services.RestCaller;
import com.example.prav.moviesapp.Services.ServiceConfig;
import com.example.prav.moviesapp.ViewModels.MovieDetailActivityModel;
import com.example.prav.moviesapp.databinding.ActivityMovieDetailBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity which loads selected movie from the MoviesActivity
 */
public class MovieDetailActivity extends AppCompatActivity implements OnItemClickListener {
    private ActivityMovieDetailBinding activityMovieDetailBinding;
    private MovieDetailActivityModel movieDetailActivityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        setTitle(getString(R.string.movie_detail));
        movieDetailActivityModel = ViewModelProviders.of(this).get(MovieDetailActivityModel.class);
        movieDetailActivityModel.isFav.setValue(false);
        movieDetailActivityModel.loadClipsAndReview(getIntent().getStringExtra("movie_selected"));
        movieDetailActivityModel.movie.observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                updateView(movie);
            }
        });
        movieDetailActivityModel.isFav.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                setFavImg(aBoolean);
            }
        });
        onMovieClipsChange();
        onChangeInReviews();
        activityMovieDetailBinding.favContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!movieDetailActivityModel.isFav.getValue()) {
                    movieDetailActivityModel.insertFav(movieDetailActivityModel.movie.getValue(), getApplicationContext());
                } else {
                    movieDetailActivityModel.delFav(movieDetailActivityModel.movie.getValue().getMovieId(), getApplicationContext());
                }
            }
        });
        movieDetailActivityModel.getFav(getApplicationContext(), movieDetailActivityModel.movie.getValue().getMovieId());
    }

    private void setFavImg(boolean val) {
        if (val) {
            activityMovieDetailBinding.imgFav.setImageResource(R.mipmap.ic_favorite);
        } else {
            activityMovieDetailBinding.imgFav.setImageResource(R.mipmap.ic_favorite_border);
        }
    }

    /**
     * Updates the UI based on the movie being passed through the intent
     *
     * @param movie selected movie
     */
    private void updateView(Movie movie) {
        Picasso.with(this)
                .load(ServiceConfig.IMG_BASEURL.replace("w780", "w500") + movie.getMovieImgUrl())
                .into(activityMovieDetailBinding.movieImg);
        activityMovieDetailBinding.setMovie(movie);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void onMovieClipsChange() {
        movieDetailActivityModel.clipsUrl.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                activityMovieDetailBinding.trailersView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                TrailerAdapter trailerAdapter = new TrailerAdapter(strings, MovieDetailActivity.this);
                activityMovieDetailBinding.trailersView.setAdapter(trailerAdapter);
            }
        });
    }

    private void onChangeInReviews() {
        movieDetailActivityModel.reviews.observe(this, new Observer<JsonArray>() {
            @Override
            public void onChanged(@Nullable JsonArray jsonArray) {
                if (jsonArray.size() > 0) {
                    activityMovieDetailBinding.reviewsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    ReviewAdapter reviewAdapter = new ReviewAdapter(jsonArray);
                    activityMovieDetailBinding.reviewsList.setAdapter(reviewAdapter);
                    hideNoReviews(true);
                } else {
                    hideNoReviews(false);
                }

            }
        });

    }

    private void hideNoReviews(boolean val) {
        if (val) {
            activityMovieDetailBinding.noreviewTxt.setVisibility(View.INVISIBLE);
            activityMovieDetailBinding.reviewsList.setVisibility(View.VISIBLE);
        } else {
            activityMovieDetailBinding.reviewsList.setVisibility(View.INVISIBLE);
            activityMovieDetailBinding.noreviewTxt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(Movie movie) {

    }

    @Override
    public void onItemClick(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

}
