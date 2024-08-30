package com.example.quickcash.activities.employee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.example.quickcash.interfaces.EmployeeLoadedCallback;
import com.example.quickcash.interfaces.LoadAllJobsCallback;
import com.example.quickcash.objects.Employee;
import com.example.quickcash.objects.Job;
import com.example.quickcash.ui_elements.EmployeeVisualizeJobHistoryAdapter;
import com.example.quickcash.utilities.EmployeeManager;
import com.example.quickcash.utilities.JobManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeVisualizeJobHistoryActivity extends AppCompatActivity implements EmployeeLoadedCallback, LoadAllJobsCallback {
    double userRating;
    List<Double> paymentAmounts;
    List<Date> paymentDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_job_history);
        setupBackButton();
        getUserReportData();
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.userReportBackButton);
        backButton.setOnClickListener(v -> navigateToActivity(EmployeeLandingActivity.class));
    }

    private void getUserReportData() {
        EmployeeManager employeeManager = new EmployeeManager();
        employeeManager.getEmployeeByID(FirebaseAuth.getInstance().getUid(), this);
    }

    @Override
    public void onEmployeeLoaded(Employee employee) {
        if (employee.hasRating()) {
            this.userRating = employee.getRating();
        } else {
            this.userRating = -1;
        }

        List<String> employeeAcceptedJobIDs = employee.getAcceptedJobIDs();
        JobManager jobManager = new JobManager();
        jobManager.getListOfJobs(employeeAcceptedJobIDs, this);
    }

    @Override
    public void onJobsLoaded(List<Job> jobs) {
        this.paymentAmounts = new ArrayList<>();
        this.paymentDates = new ArrayList<>();
        for (Job job : jobs) {
            if (job.isCompleted()) {
                this.paymentAmounts.add(job.getSalary());
                this.paymentDates.add(job.getPaymentDate());
            }
        }

        sortPaymentDataByDate(this.paymentAmounts, this.paymentDates);
        displayReport();
    }

    private void sortPaymentDataByDate(List<Double> paymentAmounts, List<Date> paymentDates) {
        Map<Date, Double> paymentMap = new HashMap<>(paymentAmounts.size());
        for (int i = 0; i < paymentDates.size(); i++) {
            paymentMap.put(paymentDates.get(i), paymentAmounts.get(i));
        }

        Collections.sort(paymentDates);

        for (int i = 0; i < paymentAmounts.size(); i++) {
            paymentAmounts.set(i, paymentMap.get(paymentDates.get(i)));
        }
    }

    private void displayReport() {
        RecyclerView recyclerView = findViewById(R.id.userRatingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EmployeeVisualizeJobHistoryAdapter adapter = new EmployeeVisualizeJobHistoryAdapter(userRating, paymentAmounts, paymentDates);
        recyclerView.setAdapter(adapter);
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployeeVisualizeJobHistoryActivity.this, destinationClass);
        intent.putExtra("switchedFrom", "employeeVisualizeJobHistory");
        startActivity(intent);
        finish();
    }

    @Override
    public void onEmployeeNotFound() {
        Log.e("EmployeeVisualizeJobHistory", "Database error: Employer not found");
    }

    @Override
    public void onDataLoadError(String errorMessage) {
        Log.e("EmployeeVisualizeJobHistory", errorMessage);
    }
}
