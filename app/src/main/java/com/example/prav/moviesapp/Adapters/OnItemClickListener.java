package com.example.prav.moviesapp.Adapters;

import com.example.prav.moviesapp.Model.Movie;

public interface OnItemClickListener {
    void onItemClick(Movie movie);

    void onItemClick(String url);
}
