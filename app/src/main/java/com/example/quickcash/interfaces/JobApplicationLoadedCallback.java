package com.example.quickcash.interfaces;

import com.example.quickcash.objects.JobApplication;

/**
 * Callback interface for handling the loading of a job application.
 */
public interface JobApplicationLoadedCallback {

    /**
     * Called when the job application has been successfully loaded.
     * @param jobApplication   The loaded job application.
     */
    void onJobApplicationLoaded(JobApplication jobApplication);

    /**
     * Called when the job application is not found in the database.
     */
    void onJobApplicationNotFound();

    /**
     * Called when there is an error during the data loading process.
     * @param errorMessage  The error message describing the issue.
     */
    void onDataLoadError(String errorMessage);
}
