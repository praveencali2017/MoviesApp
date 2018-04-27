package com.example.prav.moviesapp.Services;

import com.example.prav.moviesapp.Model.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {
    @GET("3/movie/popular")
    Call<JSONResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("3/movie/top_rated")
    Call<JSONResponse> getTopRatedMovies(@Query("api_key") String apiKey);
}
