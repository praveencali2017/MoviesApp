package com.example.prav.moviesapp.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prav.moviesapp.Activities.MovieDetailActivity;
import com.example.prav.moviesapp.Model.Movie;
import com.example.prav.moviesapp.R;
import com.example.prav.moviesapp.Services.ServiceConfig;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private List<Movie> movies;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_cell, parent, false);
        return new MovieHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie = movies.get(position);
        Picasso.with(holder.itemView.getContext())
                .load(ServiceConfig.IMG_BASEURL + movie.getMovieImgUrl())
                .into(holder.movieImg);
        holder.movieName.setText(movie.getMovieName());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
            Gson gson = new Gson();
            intent.putExtra("movie_selected", gson.toJson(movies.get(position)));
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView movieImg;
        private TextView movieName;

        private MovieHolder(View itemView) {
            super(itemView);
            this.movieImg = itemView.findViewById(R.id.movie_img);
            this.movieName = itemView.findViewById(R.id.movie_txt);
        }

    }
}
