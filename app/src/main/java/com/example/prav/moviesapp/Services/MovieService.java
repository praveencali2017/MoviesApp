package com.example.prav.moviesapp.Services;

import com.example.prav.moviesapp.Model.JSONResponse;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    @GET("3/movie/popular")
    Call<JSONResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("3/movie/top_rated")
    Call<JSONResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("3/movie/{movie_id}/videos")
    Call<JSONResponse> getMovieClips(@Path("movie_id") String movie_id, @Query("api_key") String apiKey, @Query("language") String lang);

    @GET("3/movie/{movie_id}/reviews")
    Call<JsonObject> getReviews(@Path("movie_id") String movie_id, @Query("api_key") String apiKey, @Query("language") String lang);
}
