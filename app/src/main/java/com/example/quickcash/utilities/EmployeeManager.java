package com.example.quickcash.utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quickcash.interfaces.EmployeeListLoadedCallback;
import com.example.quickcash.interfaces.EmployeeLoadedCallback;
import com.example.quickcash.objects.Employee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manages operations related to employee data in the Firebase realtime database.
 */
public class EmployeeManager {
    private static final String EMPLOYEES_REFERENCE = "employees";
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    /**
     * Constructor for EmployeeManager.
     * Initializes the Firebase realtime database.
     */
    public EmployeeManager() {
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference();
    }

    /**
     * Adds an employee to the database.
     * @param employee  The Employee object to add.
     * @return          The ID of the employee object.
     */
    public String addEmployeeToDatabase(Employee employee) {
        String employeeID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(EMPLOYEES_REFERENCE).child(employeeID).setValue(employee);

        return employeeID;
    }

    /**
     * Updates an employee in the database.
     * @param employee  The Employee object to update.
     * @return          The ID of the employee object.
     */
    public String updateEmployeeInDatabase(Employee employee) {
        return addEmployeeToDatabase(employee);
    }

    /**
     * Adds a job application ID to an employee's representation in the database.
     * @param employeeID        The ID of the employee.
     * @param jobApplicationID  The ID of the job application to be added.
     */
    public void addJobApplicationID(String employeeID, String jobApplicationID) {
        DatabaseReference employeeReference = getEmployeeReference(employeeID);

        employeeReference.child("jobApplicationIDs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> currentJobApplicationIDs = new ArrayList<>();
                if(snapshot.exists()) {
                    for(DataSnapshot snapShotChild : snapshot.getChildren()) {
                        currentJobApplicationIDs.add(snapShotChild.getValue(String.class));
                    }
                }
                currentJobApplicationIDs.add(jobApplicationID);
                employeeReference.child("jobApplicationIDs").setValue(currentJobApplicationIDs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("EmployeeManager", error.getMessage());
            }
        });
    }

    /**
     * Adds a job ID to the list of jobs accepted by an employee to the database.
     * @param employeeID    The ID of the employee.
     * @param jobID         The ID of the accepted job.
     */
    public void addAcceptedJobID(String employeeID, String jobID) {
        DatabaseReference employeeReference = getEmployeeReference(employeeID);

        employeeReference.child("acceptedJobIDs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> currentPostedJobIDs = new ArrayList<>();
                if(snapshot.exists()) {
                    for(DataSnapshot snapShotChild : snapshot.getChildren()) {
                        currentPostedJobIDs.add(snapShotChild.getValue(String.class));
                    }
                }
                currentPostedJobIDs.add(jobID);
                employeeReference.child("acceptedJobIDs").setValue(currentPostedJobIDs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("EmployeeManager", error.getMessage());
            }
        });
    }

    /**
     * Retrieves the employee object from the database given their ID.
     * @param employeeID    The ID of the employee to retrieve.
     * @param callback      The callback to handle the asynchronously returned response.
     */
    public void getEmployeeByID(String employeeID, final EmployeeLoadedCallback callback) {
        DatabaseReference employeeReference = getEmployeeReference(employeeID);

        employeeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Employee employee = snapshot.getValue(Employee.class);
                    callback.onEmployeeLoaded(employee);
                } else {
                    callback.onEmployeeNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onDataLoadError(error.getMessage());
            }
        });
    }

    /**
     * Retrieves a list of employee objects from the database given a list of employee IDs.
     * @param employeeIDs   The list of employee IDs to retrieve.
     * @param callback      The callback to handle the asynchronously returned response.
     */
    public void getListOfEmployees(List<String> employeeIDs, final EmployeeListLoadedCallback callback) {
        Query query = databaseReference.child(EMPLOYEES_REFERENCE);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Employee> employees = new ArrayList<>();

                for(DataSnapshot snapShotChild : snapshot.getChildren()) {
                    Employee employee = snapShotChild.getValue(Employee.class);
                    if (employeeIDs.contains(employee.getID())) {
                        employees.add(employee);
                    }
                }
                callback.onEmployeeListLoaded(employees);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onDataLoadError(error.getMessage());
            }
        });
    }


    private DatabaseReference getEmployeeReference(String employeeID) {
        return this.databaseReference.child(EMPLOYEES_REFERENCE).child(employeeID);
    }

}
