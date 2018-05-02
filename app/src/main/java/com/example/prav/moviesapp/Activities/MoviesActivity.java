package com.example.prav.moviesapp.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;

import com.example.prav.moviesapp.Adapters.OnItemClickListener;
import com.example.prav.moviesapp.Model.Movie;
import com.example.prav.moviesapp.Adapters.MovieAdapter;
import com.example.prav.moviesapp.R;
import com.example.prav.moviesapp.ViewModels.MoviesActivityModel;
import com.google.gson.Gson;

import java.util.List;

public class MoviesActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, OnItemClickListener {

    private RecyclerView moviesView;
    private MoviesActivityModel moviesActivityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        moviesView = findViewById(R.id.movies_recycler);
        moviesView.setLayoutManager(new GridLayoutManager(this, 2));
        moviesView.setItemAnimator(new DefaultItemAnimator());
        moviesActivityModel = ViewModelProviders.of(this).get(MoviesActivityModel.class);
        moviesActivityModel.movies.observe(this, (movies) -> {
            if (movies != null && movies.size() > 0) {
                updateMovieRecycler(movies);
            }
        });
        setupPreferences();
    }

    /**
     * Update the movie list based on the user sorting preferences
     *
     * @param movies list of movies
     */
    private void updateMovieRecycler(List<Movie> movies) {
        moviesView.setAdapter(null);
        MovieAdapter movieAdapter = new MovieAdapter(movies, this);
        moviesView.setAdapter(movieAdapter);
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
        moviesActivityModel.loadMovies(val);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public void setSortTitle(String key) {
        if (key.equals("pop")) {
            setTitle(getString(R.string.pref_sort_popular));
        } else {
            setTitle(getString(R.string.pref_sort_toprated));
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getString(R.string.pref_sort_key))) {
            String val = sharedPreferences.getString(getString(R.string.pref_sort_key), "pop");
            setSortTitle(val);
            moviesActivityModel.loadMovies(val);
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
