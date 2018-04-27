package com.example.prav.moviesapp.Activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.prav.moviesapp.Model.Movie;
import com.example.prav.moviesapp.R;
import com.example.prav.moviesapp.Services.ServiceConfig;
import com.example.prav.moviesapp.databinding.ActivityMovieDetailBinding;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Activity which loads selected movie from the MoviesActivity
 */
public class MovieDetailActivity extends AppCompatActivity {
    private ActivityMovieDetailBinding activityMovieDetailBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        String movieStr = getIntent().getStringExtra("movie_selected");
        Gson gson =new Gson();
        Movie movie = gson.fromJson(movieStr,Movie.class);
        if(movie!=null){
            updateView(movie);
        }
    }

    /**
     * Updates the UI based on the movie being passed through the intent
     * @param movie selected movie
     */
    private void updateView(Movie movie){
        setTitle(movie.getMovieName());
        Picasso.with(this)
                .load(ServiceConfig.IMG_BASEURL+movie.getMovieImgUrl())
                .into(activityMovieDetailBinding.movieImg);
        activityMovieDetailBinding.setMovie(movie);
    }

}
