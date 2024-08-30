package com.example.quickcash.interfaces;

import com.example.quickcash.objects.JobApplication;

import java.util.List;

/**
 * Callback interface for handling the loading of a list of all job applications.
 */
public interface LoadAllJobApplicationsCallback {

    /**
     * Called when the list of job applications has been successfully loaded.
     * @param jobApplications   The list of loaded job applications.
     */
    void onJobApplicationsLoaded(List<JobApplication> jobApplications);

    /**
     * Called when there is an error during the data loading process.
     * @param errorMessage  The error message describing the issue.
     */
    void onDataLoadError(String errorMessage);
}
