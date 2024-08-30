package com.example.quickcash.interfaces;

import com.example.quickcash.objects.Employer;

/**
 * Callback interface for handling the loading of an employer.
 */
public interface EmployerLoadedCallback {

    /**
     * Called when the employer has been successfully loaded.
     * @param employer  The employer that has been loaded.
     */
    void onEmployerLoaded(Employer employer);

    /**
     * Called when the employer is not found in the database.
     */
    void onEmployerNotFound();

    /**
     * Called when there is an error during the data loading process.
     * @param errorMessage  The error message describing the issue.
     */
    void onDataLoadError(String errorMessage);
}
