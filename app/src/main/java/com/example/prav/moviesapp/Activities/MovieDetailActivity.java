package com.example.prav.moviesapp.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
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
public class MovieDetailActivity extends AppCompatActivity implements ResponseCallback, OnItemClickListener {
    private ActivityMovieDetailBinding activityMovieDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        setTitle(getString(R.string.movie_detail));
        String movieStr = getIntent().getStringExtra("movie_selected");
        Gson gson = new Gson();
        Movie movie = gson.fromJson(movieStr, Movie.class);
        if (movie != null) {
            RestCaller.loadClips(movie.getMovieId(), this, true);
            updateView(movie);
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

    @Override
    public void onResponseMovieClips(List<Movie> jsonResponse) {
        List<String> urls = new ArrayList<>();
        for (Movie movie : jsonResponse) {
            urls.add(ServiceConfig.youtubeUrl + movie.getVideoClip());
        }
        activityMovieDetailBinding.trailersView.setLayoutManager(new LinearLayoutManager(this));
        TrailerAdapter trailerAdapter = new TrailerAdapter(urls, this);
        activityMovieDetailBinding.trailersView.setAdapter(trailerAdapter);
    }

    @Override
    public void onResponseReviews(JsonArray jsonArray) {
        if (jsonArray.size() > 0) {
            activityMovieDetailBinding.reviewsList.setLayoutManager(new LinearLayoutManager(this));
            ReviewAdapter reviewAdapter = new ReviewAdapter(jsonArray);
            activityMovieDetailBinding.reviewsList.setAdapter(reviewAdapter);
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

    public void loadReviews(String movieID) {

    }

}
