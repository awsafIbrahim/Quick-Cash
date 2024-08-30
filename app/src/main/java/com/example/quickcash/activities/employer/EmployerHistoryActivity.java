package com.example.quickcash.activities.employer;

import static com.example.quickcash.activities.employer.EmployerLandingActivity.SWITCHED_FROM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.quickcash.R;
import com.example.quickcash.activities.employee.EmployeeAppliedJobsActivity;
import com.example.quickcash.activities.employee.EmployeeHistoryActivity;
import com.example.quickcash.activities.employer.EmployerViewAcceptedApplicantsActivity;
public class EmployerHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_history);
        Button jobsPostedBtn = findViewById(R.id.jobsPostedButton);
        jobsPostedBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerHistoryActivity.this, EmployerViewJobPostingsActivity.class);
            startActivity(intent);
        });

        Button acceptedCandidateBtn = findViewById(R.id.acceptedCandidateButton);
        acceptedCandidateBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerHistoryActivity.this, EmployerViewJobPostingsActivity.class);
            startActivity(intent);
        });

        Button preferenceBtn = findViewById(R.id.preferenceButton);
        preferenceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerHistoryActivity.this, EmployerViewPreferredEmployeesActivity.class);
            startActivity(intent);
        });
        setupBackButton();
    }


    private void setupBackButton() {
        Button backButton = findViewById(R.id.employerViewAcceptedApplicantsBackButton);
        backButton.setOnClickListener(v -> navigateToActivity(EmployerLandingActivity.class));
    }
    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployerHistoryActivity.this, destinationClass);
        intent.putExtra(SWITCHED_FROM, "employer");
        startActivity(intent);
        finish();
    }
}