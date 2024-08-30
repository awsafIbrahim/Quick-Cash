package com.example.quickcash.interfaces;

import com.example.quickcash.objects.Job;

import java.util.List;

/**
 * Callback interface for handling the loading of a list of jobs belonging to an employer.
 */
public interface JobsLoadedByEmployerIDCallback {

    /**
     * Called when the list of jobs has been successfully loaded.
     * @param jobs  The list of loaded jobs.
     */
    void onJobsLoaded(List<Job> jobs);

    /**
     * Called when there is an error during the data loading process.
     * @param errorMessage  The error message describing the issue.
     */
    void onDataLoadError(String errorMessage);

}