package com.example.prav.moviesapp.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;

import com.example.prav.moviesapp.Adapters.OnItemClickListener;
import com.example.prav.moviesapp.Model.Movie;
import com.example.prav.moviesapp.Adapters.MovieAdapter;
import com.example.prav.moviesapp.R;
import com.example.prav.moviesapp.ViewModels.MoviesActivityModel;
import com.example.prav.moviesapp.databinding.ActivityMoviesBinding;
import com.google.gson.Gson;

import java.util.List;

public class MoviesActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, OnItemClickListener {
    private ActivityMoviesBinding activityMoviesBinding;
    private MoviesActivityModel moviesActivityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMoviesBinding = DataBindingUtil.setContentView(this, R.layout.activity_movies);
        activityMoviesBinding.moviesRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        activityMoviesBinding.moviesRecycler.setItemAnimator(new DefaultItemAnimator());
        moviesActivityModel = ViewModelProviders.of(this).get(MoviesActivityModel.class);
        moviesActivityModel.movies.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies != null && movies.size() > 0) {
                    updateMovieRecycler(movies);
                    hideNoMovies(true);
                } else {
                    activityMoviesBinding.moviesRecycler.setAdapter(null);
                    hideNoMovies(false);
                }
            }
        });
        setupPreferences();
    }

    private void hideNoMovies(boolean val) {
        if (val) {
            activityMoviesBinding.moviesRecycler.setVisibility(View.VISIBLE);
            activityMoviesBinding.noMoviesContainer.setVisibility(View.INVISIBLE);
        } else {
            activityMoviesBinding.moviesRecycler.setVisibility(View.INVISIBLE);
            activityMoviesBinding.noMoviesContainer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Update the movie list based on the user sorting preferences
     *
     * @param movies list of movies
     */
    private void updateMovieRecycler(List<Movie> movies) {
        activityMoviesBinding.moviesRecycler.setAdapter(null);
        MovieAdapter movieAdapter = new MovieAdapter(movies, this);
        activityMoviesBinding.moviesRecycler.setAdapter(movieAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(startSettingsActivity);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     */
    private void setupPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String val = sharedPreferences.getString(getString(R.string.pref_sort_key), "pop");
        setSortTitle(val);
        moviesActivityModel.loadMovies(val, this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public void setSortTitle(String key) {
        if (key.equals("pop")) {
            setTitle(getString(R.string.pref_sort_popular));
        } else if (key.equals(getString(R.string.pref_sort_toprated))) {
            setTitle(getString(R.string.pref_sort_toprated));
        } else {
            setTitle(getString(R.string.pref_sort_fav));
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getString(R.string.pref_sort_key))) {
            String val = sharedPreferences.getString(getString(R.string.pref_sort_key), "pop");
            setSortTitle(val);
            moviesActivityModel.loadMovies(val, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String val = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.pref_sort_key), "pop");
        if (val.equals("fav")) {
            moviesActivityModel.loadMovies(val, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
        Gson gson = new Gson();
        intent.putExtra("movie_selected", gson.toJson(movie));
        startActivity(intent);
    }

    @Override
    public void onItemClick(String url) {

    }
}
