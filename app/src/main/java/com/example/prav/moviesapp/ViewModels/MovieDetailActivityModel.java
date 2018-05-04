package com.example.prav.moviesapp.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.prav.moviesapp.Database.DBTask;
import com.example.prav.moviesapp.Database.Favorite;
import com.example.prav.moviesapp.Interfaces.IDBTask;
import com.example.prav.moviesapp.Interfaces.ResponseCallback;
import com.example.prav.moviesapp.Model.Movie;
import com.example.prav.moviesapp.Services.RestCaller;
import com.example.prav.moviesapp.Services.ServiceConfig;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivityModel extends ViewModel implements ResponseCallback, IDBTask {
    public MutableLiveData<Movie> movie = new MutableLiveData<>();
    public MutableLiveData<List<String>> clipsUrl = new MutableLiveData<>();
    public MutableLiveData<JsonArray> reviews = new MutableLiveData<>();
    public MutableLiveData<Boolean> isFav = new MutableLiveData<>();
    public MutableLiveData<Integer> counts = new MutableLiveData<>();
    public void loadClipsAndReview(String movieStr){
        Gson gson = new Gson();
        Movie movie = gson.fromJson(movieStr, Movie.class);
        if (movie != null) {
            this.movie.setValue(movie);
            RestCaller.loadClips(movie.getMovieId(), this, true);

        }
    }
    public void insertFav(Movie movie,Context context){
        DBTask dbTask = new DBTask();
        dbTask.insertFav(movie, context);
        isFav.setValue(true);
    }

    public void delFav(String movieId,Context context){
        DBTask dbTask = new DBTask();
        dbTask.delFav(movieId, context);
        isFav.setValue(false);
    }

    public void getFav(Context context, String movieId){
        DBTask dbTask = new DBTask();
        dbTask.getFav(context,this, movieId);
    }

    @Override
    public void onResponseMovieClips(List<Movie> jsonResponse) {
        List<String> urls = new ArrayList<>();
        for (Movie movie : jsonResponse) {
            urls.add(ServiceConfig.youtubeUrl + movie.getVideoClip());
        }
        clipsUrl.setValue(urls);
    }

    @Override
    public void onResponseReviews(JsonArray jsonArray) {
        reviews.setValue(jsonArray);
    }

    @Override
    public void onFetch(Favorite favorite) {
        if(favorite==null){
            isFav.setValue(false);
        }else {
            isFav.setValue(true);
        }
    }

    @Override
    public void onFetchAll(List<Favorite> favorites) {

    }
}
