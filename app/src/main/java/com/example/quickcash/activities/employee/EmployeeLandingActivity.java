package com.example.quickcash.activities.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.example.quickcash.activities.employer.EmployerLandingActivity;
import com.example.quickcash.activities.general.LoginPageActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Activity for displaying the main landing page for employees.
 */
public class EmployeeLandingActivity extends AppCompatActivity {
    public static final String SWITCHED_FROM = "switchedFrom";
    FirebaseAuth auth;
    FirebaseUser user;

    /**
     * Called when the activity is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_landing);
        validateUser();
        setupSnackBarNotification();
        setupToggleButton();
        setupLogoutButton();
        setupAvailableJobsButton();
        setupAppliedJobsButton();
        setupAcceptedJobsButton();
        setupReportButton();

        Button jobHistoryBtn = findViewById(R.id.employeeJobHistory);
        jobHistoryBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EmployeeLandingActivity.this, EmployeeHistoryActivity.class);
            startActivity(intent);
        });


    }

    private void validateUser() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null) {
            navigateToActivity(LoginPageActivity.class);
        }
    }

    private void setupSnackBarNotification() {
        if(getIntent().hasExtra(SWITCHED_FROM)) {
            String switchedFrom = getIntent().getStringExtra(SWITCHED_FROM);
            String message = "";
            if("employer".equals(switchedFrom)) {
                message = "Switched to Employee";
            }
            Snackbar.make(findViewById(android.R.id.content), message, BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }

    private void setupToggleButton() {
        Button employeeToggleEmploymentView = findViewById(R.id.employeeToggleEmploymentView);
        employeeToggleEmploymentView.setOnClickListener(v -> navigateToActivity(EmployerLandingActivity.class));
    }

    private void setupLogoutButton() {
        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            navigateToActivity(LoginPageActivity.class);
        });
    }

    private void setupAvailableJobsButton() {
        Button availableJobsButton = findViewById(R.id.btnEmployeeAvailableJobs);

        availableJobsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity(EmployeeSearchJobsActivity.class);
            }
        });
    }

    private void setupAppliedJobsButton() {
        Button appliedJobsButton = findViewById(R.id. btnEmployeeAppliedJobs);
        appliedJobsButton.setOnClickListener(v -> navigateToActivity(EmployeeAppliedJobsActivity.class));
    }

    private void setupAcceptedJobsButton() {
        Button acceptedJobsButton = findViewById(R.id.btnEmployeeAcceptedJobs);
        acceptedJobsButton.setOnClickListener(v -> navigateToActivity(EmployeeViewAcceptedJobsActivity.class));
    }

    private void setupReportButton() {
        Button reportButton = findViewById(R.id.btnEmployeeReport);
        reportButton.setOnClickListener(v -> navigateToActivity(EmployeeVisualizeJobHistoryActivity.class));
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployeeLandingActivity.this, destinationClass);
        intent.putExtra(SWITCHED_FROM, "employee");
        startActivity(intent);
        finish();
    }

}
