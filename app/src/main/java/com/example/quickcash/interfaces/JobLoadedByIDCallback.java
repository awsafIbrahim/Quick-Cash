package com.example.quickcash.interfaces;

import com.example.quickcash.objects.Job;

/**
 * Callback interface for handling the loading of a job via its ID.
 */
public interface JobLoadedByIDCallback {

    /**
     * Called when the job has been successfully loaded.
     * @param job   The loaded job.
     */
    void onJobLoaded(Job job);

    /**
     * Called when the job is not found in the database.
     */
    void onJobNotFound();

    /**
     * Called when there is an error during the data loading process.
     * @param errorMessage  The error message describing the issue.
     */
    void onDataLoadError(String errorMessage);

}