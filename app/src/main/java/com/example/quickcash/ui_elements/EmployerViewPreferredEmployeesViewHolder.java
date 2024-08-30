package com.example.quickcash.ui_elements;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.example.quickcash.objects.Employee;
import com.example.quickcash.utilities.EmployeeManager;

/**
 * ViewHolder for displaying employees.
 */
public class EmployerViewPreferredEmployeesViewHolder extends RecyclerView.ViewHolder {
    TextView usernameTextView;
    LinearLayout userRatingWrapper;
    RatingBar ratingBar;
    Button submitRatingButton;

    /**
     * Constructor for the ViewHolder.
     * @param userView   The View representing a user item.
     */
    public EmployerViewPreferredEmployeesViewHolder(@NonNull View userView) {
        super(userView);
        this.usernameTextView = userView.findViewById(R.id.usernameTextView);
        this.userRatingWrapper = userView.findViewById(R.id.userRatingWrapper);
        this.ratingBar = userView.findViewById(R.id.userRatingBar);
        this.submitRatingButton = userView.findViewById(R.id.submitRatingButton);
    }

    public void setupRatingsSubmit(Employee employee) {
        this.userRatingWrapper.setVisibility(View.VISIBLE);
        this.submitRatingButton.setOnClickListener(v -> {
            float rating = this.ratingBar.getRating();
            employee.addRating(rating);
            EmployeeManager employeeManager = new EmployeeManager();
            employeeManager.updateEmployeeInDatabase(employee);
            Toast.makeText(v.getContext(), "Rating has been submitted", Toast.LENGTH_SHORT).show();
            this.userRatingWrapper.setVisibility(View.GONE);
        });
    }
}
