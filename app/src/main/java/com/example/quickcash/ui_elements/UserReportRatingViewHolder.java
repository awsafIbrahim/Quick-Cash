package com.example.quickcash.ui_elements;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;

import java.util.Locale;

public class UserReportRatingViewHolder extends RecyclerView.ViewHolder {
    private RatingBar ratingBar;
    private TextView ratingTextView;

    public UserReportRatingViewHolder(@NonNull View itemView) {
        super(itemView);
        ratingBar = itemView.findViewById(R.id.userRatingBar);
        ratingTextView = itemView.findViewById(R.id.userRatingTextView);
    }

    public void setUserRating(double rating) {
        if (rating >= 0) {
            ratingBar.setRating((float) rating);
            ratingTextView.setText(String.format(Locale.getDefault(), "Your Rating: %.2f / 5", (float) rating));
        } else {
            ratingTextView.setText("You have no rating");
        }
    }

}
