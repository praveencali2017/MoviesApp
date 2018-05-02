package com.example.prav.moviesapp.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.prav.moviesapp.Interfaces.IMoviesCallback;
import com.example.prav.moviesapp.Model.JSONResponse;
import com.example.prav.moviesapp.Model.Movie;
import com.example.prav.moviesapp.Services.MovieService;
import com.example.prav.moviesapp.Services.RestCaller;
import com.example.prav.moviesapp.Services.ServiceConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * View Model for the Activity MovieActivity
 */
public class MoviesActivityModel extends ViewModel implements IMoviesCallback {
    public MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    public Call<JSONResponse> res;

    /**
     * Loads the movies based on selected sorting preferences
     *
     * @param type sorting preferences
     */
    public void loadMovies(String type) {
        RestCaller.loadMovies(type, this);
    }

    @Override
    public void onLoadMovies(List<Movie> movies) {
        this.movies.setValue(movies);
    }
}
