package com.example.quickcash.activities.employee;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.quickcash.R;
import com.example.quickcash.activities.employer.EmployerViewAcceptedApplicantsActivity;
public class EmployeeHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_history);
        Button appliedJobsBtn = findViewById(R.id.appliedJobsButton);
        appliedJobsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EmployeeHistoryActivity.this, EmployeeAppliedJobsActivity.class);
            startActivity(intent);
        });

        Button acceptedJobBtn = findViewById(R.id.acceptedJobsButton);
        acceptedJobBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EmployeeHistoryActivity.this, EmployeeViewAcceptedJobsActivity.class);
            startActivity(intent);
        });

        setupBackButton();

    }
    private void setupBackButton() {
        Button backButton = findViewById(R.id.employeeAppliedJobsBackButton);
        backButton.setOnClickListener(v -> navigateToActivity(EmployeeLandingActivity.class));
    }
    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployeeHistoryActivity.this, destinationClass);
        intent.putExtra("switchedFrom", "employerAppliedJob");
        startActivity(intent);
        finish();
    }

}