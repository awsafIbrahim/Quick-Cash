package com.example.quickcash.ui_elements;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quickcash.R;
import com.example.quickcash.objects.Employee;
import com.example.quickcash.utilities.EmployeeManager;
import com.example.quickcash.activities.employer.EmployerPaymentActivity;
import com.example.quickcash.objects.Employee;
import com.example.quickcash.objects.Job;
import com.example.quickcash.utilities.JobManager;

/**
 * ViewHolder for displaying employees.
 */
public class EmployerViewApplicantsViewHolder extends RecyclerView.ViewHolder {
    TextView usernameTextView;
    Button acceptButton;
    Button preferEmployeeButton;
    Button payButton;
    LinearLayout userRatingWrapper;
    RatingBar ratingBar;
    Button submitRatingButton;

    /**
     * Constructor for the ViewHolder.
     * @param userView   The View representing a user item.
     */
    public EmployerViewApplicantsViewHolder(@NonNull View userView) {
        super(userView);
        this.usernameTextView = userView.findViewById(R.id.usernameTextView);
        this.acceptButton = userView.findViewById(R.id.acceptButton);
        this.preferEmployeeButton = userView.findViewById(R.id.preferEmployeeButton);
        this.payButton = userView.findViewById(R.id.payButton);
        this.userRatingWrapper = userView.findViewById(R.id.userRatingWrapper);
        this.ratingBar = userView.findViewById(R.id.userRatingBar);
        this.submitRatingButton = userView.findViewById(R.id.submitRatingButton);
    }

    public void payEmployee(Job job, Context context) {
        this.payButton.setVisibility(View.VISIBLE);
        this.payButton.setOnClickListener(v -> {
                Intent intent = new Intent(context, EmployerPaymentActivity.class);
                intent.putExtra("jobSalary", String.valueOf(job.getSalary()));

                JobManager jobManager = new JobManager();
                jobManager.markJobAsCompleted(job.getJobID());

                context.startActivity(intent);
                Toast.makeText(context, "Navigating to payment", Toast.LENGTH_SHORT).show();

        });
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
