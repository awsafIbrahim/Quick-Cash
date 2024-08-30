package com.example.quickcash.ui_elements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;

public class EmployerVisualizeReportAdapter extends RecyclerView.Adapter<UserReportRatingViewHolder> {
    double rating;

    public EmployerVisualizeReportAdapter(double rating) {
        this.rating = rating;
    }

    @NonNull
    @Override
    public UserReportRatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_item_layout, parent, false);
        return new UserReportRatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserReportRatingViewHolder holder, int position) {
        holder.setUserRating(rating);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
