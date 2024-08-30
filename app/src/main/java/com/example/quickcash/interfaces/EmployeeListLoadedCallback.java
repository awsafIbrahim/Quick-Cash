package com.example.quickcash.interfaces;

import com.example.quickcash.objects.Employee;

import java.util.List;

/**
 * Callback interface for handling the loading of a list of employees.
 */
public interface EmployeeListLoadedCallback {

    /**
     * Called when the list of employees has been successfully loaded.
     * @param employees The list of loaded employees.
     */
    void onEmployeeListLoaded(List<Employee> employees);

    /**
     * Called when there is an error during the data loading process.
     * @param errorMessage  The error message describing the issue.
     */
    void onDataLoadError(String errorMessage);
}
