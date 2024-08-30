package com.example.quickcash.interfaces;

/**
 * Callback interface for handling the view applicants button being clicked.
 */
public interface OnViewApplicantsClickListener {

    /**
     * Called when the view applicants button has been clicked.
     * @param jobID     The ID of the job.
     * @param isJobOpen A boolean representing if the job is open to applications.
     */
    void onViewApplicantsClicked(String jobID, boolean isJobOpen);
}
