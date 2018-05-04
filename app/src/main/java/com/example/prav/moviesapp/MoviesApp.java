package com.example.prav.moviesapp;

import android.app.Application;

import com.example.prav.moviesapp.Database.MovieDB;

public class MoviesApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MovieDB.getAppDatabase(this);
    }
}
