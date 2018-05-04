package com.example.prav.moviesapp.Services;

import com.example.prav.moviesapp.Interfaces.IMoviesCallback;
import com.example.prav.moviesapp.Interfaces.ResponseCallback;
import com.example.prav.moviesapp.Model.JSONResponse;
import com.example.prav.moviesapp.Model.Movie;
import com.example.prav.moviesapp.ViewModels.MoviesActivityModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestCaller {
    public static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(ServiceConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static Call<JSONResponse> res;

    public static void loadClips(final String movieKey, final ResponseCallback responseCallback,final boolean loadReviews) {
        final MovieService service = RETROFIT.create(MovieService.class);
        res = service.getMovieClips(movieKey, ServiceConfig.API_KEY, ServiceConfig.LANG);
        res.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                responseCallback.onResponseMovieClips(response.body().results);
                if (loadReviews) {
                    Call<JsonObject> reviews = service.getReviews(movieKey, ServiceConfig.API_KEY, ServiceConfig.LANG);
                    reviews.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject jsonObject = response.body();
                            JsonArray reviews = (JsonArray) jsonObject.get("results");
                            responseCallback.onResponseReviews(reviews);

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                responseCallback.onResponseMovieClips(null);
            }
        });

    }

    public static void loadMovies(String type, final IMoviesCallback iMoviesCallback) {
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
                    iMoviesCallback.onLoadMovies(movies);
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
            }
        });

    }
}
