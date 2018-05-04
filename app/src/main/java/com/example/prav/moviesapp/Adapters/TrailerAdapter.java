package com.example.prav.moviesapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prav.moviesapp.Model.Movie;
import com.example.prav.moviesapp.R;
import com.example.prav.moviesapp.Services.ServiceConfig;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerVH> {

    private List<String> trailerUrls;
    private OnItemClickListener onItemClickListener;

    public TrailerAdapter(List<String> trailerUrls, OnItemClickListener onItemClickListener) {
        this.trailerUrls = trailerUrls;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TrailerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.trailer_cell, parent, false);
        return new TrailerVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerVH holder, int position) {
        holder.trailerNumber.setText("Trailer " + (position + 1));
        holder.bind(trailerUrls.get(position), this.onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return trailerUrls.size();
    }

    public class TrailerVH extends RecyclerView.ViewHolder {
        private TextView trailerNumber;

        public TrailerVH(View itemView) {
            super(itemView);
            trailerNumber = itemView.findViewById(R.id.trailer_number);
        }

        public void bind(final String url, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(url);
                }
            });
        }
    }
}
