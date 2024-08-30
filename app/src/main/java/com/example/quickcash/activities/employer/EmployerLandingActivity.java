package com.example.quickcash.activities.employer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.example.quickcash.activities.employee.EmployeeLandingActivity;
import com.example.quickcash.activities.general.LoginPageActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Activity for displaying the main landing page for employers.
 */
public class EmployerLandingActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_employer_landing);
        validateUser();
        setupSnackBarNotification();
        setupToggleButton();
        setupLogoutButton();
        setupAddJobPostingButton();
        setupViewJobPostingsButton();
        setupViewPreferredEmployeesButton();
        setupViewAcceptedApplicationsButton();
        setupReportButton();

        Button jobHistBtn = findViewById(R.id.employerJobHistory);
        jobHistBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerLandingActivity.this, EmployerHistoryActivity.class);
            startActivity(intent);
        });
    }

    private void validateUser() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null){
            navigateToActivity(LoginPageActivity.class);
        }
    }

    private void setupSnackBarNotification() {
        if(getIntent().hasExtra(SWITCHED_FROM)) {
            String switchedFrom = getIntent().getStringExtra(SWITCHED_FROM);
            String message = "";
            if("employee".equals(switchedFrom)) {
                message = "Switched to Employer";
            }
            Snackbar.make(findViewById(android.R.id.content), message, BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }

    private void setupToggleButton() {
        Button employeeToggleEmploymentView = findViewById(R.id.employerToggleEmploymentView);
        employeeToggleEmploymentView.setOnClickListener(v -> navigateToActivity(EmployeeLandingActivity.class));
    }

    private void setupLogoutButton() {
        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            navigateToActivity(LoginPageActivity.class);
        });
    }

    private void setupAddJobPostingButton() {
        Button addJobPostingButton = findViewById(R.id.btnEmployerAddJobPosting);
        addJobPostingButton.setOnClickListener(v -> navigateToActivity(EmployerAddJobPostingActivity.class));
    }

    private void setupViewJobPostingsButton() {
        Button viewJobPostingsButton = findViewById(R.id.btnEmployerViewJobPostings);
        viewJobPostingsButton.setOnClickListener(v -> navigateToActivity(EmployerViewJobPostingsActivity.class));
    }

    private void setupViewPreferredEmployeesButton() {
        Button viewPreferredEmployeesButton = findViewById(R.id.btnEmployerViewPreferredEmployees);
        viewPreferredEmployeesButton.setOnClickListener(v -> navigateToActivity(EmployerViewPreferredEmployeesActivity.class));
    }

    private void setupViewAcceptedApplicationsButton() {
        Button viewAcceptedApplicationsButton = findViewById(R.id.btnEmployerAcceptedApplications);
        viewAcceptedApplicationsButton.setOnClickListener(v -> navigateToActivity(EmployerViewAcceptedApplicantsActivity.class));
    }

    private void setupReportButton() {
        Button reportButton = findViewById(R.id.btnEmployerReport);
        reportButton.setOnClickListener(v -> navigateToActivity(EmployerVisualizeReputationActivity.class));
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployerLandingActivity.this, destinationClass);
        intent.putExtra(SWITCHED_FROM, "employer");
        startActivity(intent);
        finish();
    }

}
