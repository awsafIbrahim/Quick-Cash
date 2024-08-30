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
import com.example.quickcash.interfaces.EmployerLoadedCallback;
import com.example.quickcash.interfaces.LoadAllJobsCallback;
import com.example.quickcash.objects.Employee;
import com.example.quickcash.objects.Employer;
import com.example.quickcash.objects.Job;
import com.example.quickcash.ui_elements.EmployerViewAcceptedApplicantsAdapter;
import com.example.quickcash.utilities.EmployeeManager;
import com.example.quickcash.utilities.EmployerManager;
import com.example.quickcash.utilities.JobManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Activity for displaying an employer's accepted employee for a job.
 */
public class EmployerViewAcceptedApplicantsActivity extends AppCompatActivity implements EmployerLoadedCallback, LoadAllJobsCallback, EmployeeListLoadedCallback{
    private List<Employee> acceptedApplicants;
    private Employer employer;
    List<Job> jobsWithAcceptedApplicants = new ArrayList<>();

    /**
     * Called when activity is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_view_accepted_applicants);
        setupBackButton();
        getApplicants();
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.employerViewAcceptedApplicantsBackButton);
        backButton.setOnClickListener(v -> navigateToActivity(EmployerLandingActivity.class));
    }

    private void getApplicants() {
        String employerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        EmployerManager employerManager = new EmployerManager();
        employerManager.getEmployerByID(employerID, this);
    }

    /**
     * Callback method called when employer object is loaded.
     * @param employer  The employer that has been loaded.
     */
    @Override
    public void onEmployerLoaded(Employer employer) {
        this.employer = employer;
        List<String> postedJobIDs = employer.getPostedJobIDs();
        JobManager jobManager = new JobManager();
        jobManager.getListOfJobs(postedJobIDs, this);
    }

    /**
     * Callback method called when jobs have been loaded.
     * @param jobs  The list of loaded jobs.
     */
    @Override
    public void onJobsLoaded(List<Job> jobs) {
        List<Job> jobsWithAcceptedApplicants = new ArrayList<>();
        for (Job job : jobs) {
            boolean jobClosed = !job.isOpenToApplications();
            if (jobClosed) {
                jobsWithAcceptedApplicants.add(job);
            }
        }
        this.jobsWithAcceptedApplicants = jobsWithAcceptedApplicants;
        List<String> acceptedApplicantIDs = new ArrayList<>();
        for(Job job : jobsWithAcceptedApplicants) {
            acceptedApplicantIDs.add(job.getAcceptedEmployeeID());
        }

        EmployeeManager employeeManager = new EmployeeManager();
        employeeManager.getListOfEmployees(acceptedApplicantIDs, this);
    }

    /**
     * Callback method called when employees have been loaded.
     * @param employees The list of loaded employees.
     */
    @Override
    public void onEmployeeListLoaded(List<Employee> employees) {
        this.acceptedApplicants = getDistinctEmployees(employees);
        displayAcceptedApplicants();
    }

    private List<Employee> getDistinctEmployees(List<Employee> employees) {
        Set<String> uniqueIDs = new HashSet<>();
        List<Employee> uniqueEmployees = new ArrayList<>();

        for (Employee employee : employees) {
            String id = employee.getID();
            if (uniqueIDs.add(id)) {
                uniqueEmployees.add(employee);
            }
        }

        return uniqueEmployees;
    }

    private void displayAcceptedApplicants() {
        RecyclerView recyclerView = null;
        if (acceptedApplicants.isEmpty()) {
            Toast.makeText(this, "No accepted applicants found.", Toast.LENGTH_SHORT).show();
        }
            recyclerView = findViewById(R.id.employerViewAcceptedApplicantsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EmployerViewAcceptedApplicantsAdapter adapter = new EmployerViewAcceptedApplicantsAdapter(this.acceptedApplicants,this.employer,this.jobsWithAcceptedApplicants);
        recyclerView.setAdapter(adapter);
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployerViewAcceptedApplicantsActivity.this, destinationClass);
        intent.putExtra("switchedFrom", "employerViewAcceptedApplicants");
        startActivity(intent);
        finish();
    }

    /**
     * Callback method called if employer is missing from the database.
     */
    @Override
    public void onEmployerNotFound() {
        Log.e("EmployerViewAcceptedApplicantsActivity", "Database error: employer not found");
    }

    /**
     * Callback method called if database returns an error.
     * @param errorMessage  The error message describing the issue.
     */
    @Override
    public void onDataLoadError(String errorMessage) {
        Log.e("EmployerViewAcceptedApplicantsActivity", errorMessage);
    }

}

