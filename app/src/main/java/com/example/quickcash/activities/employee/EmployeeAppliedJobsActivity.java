package com.example.quickcash.activities.employee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.example.quickcash.interfaces.LoadAllJobApplicationsCallback;
import com.example.quickcash.interfaces.LoadAllJobsCallback;
import com.example.quickcash.objects.Job;
import com.example.quickcash.objects.JobApplication;
import com.example.quickcash.ui_elements.EmployeeViewAppliedJobsAdapter;
import com.example.quickcash.utilities.JobApplicationManager;
import com.example.quickcash.utilities.JobManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for displaying an employee's applied jobs.
 */
public class EmployeeAppliedJobsActivity extends AppCompatActivity implements LoadAllJobApplicationsCallback, LoadAllJobsCallback {
    List<JobApplication> jobApplications;
    List<Job> appliedJobs;

    /**
     * Called when the activity is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_applied_jobs);
        setupBackButton();
        getAppliedJobs();
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.employeeAppliedJobsBackButton);
        backButton.setOnClickListener(v -> navigateToActivity(EmployeeLandingActivity.class));
    }

    private void getAppliedJobs() {
        JobApplicationManager jobApplicationManager = new JobApplicationManager();
        jobApplicationManager.getAllJobApplications(this);
    }

    /**
     * Callback method called when job applications are loaded.
     * @param jobApplications   The list of loaded job applications.
     */
    @Override
    public void onJobApplicationsLoaded(List<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
        JobManager jobManager = new JobManager();
        jobManager.getAllJobs(this);
    }

    /**
     * Callback method called when jobs are loaded.
     * @param jobs  The list of loaded jobs.
     */
    @Override
    public void onJobsLoaded(List<Job> jobs) {
        filterAppliedJobs(jobs);
        displayAppliedJobs();
    }

    private void filterAppliedJobs(List<Job> jobs) {
        this.appliedJobs = new ArrayList<>();
        List<String> appliedJobIDs = filterApplications();
        for (Job job : jobs) {
            if (appliedJobIDs.contains(job.getJobID())) {
                this.appliedJobs.add(job);
            }
        }
    }

    private List<String> filterApplications() {
        String employeeID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        List<String> appliedJobIDs = new ArrayList<>();
        for (JobApplication jobApplication : this.jobApplications) {
            if (jobApplication.getEmployeeID().equals(employeeID)) {
                appliedJobIDs.add(jobApplication.getJobID());
            }
        }
        return appliedJobIDs;
    }

    private void displayAppliedJobs() {
        RecyclerView recyclerView = null;
        if (appliedJobs.isEmpty()) {
            Toast.makeText(this, "No applied jobs found.", Toast.LENGTH_SHORT).show();
        }
        recyclerView = findViewById(R.id.employeeAppliedJobsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EmployeeViewAppliedJobsAdapter adapter = new EmployeeViewAppliedJobsAdapter(appliedJobs, this);
        recyclerView.setAdapter(adapter);
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployeeAppliedJobsActivity.this, destinationClass);
        intent.putExtra("switchedFrom", "employerAppliedJob");
        startActivity(intent);
        finish();
    }

    /**
     * Callback method called if the database returns an error.
     * @param errorMessage  The error message describing the issue.
     */
    @Override
    public void onDataLoadError(String errorMessage) {
        Log.e("EmployeeAppliedJobsActivity", errorMessage);
    }

}
