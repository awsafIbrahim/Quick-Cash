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
import com.example.quickcash.interfaces.EmployeeListLoadedCallback;
import com.example.quickcash.interfaces.LoadAllJobApplicationsCallback;
import com.example.quickcash.objects.Employee;
import com.example.quickcash.objects.JobApplication;
import com.example.quickcash.ui_elements.EmployerViewApplicantsAdapter;
import com.example.quickcash.utilities.EmployeeManager;
import com.example.quickcash.utilities.JobApplicationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for displaying an employer's applicants for a posted job.
 */
public class EmployerViewApplicantsActivity extends AppCompatActivity implements LoadAllJobApplicationsCallback, EmployeeListLoadedCallback {
    private String jobID;
    private boolean isJobOpen;
    private List<Employee> employees;


    /**
     * Called when activity is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_view_applicants);

        this.jobID = getIntent().getStringExtra("jobID");
        this.isJobOpen = getIntent().getBooleanExtra("isJobOpen", false);
        setupBackButton();
        getApplicants();
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.employerViewApplicantsBackButton);
        backButton.setOnClickListener(v -> navigateToActivity(EmployerLandingActivity.class));
    }

    private void getApplicants() {
        JobApplicationManager jobApplicationManager = new JobApplicationManager();
        jobApplicationManager.getAllJobApplications(this);
    }

    /**
     * Callback method called when job applications have been loaded.
     * @param jobApplications   The list of loaded job applications.
     */
    @Override
    public void onJobApplicationsLoaded(List<JobApplication> jobApplications) {
        List<String> employeeIDs = new ArrayList<>();
        for (JobApplication jobApplication : jobApplications) {
            if (jobApplication.getJobID().equals(this.jobID)) {
                employeeIDs.add(jobApplication.getEmployeeID());
            }
        }

        EmployeeManager employeeManager = new EmployeeManager();
        employeeManager.getListOfEmployees(employeeIDs, this);
    }

    /**
     * Callback method called when employees have been loaded.
     * @param employees The list of loaded employees.
     */
    @Override
    public void onEmployeeListLoaded(List<Employee> employees) {
        this.employees = employees;
        displayEmployees();
    }

    private void displayEmployees() {
        RecyclerView recyclerView = null;
        if (employees.isEmpty()) {
            Toast.makeText(this, "No applicants found.", Toast.LENGTH_SHORT).show();
        }
            recyclerView = findViewById(R.id.employerViewApplicantsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EmployerViewApplicantsAdapter adapter = new EmployerViewApplicantsAdapter(this.employees, this.jobID, this.isJobOpen);
        recyclerView.setAdapter(adapter);
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployerViewApplicantsActivity.this, destinationClass);
        intent.putExtra("switchedFrom", "employerViewApplicants");
        startActivity(intent);
        finish();
    }

    /**
     * Callback method called if database returns an error.
     * @param errorMessage  The error message describing the issue.
     */
    @Override
    public void onDataLoadError(String errorMessage) {
        Log.e("EmployerViewApplicantsActivity", errorMessage);
    }

}
