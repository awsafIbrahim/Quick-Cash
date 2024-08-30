package com.example.quickcash.ui_elements;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.example.quickcash.interfaces.EmployerLoadedCallback;
import com.example.quickcash.objects.Employer;
import com.example.quickcash.objects.Job;
import com.example.quickcash.utilities.EmployerManager;

/**
 * ViewHolder for displaying jobs.
 */
public class EmployeeViewJobViewHolder extends RecyclerView.ViewHolder {
    TextView titleTextView;
    LinearLayout detailsLayout;
    TextView detailsTextView;
    Button applyButton;
    Button showDetailsButton;
    Button preferEmployerButton;
    LinearLayout userRatingWrapperForEmployer;
    Button submitRatingButtonForEmployer;
    RatingBar ratingBarForEmployer;

    /**
     * Constructor for the ViewHolder.
     * @param jobView   The View representing a job item.
     */
    public EmployeeViewJobViewHolder(View jobView) {
        super(jobView);
        this.titleTextView = jobView.findViewById(R.id.jobTitleTextView);
        this.detailsLayout = jobView.findViewById(R.id.detailsLayout);
        this.detailsTextView = jobView.findViewById(R.id.detailsTextView);
        this.applyButton = jobView.findViewById(R.id.applyButton);
        this.showDetailsButton = jobView.findViewById(R.id.showDetailsButton);
        this.preferEmployerButton = jobView.findViewById(R.id.preferEmployerButton);
        setupShowDetailsButton();
        this.userRatingWrapperForEmployer = jobView.findViewById(R.id.userRatingWrapperForEmployer);
        this.ratingBarForEmployer = jobView.findViewById(R.id.userRatingBarForEmployer);
        this.submitRatingButtonForEmployer = jobView.findViewById(R.id.submitRatingButtonForEmployer);
    }

    private void setupShowDetailsButton() {
        this.showDetailsButton.setOnClickListener(view -> {
            if (detailsLayout.getVisibility() == View.VISIBLE) {
                detailsLayout.setVisibility(View.GONE);
            } else {
                detailsLayout.setVisibility(View.VISIBLE);
            }
        });
    }
     void setupRatingSubmitForEmployer(Job job) {
        this.userRatingWrapperForEmployer.setVisibility(View.VISIBLE);
        this.submitRatingButtonForEmployer.setOnClickListener(v -> {
            float rating = this.ratingBarForEmployer.getRating();
            String employerID = job.getEmployerID();

            EmployerManager employerManager = new EmployerManager();
            employerManager.getEmployerByID(employerID, new EmployerLoadedCallback() {
                @Override
                public void onEmployerLoaded(Employer employer) {
                    employer.addRating(rating);
                    employerManager.updateEmployerInDatabase(employer);
                    Toast.makeText(v.getContext(), "Rating has been submitted", Toast.LENGTH_SHORT).show();
                    userRatingWrapperForEmployer.setVisibility(View.GONE);
                }
                @Override
                public void onEmployerNotFound() {
                    Toast.makeText(v.getContext(), "Employer not found", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onDataLoadError(String errorMessage) {
                    Toast.makeText(v.getContext(), "Error loading data: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
