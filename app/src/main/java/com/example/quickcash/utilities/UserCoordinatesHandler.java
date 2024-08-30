package com.example.quickcash.utilities;

import android.util.Log;

import com.example.quickcash.interfaces.EmployeeLoadedCallback;
import com.example.quickcash.interfaces.EmployerLoadedCallback;
import com.example.quickcash.objects.Coordinates;
import com.example.quickcash.objects.Employee;
import com.example.quickcash.objects.Employer;

/**
 * Handles updating user coordinates in the database for users
 */
public class UserCoordinatesHandler implements EmployeeLoadedCallback, EmployerLoadedCallback {
    private EmployeeManager employeeManager;
    private EmployerManager employerManager;
    private Coordinates coordinates;

    /**
     * Constructor for UserCoordinatesHandler.
     */
    public UserCoordinatesHandler() {
        this.employeeManager = new EmployeeManager();
        this.employerManager = new EmployerManager();
    }

    /**
     * Updates the location of the user in the database.
     * @param userID        The ID of the user.
     * @param coordinates   The new Coordinates object.
     */
    public void updateUserLocation(String userID, Coordinates coordinates) {
        this.coordinates = coordinates;
        this.employeeManager.getEmployeeByID(userID, this);
        this.employerManager.getEmployerByID(userID, this);
    }

    /**
     * Callback method invoked when the employee's data is successfully returned.
     * Deals with updating the employee's coordinates.
     * @param employee  The loaded Employee object.
     */
    @Override
    public void onEmployeeLoaded(Employee employee) {
        employee.setCoordinates(this.coordinates);
        this.employeeManager.updateEmployeeInDatabase(employee);
    }

    /**
     * Callback method invoked when the employer's data is successfully returned.
     * Deals with updating the employer's coordinates.
     * @param employer  The loaded Employer object.
     */
    @Override
    public void onEmployerLoaded(Employer employer) {
        employer.setCoordinates(this.coordinates);
        this.employerManager.updateEmployerInDatabase(employer);
    }

    /**
     * Callback method invoked when the employee is not found in the database.
     * Logs error indicating this.
     */
    @Override
    public void onEmployeeNotFound() {
        Log.e("UserCoordinatesHandler", "Database error: Employee not found");
    }

    /**
     * Callback method invoked when the employer is not found in the database.
     * Logs error indicating this.
     */
    @Override
    public void onEmployerNotFound() {
        Log.e("UserCoordinatesHandler", "Database error: Employer not found");
    }

    /**
     * Callback method invoked when there is an error from the database.
     * Logs error indicating this.
     * @param errorMessage  The error message returned from the database.
     */
    @Override
    public void onDataLoadError(String errorMessage) {
        Log.e("UserCoordinatesHandler", errorMessage);
    }

}
