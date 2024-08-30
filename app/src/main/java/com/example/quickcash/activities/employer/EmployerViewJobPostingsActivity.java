package com.example.quickcash.activities.employer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.example.quickcash.interfaces.JobsLoadedByEmployerIDCallback;
import com.example.quickcash.interfaces.OnViewApplicantsClickListener;
import com.example.quickcash.objects.Job;
import com.example.quickcash.ui_elements.EmployerViewJobAdapter;
import com.example.quickcash.utilities.JobManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Activity for displaying an employer's posted jobs.
 */
public class EmployerViewJobPostingsActivity extends AppCompatActivity implements JobsLoadedByEmployerIDCallback, OnViewApplicantsClickListener {
    List<Job> jobs;

    /**
     * Called when activity is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_view_job_postings);
        setupBackButton();
        getEmployersJobs();
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.employerViewJobsBackButton);
        backButton.setOnClickListener(v -> navigateToActivity((EmployerLandingActivity.class)));
    }

    private void getEmployersJobs() {
        JobManager jobManager = new JobManager();
        jobManager.searchJobsFromEmployer(FirebaseAuth.getInstance().getCurrentUser().getUid(), this);
    }

    /**
     * Callback method called when jobs have been loaded.
     * @param jobs  The list of loaded jobs.
     */
    @Override
    public void onJobsLoaded(List<Job> jobs) {
        this.jobs = jobs;
        filterJobs();
        displayJobs();
    }

    private void filterJobs() {
        //TODO: apply filters for story 9/job.isOpenToApplications()
    }

    private void displayJobs() {
        RecyclerView recyclerView = null;
        if (jobs.isEmpty()) {
            Toast.makeText(this, "No jobs found.", Toast.LENGTH_SHORT).show();
        }
            recyclerView = findViewById(R.id.employerViewJobsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EmployerViewJobAdapter adapter = new EmployerViewJobAdapter(jobs, this, this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Callback method called when user clicks view applicants in the recycler view for a job.
     * Redirects to a view showing the applicants for that job.
     * @param jobID     The ID of the job.
     * @param isJobOpen A boolean representing if the job is open to applications.
     */
    @Override
    public void onViewApplicantsClicked(String jobID, boolean isJobOpen) {
        Intent intent = new Intent(EmployerViewJobPostingsActivity.this, EmployerViewApplicantsActivity.class);
        intent.putExtra("jobID", jobID);
        intent.putExtra("isJobOpen", isJobOpen);
        startActivity(intent);
        finish();
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployerViewJobPostingsActivity.this, destinationClass);
        intent.putExtra("switchedFrom", "employerViewJobs");
        startActivity(intent);
        finish();
    }

    /**
     * Callback method called if database returns an error.
     * @param errorMessage  The error message describing the issue.
     */
    @Override
    public void onDataLoadError(String errorMessage) {
        Log.e("EmployerViewJobPostingsActivity", errorMessage);
    }

}
