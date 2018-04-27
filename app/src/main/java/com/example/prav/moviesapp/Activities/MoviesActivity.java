package com.example.prav.moviesapp.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.example.prav.moviesapp.Model.Movie;
import com.example.prav.moviesapp.Adapters.MovieAdapter;
import com.example.prav.moviesapp.R;
import com.example.prav.moviesapp.ViewModels.MoviesActivityModel;

import java.util.List;

public class MoviesActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView moviesView;
    private MoviesActivityModel moviesActivityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        moviesView = findViewById(R.id.movies_recycler);
        moviesView.setLayoutManager(new GridLayoutManager(this, 3));
        moviesView.setItemAnimator(new DefaultItemAnimator());
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
        MovieAdapter movieAdapter = new MovieAdapter(movies);
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
     * Setup and register the shared preferences
     */
    private void setupPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String val = sharedPreferences.getString(getString(R.string.pref_sort_key), "pop");
        moviesActivityModel.loadMovies(val);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getString(R.string.pref_sort_key))) {
            String val = sharedPreferences.getString(getString(R.string.pref_sort_key), "pop");
            moviesActivityModel.loadMovies(val);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

}
