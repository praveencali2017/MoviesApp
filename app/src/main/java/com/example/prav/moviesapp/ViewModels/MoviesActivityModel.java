package com.example.prav.moviesapp.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.prav.moviesapp.Model.JSONResponse;
import com.example.prav.moviesapp.Model.Movie;
import com.example.prav.moviesapp.Services.MovieService;
import com.example.prav.moviesapp.Services.ServiceConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * View Model for the Activity MovieActivity
 */
public class MoviesActivityModel extends ViewModel {
    public MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    public Call<JSONResponse> res;

    /**
     * Loads the movies based on selected sorting preferences
     * @param type sorting preferences
     */
    public void loadMovies(String type) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieService service = retrofit.create(MovieService.class);
        if (type.equals("pop")) {
            res = service.getPopularMovies(ServiceConfig.API_KEY);
        } else {
            res = service.getTopRatedMovies(ServiceConfig.API_KEY);
        }
        res.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                List<Movie> movies = response.body().results;
                if (movies != null && movies.size() > 0) {
                    MoviesActivityModel.this.movies.setValue(movies);
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                String res = t.getMessage();
                String ff = "";
            }
        });
    }
}
