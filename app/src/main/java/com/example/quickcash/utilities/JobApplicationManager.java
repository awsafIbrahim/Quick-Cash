package com.example.quickcash.utilities;

import androidx.annotation.NonNull;

import com.example.quickcash.interfaces.JobApplicationLoadedCallback;
import com.example.quickcash.interfaces.LoadAllJobApplicationsCallback;
import com.example.quickcash.objects.JobApplication;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages operations related to job applications in the Firebase Realtime Database.
 */
public class JobApplicationManager {
    private static final String JOB_APPLICATION_REFERENCE = "jobApplications";
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    /**
     * Constructor for JobApplicationManager.
     * Initializes the Firebase realtime database.
     */
    public JobApplicationManager() {
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference();
    }

    /**
     * Handles creating and adding a job application within the database.
     * Creates a JobApplication object, generates a unique ID, sets it, and writes to the database.
     * Also updates the application IDs for both the employee and the job.
     * @param employeeID    The ID of the employee submitting the application.
     * @param jobID         The ID of the job to apply to.
     * @return              The generated ID of the job application.
     */
    public String submitJobApplication(String employeeID, String jobID) {
        JobApplication jobApplication = new JobApplication(employeeID, jobID);
        String jobApplicationID = this.databaseReference.child(JOB_APPLICATION_REFERENCE).push().getKey();
        databaseReference.child(JOB_APPLICATION_REFERENCE).child(jobApplicationID).setValue(jobApplication);

        addApplicationIDtoEmployee(employeeID, jobApplicationID);
        addApplicationIDtoJob(jobID, jobApplicationID);

        return jobApplicationID;
    }

    private void addApplicationIDtoEmployee(String employeeID, String jobApplicationID) {
        EmployeeManager employeeManager = new EmployeeManager();
        employeeManager.addJobApplicationID(employeeID, jobApplicationID);
    }

    private void addApplicationIDtoJob(String jobID, String jobApplicationID) {
        JobManager jobManager = new JobManager();
        jobManager.addJobApplicationID(jobID, jobApplicationID);
    }

    /**
     * Retrieves the job application from the database given their ID.
     * @param jobApplicationID  The ID of the job application to retrieve.
     * @param callback          The callback to handle the asynchronously retrieved result.
     */
    public void getJobApplication(String jobApplicationID, final JobApplicationLoadedCallback callback) {
        DatabaseReference jobApplicationIDReference = getEmployeeReference(jobApplicationID);

        jobApplicationIDReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    JobApplication jobApplication = snapshot.getValue(JobApplication.class);
                    callback.onJobApplicationLoaded(jobApplication);
                } else {
                    callback.onJobApplicationNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onDataLoadError(error.getMessage());
            }
        });
    }

    /**
     * Retrieves all job applications from the database.
     * @param callback  The callback to handle the asynchronously retrieved result.
     */
    public void getAllJobApplications(final LoadAllJobApplicationsCallback callback) {
        Query query = databaseReference.child(JOB_APPLICATION_REFERENCE);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<JobApplication> jobApplications = new ArrayList<>();

                for(DataSnapshot snapShotChild : snapshot.getChildren()) {
                    JobApplication jobApplication = snapShotChild.getValue(JobApplication.class);
                    jobApplications.add(jobApplication);
                }
                callback.onJobApplicationsLoaded(jobApplications);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onDataLoadError(error.getMessage());
            }
        });
    }

    private DatabaseReference getEmployeeReference(String jobID) {
        return this.databaseReference.child(JOB_APPLICATION_REFERENCE).child(jobID);
    }

}
