package com.example.quickcash.activities.employee;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.example.quickcash.interfaces.CoordinatesCallback;
import com.example.quickcash.interfaces.EmployeeLoadedCallback;
import com.example.quickcash.interfaces.LoadAllJobsCallback;
import com.example.quickcash.objects.Coordinates;
import com.example.quickcash.objects.Employee;
import com.example.quickcash.objects.Job;
import com.example.quickcash.objects.JobSearchParameters;
import com.example.quickcash.objects.User;
import com.example.quickcash.ui_elements.EmployeeViewJobAdapter;
import com.example.quickcash.objects.JobSearchParameters;
import com.example.quickcash.ui_elements.EmployeeViewJobAdapter;
import com.example.quickcash.utilities.EmployeeManager;
import com.example.quickcash.utilities.JobManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Activity for displaying jobs to an employee.
 */
public class EmployeeViewJobPostingsActivity extends AppCompatActivity implements LoadAllJobsCallback {
    List<Job> jobs;


    private JobSearchParameters receivedParameters;
    /**
     * Called when the activity is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_view_jobs);
        setupBackButton();
        getJobs();

        receivedParameters = (JobSearchParameters) getIntent().getSerializableExtra("jobSearchParameters");
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.employeeViewJobsBackButton);
        backButton.setOnClickListener(v -> navigateToActivity(EmployeeLandingActivity.class));
    }

    private void getJobs() {
        JobManager jobManager = new JobManager();
        jobManager.getAllJobs(this);
    }

    private void displayJobs(List<Job> jobs) {
        RecyclerView recyclerView = findViewById(R.id.employeeViewJobsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EmployeeViewJobAdapter adapter = new EmployeeViewJobAdapter(jobs,this);
        recyclerView.setAdapter(adapter);
    }
    private void displayJobs() {
        RecyclerView recyclerView = findViewById(R.id.employeeViewJobsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EmployeeViewJobAdapter adapter = new EmployeeViewJobAdapter(jobs,this);
        recyclerView.setAdapter(adapter);
    }
    private double calculateDistance(double x1,double y1, double x2, double y2){
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)*111);
        return distance;
    }


    private void applyParameter(JobSearchParameters jobSearchParameters, List<Job> jobs) {

        getEmployeeCoordinates(new CoordinatesCallback() {
            @Override
            public void onCoordinatesRetrieved(Coordinates userCoordinates) {
                List<Job> filteredJobs = new ArrayList<>();

                for (Job job : jobs) {
                    Coordinates jobCoords = job.getCoordinates();
                    if (jobCoords != null) {
                        double distanceToJob = calculateDistance(userCoordinates.getLatitude(), userCoordinates.getLongitude(),
                                jobCoords.getLatitude(), jobCoords.getLongitude());


                        if (job.getTitle().equalsIgnoreCase(jobSearchParameters.getJobTitle()) && distanceToJob <= jobSearchParameters.getDistanceRange()) {
                            filteredJobs.add(job);
                        }
                        else if(receivedParameters.getJobTitle().isEmpty() && distanceToJob <= jobSearchParameters.getDistanceRange()){
                            filteredJobs.add(job);
                        }
                    }
                }

                displayJobs(filteredJobs);
            }
        });

    }

    public void getEmployeeCoordinates(final CoordinatesCallback callback){
        String employeeID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        EmployeeManager employeeManager = new EmployeeManager();
        employeeManager.getEmployeeByID(employeeID, new EmployeeLoadedCallback() {
            @Override
            public void onEmployeeLoaded(Employee employee) {
                if (employee != null && employee.getCoordinates() != null) {
                    callback.onCoordinatesRetrieved(employee.getCoordinates());
                }
            }

            @Override
            public void onEmployeeNotFound() {

            }

            @Override
            public void onDataLoadError(String errorMessage) {

            }
        });
    }

    @Override
    public void onJobsLoaded(List<Job> jobs) {
        this.jobs = jobs;
        if (receivedParameters != null) {
            applyParameter(receivedParameters, jobs);

        }
        else  {
            displayJobs();
        }
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployeeViewJobPostingsActivity.this, destinationClass);
        intent.putExtra("switchedFrom", "employerViewJobs");
        startActivity(intent);
        finish();
    }

    /**
     * Callback method called if the database returns an error.
     * @param errorMessage  The error message describing the issue.
     */
    @Override
    public void onDataLoadError(String errorMessage) {
        Log.e("EmployeeViewJobPostingsActivity", errorMessage);
    }
}
