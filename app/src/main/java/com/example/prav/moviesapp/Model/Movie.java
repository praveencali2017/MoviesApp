package com.example.prav.moviesapp.Model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("title")
    private String movieName;
    @SerializedName("poster_path")
    private String movieImgUrl;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("overview")
    private String synopsis;
    @SerializedName("vote_average")
    private String userRatings;
    @SerializedName("release_date")
    private String releaseDate;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieImgUrl() {
        return movieImgUrl;
    }

    public void setMovieImgUrl(String movieImgUrl) {
        this.movieImgUrl = movieImgUrl;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(String userRatings) {
        this.userRatings = userRatings;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


}
