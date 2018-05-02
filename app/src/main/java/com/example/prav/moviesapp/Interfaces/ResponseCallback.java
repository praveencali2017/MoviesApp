package com.example.prav.moviesapp.Interfaces;

import com.example.prav.moviesapp.Model.Movie;
import com.google.gson.JsonArray;

import java.util.List;

public interface ResponseCallback {
    public void onResponseMovieClips(List<Movie> jsonResponse);

    public void onResponseReviews(JsonArray jsonArray);
}
