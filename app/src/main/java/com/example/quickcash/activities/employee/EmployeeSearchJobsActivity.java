package com.example.quickcash.activities.employee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.example.quickcash.objects.JobSearchParameters;

public class EmployeeSearchJobsActivity extends AppCompatActivity {
    EditText  jobTitleInput;
    Spinner distanceSpinner;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_jobs);

        setupViews();
        setupSubmitButton();
        setupBackButton();
    }

    private void setupViews() {
        jobTitleInput = findViewById(R.id.jobTitleInput);
        distanceSpinner=findViewById(R.id.distanceSpinner);
        ArrayAdapter<CharSequence> distanceAdapter = ArrayAdapter.createFromResource(this,
                R.array.distance_array, android.R.layout.simple_spinner_item);
        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceSpinner.setAdapter(distanceAdapter);
    }

    void setupSubmitButton() {
        submitButton = findViewById(R.id.SubmitBtn);
        submitButton.setOnClickListener(v -> extractSearchParametersAndNavigate());
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.backButoton);
        backButton.setOnClickListener(v -> navigateToEmployeeLandingActivity());
    }

    private void extractSearchParametersAndNavigate() {
        String jobTitleInputValue = jobTitleInput.getText().toString();
        String selectedDistance = distanceSpinner.getSelectedItem().toString();
        double distanceRange=0.0;
        boolean hasParameters = false;

        if(!selectedDistance.isEmpty()){
            distanceRange=Double.parseDouble(selectedDistance);
            hasParameters = true;
        }
        if(!jobTitleInputValue.isEmpty()) {
            hasParameters = true;
        }
        JobSearchParameters jobSearchParameters = hasParameters ? new JobSearchParameters(jobTitleInputValue, distanceRange) : null;
        Log.d("SearchJobs", "Parameters: " + (jobSearchParameters == null ? "null" : jobSearchParameters.toString()));
        navigateToJobPostingsActivity(jobSearchParameters);
    }

    private void navigateToJobPostingsActivity(JobSearchParameters jobSearchParameters) {
        Intent intent = new Intent(EmployeeSearchJobsActivity.this, EmployeeViewJobPostingsActivity.class);
        intent.putExtra("jobSearchParameters", jobSearchParameters);
        startActivity(intent);
    }

    private void navigateToEmployeeLandingActivity() {
        Intent intent = new Intent(EmployeeSearchJobsActivity.this, EmployeeLandingActivity.class);
        startActivity(intent);
    }
}