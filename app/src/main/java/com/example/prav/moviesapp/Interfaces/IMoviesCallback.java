package com.example.prav.moviesapp.Interfaces;

import com.example.prav.moviesapp.Model.Movie;

import java.util.List;

public interface IMoviesCallback {
    public void onLoadMovies(List<Movie> movies);
}
