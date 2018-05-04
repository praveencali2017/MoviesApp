package com.example.prav.moviesapp.Interfaces;

import com.example.prav.moviesapp.Database.Favorite;

import java.util.List;

public interface IDBTask {
    public void onFetch(Favorite favorites);
    public void onFetchAll(List<Favorite> favorites);
}
