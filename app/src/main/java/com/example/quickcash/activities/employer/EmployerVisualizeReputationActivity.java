package com.example.quickcash.activities.employer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.example.quickcash.interfaces.EmployerLoadedCallback;
import com.example.quickcash.objects.Employer;
import com.example.quickcash.ui_elements.EmployerVisualizeReportAdapter;
import com.example.quickcash.utilities.EmployerManager;
import com.google.firebase.auth.FirebaseAuth;

public class EmployerVisualizeReputationActivity extends AppCompatActivity implements EmployerLoadedCallback {
    private double userRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_job_history);
        setupBackButton();
        getUserRating();
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.userReportBackButton);
        backButton.setOnClickListener(v -> navigateToActivity(EmployerLandingActivity.class));
    }

    private void getUserRating() {
        EmployerManager employerManager = new EmployerManager();
        employerManager.getEmployerByID(FirebaseAuth.getInstance().getUid(), this);
    }

    @Override
    public void onEmployerLoaded(Employer employer) {
        if (employer.hasRating()) {
            this.userRating = employer.getRating();
        } else {
            this.userRating = -1;
        }

        displayReport();
    }

    private void displayReport() {
        RecyclerView recyclerView = findViewById(R.id.userRatingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EmployerVisualizeReportAdapter adapter = new EmployerVisualizeReportAdapter(userRating);
        recyclerView.setAdapter(adapter);
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployerVisualizeReputationActivity.this, destinationClass);
        intent.putExtra("switchedFrom", "employerVisualizeReputation");
        startActivity(intent);
        finish();
    }

    @Override
    public void onEmployerNotFound() {
        Log.e("EmployerVisualizeReputationActivity", "Database error: Employer not found");
    }

    @Override
    public void onDataLoadError(String errorMessage) {
        Log.e("EmployerVisualizeReputationActivity", errorMessage);
    }
}
