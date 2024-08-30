package com.example.quickcash.ui_elements;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;

/**
 * ViewHolder for displaying applied jobs.
 */
public class EmployeeViewAppliedJobsViewHolder extends RecyclerView.ViewHolder {
    TextView titleTextView;
    LinearLayout detailsLayout;
    TextView detailsTextView;
    Button showDetailsButton;

    /**
     * Constructor for the ViewHolder.
     * @param jobView   The View representing a job item.
     */
    public EmployeeViewAppliedJobsViewHolder(View jobView) {
        super(jobView);
        this.titleTextView = jobView.findViewById(R.id.jobTitleTextView);
        this.detailsLayout = jobView.findViewById(R.id.detailsLayout);
        this.detailsTextView = jobView.findViewById(R.id.detailsTextView);
        this.showDetailsButton = jobView.findViewById(R.id.showDetailsButton);

        setupShowDetailsButton();
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

}
