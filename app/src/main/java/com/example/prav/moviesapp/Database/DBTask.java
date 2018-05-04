package com.example.prav.moviesapp.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.example.prav.moviesapp.Interfaces.IDBTask;
import com.example.prav.moviesapp.Model.Movie;
import com.google.gson.Gson;

import java.util.List;

public class DBTask {
    @SuppressLint("StaticFieldLeak")
    public void insertFav(Movie movie, final Context context) {
        new AsyncTask<Movie, Void, Void>() {
            @Override
            protected Void doInBackground(Movie... movies) {
                MovieDB db = MovieDB.getAppDatabase(context);
                Favorite favorite = new Favorite();
                favorite.setMovie(new Gson().toJson(movies[0]));
                favorite.setMovieId(movies[0].getMovieId());
                db.favoriteDaoDao().insertFav(favorite);
                return null;
            }
        }.execute(movie);
    }

    @SuppressLint("StaticFieldLeak")
    public void delFav(String movieId, final Context context) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                MovieDB db = MovieDB.getAppDatabase(context);
                db.favoriteDaoDao().deleteFav(strings[0]);
                return null;
            }
        }.execute(movieId);
    }

    @SuppressLint("StaticFieldLeak")
    public void getFav(final Context context, final IDBTask idbTask, final String movieId) {
        new AsyncTask<Void, Void, Favorite>() {
            @Override
            protected Favorite doInBackground(Void... voids) {
                MovieDB db = MovieDB.getAppDatabase(context);
                return db.favoriteDaoDao().getFav(movieId);
            }

            @Override
            protected void onPostExecute(Favorite favorites) {
                super.onPostExecute(favorites);
                idbTask.onFetch(favorites);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void getAllFav(final IDBTask idbTask, final Context context) {
        new AsyncTask<Void, List<Favorite>, List<Favorite>>() {

            @Override
            protected List<Favorite> doInBackground(Void... voids) {
                MovieDB db = MovieDB.getAppDatabase(context);
                return db.favoriteDaoDao().getFav();
            }

            @Override
            protected void onPostExecute(List<Favorite> favorites) {
                super.onPostExecute(favorites);
                idbTask.onFetchAll(favorites);
            }
        }.execute();
    }

}
