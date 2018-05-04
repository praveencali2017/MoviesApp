package com.example.prav.moviesapp.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.prav.moviesapp.Database.DBTask;
import com.example.prav.moviesapp.Database.Favorite;
import com.example.prav.moviesapp.Interfaces.IDBTask;
import com.example.prav.moviesapp.Interfaces.IMoviesCallback;
import com.example.prav.moviesapp.Model.JSONResponse;
import com.example.prav.moviesapp.Model.Movie;
import com.example.prav.moviesapp.Services.MovieService;
import com.example.prav.moviesapp.Services.RestCaller;
import com.example.prav.moviesapp.Services.ServiceConfig;
import com.google.gson.Gson;

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
public class MoviesActivityModel extends ViewModel implements IMoviesCallback, IDBTask {
    public MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    public Call<JSONResponse> res;

    /**
     * Loads the movies based on selected sorting preferences
     *
     * @param type sorting preferences
     */
    public void loadMovies(String type, Context context) {
        if (type.equals("fav")) {
            DBTask dbTask = new DBTask();
            dbTask.getAllFav(this, context);
        } else {
            RestCaller.loadMovies(type, this);
        }

    }

    @Override
    public void onLoadMovies(List<Movie> movies) {
        this.movies.setValue(movies);
    }

    @Override
    public void onFetch(Favorite favorites) {

    }

    @Override
    public void onFetchAll(List<Favorite> favorites) {
        List<Movie> movies = new ArrayList<>();
        if (favorites != null && favorites.size() > 0) {
            for (Favorite fav : favorites) {
                Gson gson = new Gson();
                movies.add(gson.fromJson(fav.getMovie(), Movie.class));
            }
        }
        this.movies.setValue(movies);

    }
}
