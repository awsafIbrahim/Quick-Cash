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
import com.example.quickcash.interfaces.EmployeeLoadedCallback;
import com.example.quickcash.interfaces.LoadAllJobsCallback;
import com.example.quickcash.objects.Employee;
import com.example.quickcash.objects.Job;
import com.example.quickcash.ui_elements.EmployeeViewAcceptedJobsAdapter;
import com.example.quickcash.utilities.EmployeeManager;
import com.example.quickcash.utilities.JobManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Activity for displaying an employee's accepted jobs.
 */
public class EmployeeViewAcceptedJobsActivity extends AppCompatActivity implements EmployeeLoadedCallback, LoadAllJobsCallback {
    List<Job> jobs;
    List<String> acceptedJobIDs;
    Employee employee;

    /**
     * Called when the activity is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_view_accepted_jobs);
        setupBackButton();
        getJobs();
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.employeeViewAcceptedJobsBackButton);
        backButton.setOnClickListener(v -> navigateToActivity(EmployeeLandingActivity.class));
    }

    private void getJobs() {
        String employeeID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        EmployeeManager employeeManager = new EmployeeManager();
        employeeManager.getEmployeeByID(employeeID, this);
    }

    /**
     * Callback method called when employee object is loaded.
     * @param employee  The employee that has been loaded.
     */
    @Override
    public void onEmployeeLoaded(Employee employee) {
        this.employee = employee;
        this.acceptedJobIDs = employee.getAcceptedJobIDs();
        JobManager jobManager = new JobManager();
        jobManager.getListOfJobs(this.acceptedJobIDs, this);
    }

    /**
     * Callback method called when an employee's jobs have been loaded.
     * @param jobs  The list of loaded jobs.
     */
    @Override
    public void onJobsLoaded(List<Job> jobs) {
        this.jobs = jobs;
        displayJobs();
    }

    private void displayJobs() {
        RecyclerView recyclerView = null;
        if (jobs.isEmpty()) {
            Toast.makeText(this, "No accepted jobs found.", Toast.LENGTH_SHORT).show();
        }
            recyclerView = findViewById(R.id.employeeViewAcceptedJobsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EmployeeViewAcceptedJobsAdapter adapter = new EmployeeViewAcceptedJobsAdapter(this.jobs, this.employee, this);
        recyclerView.setAdapter(adapter);
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployeeViewAcceptedJobsActivity.this, destinationClass);
        intent.putExtra("switchedFrom", "employerViewAcceptedJobs");
        startActivity(intent);
        finish();
    }

    /**
     * Callback method if an employee is missing from the database.
     */
    @Override
    public void onEmployeeNotFound() {
        Log.e("EmployeeViewAcceptedJobsActivity", "Database error: Employee not found");
    }

    /**
     * Callback method if the database returns an error.
     * @param errorMessage  The error message describing the issue.
     */
    @Override
    public void onDataLoadError(String errorMessage) {
        Log.e("EmployeeViewAcceptedJobsActivity", errorMessage);
    }

}
