package com.example.prav.moviesapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.prav.moviesapp.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private JsonArray reviews;

    public ReviewAdapter(JsonArray jsonArray) {
        this.reviews = jsonArray;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_cell, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        JsonObject review = reviews.get(position).getAsJsonObject();
        holder.authorTxt.setText(review.get("author").toString());
        holder.review.setText(review.get("content").toString());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        private TextView authorTxt;
        private TextView review;

        public ReviewHolder(View itemView) {
            super(itemView);
            authorTxt = itemView.findViewById(R.id.author_txt);
            review = itemView.findViewById(R.id.author_review_txt);
        }
    }
}
