package com.example.quickcash.interfaces;

import com.example.quickcash.objects.Employee;

/**
 * Callback interface for handling the loading of an employee.
 */
public interface EmployeeLoadedCallback {

    /**
     * Called when the employee has been successfully loaded.
     * @param employee  The employee that has been loaded.
     */
    void onEmployeeLoaded(Employee employee);

    /**
     * Called when the employee is not found in the database.
     */
    void onEmployeeNotFound();

    /**
     * Called when there is an error during the data loading process.
     * @param errorMessage  The error message describing the issue.
     */
    void onDataLoadError(String errorMessage);
}
