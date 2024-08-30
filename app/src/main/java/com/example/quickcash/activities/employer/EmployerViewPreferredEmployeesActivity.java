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
import com.example.quickcash.objects.Employee;
import com.example.quickcash.objects.Employer;
import com.example.quickcash.ui_elements.EmployerViewPreferredEmployeesAdapter;
import com.example.quickcash.utilities.EmployeeManager;
import com.example.quickcash.utilities.EmployerManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Activity for displaying an employer's preferred employees.
 */
public class EmployerViewPreferredEmployeesActivity extends AppCompatActivity implements EmployerLoadedCallback, EmployeeListLoadedCallback {
    List<Employee> preferredEmployees;

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
        getPreferredEmployees();
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.employerViewAcceptedApplicantsBackButton);
        backButton.setOnClickListener(v -> navigateToActivity(EmployerLandingActivity.class));
    }

    private void getPreferredEmployees() {
        String employerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        EmployerManager employerManager = new EmployerManager();
        employerManager.getEmployerByID(employerID, this);
    }

    /**
     * Callback method called when employer has been loaded.
     * @param employer  The employer that has been loaded.
     */
    @Override
    public void onEmployerLoaded(Employer employer) {
        List<String> preferredEmployeeIDs = employer.getPreferredEmployeeIDs();
        EmployeeManager employeeManager = new EmployeeManager();
        employeeManager.getListOfEmployees(preferredEmployeeIDs, this);
    }

    /**
     * Callback method called when list of employees has been loaded.
     * @param employees The list of loaded employees.
     */
    @Override
    public void onEmployeeListLoaded(List<Employee> employees) {
        this.preferredEmployees = getDistinctEmployees(employees);
        displayPreferredEmployees();
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

    private void displayPreferredEmployees() {
        RecyclerView recyclerView = null;
        if (preferredEmployees.isEmpty()) {
            Toast.makeText(this, "No preferred applicants found.", Toast.LENGTH_SHORT).show();
        }
        recyclerView = findViewById(R.id.employerViewAcceptedApplicantsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EmployerViewPreferredEmployeesAdapter adapter = new EmployerViewPreferredEmployeesAdapter(this.preferredEmployees);
        recyclerView.setAdapter(adapter);
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployerViewPreferredEmployeesActivity.this, destinationClass);
        intent.putExtra("switchedFrom", "employerViewPreferredEmployees");
        startActivity(intent);
        finish();
    }

    /**
     * Callback method called if employer is missing from the database.
     */
    @Override
    public void onEmployerNotFound() {
        Log.e("EmployerViewPreferredEmployeesActivity", "Database error: Employer not found");
    }

    /**
     * Callback method called if database returns an error.
     * @param errorMessage  The error message describing the issue.
     */
    @Override
    public void onDataLoadError(String errorMessage) {
        Log.e("EmployerViewPreferredEmployeesActivity", errorMessage);
    }

}
