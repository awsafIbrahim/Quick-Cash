package com.example.quickcash.utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quickcash.interfaces.JobLoadedByIDCallback;
import com.example.quickcash.interfaces.JobsLoadedByEmployerIDCallback;
import com.example.quickcash.interfaces.LoadAllJobsCallback;
import com.example.quickcash.objects.Employee;
import com.example.quickcash.objects.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manages operations related to job data in the Firebase Realtime Database.
 */
public class JobManager {
    private static final String JOBS_REFERENCE = "jobs";
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    /**
     * Constructor for JobManager.
     * Initializes the Firebase realtime database.
     */
    public JobManager() {
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference();
    }

    /**
     * Posts a job to the database.
     * Generates a unique job ID, sets it for the job, and writes the job to the database.
     * @param job   The Job object to be posted.
     * @return      The unique ID assigned to the posted job.
     */
    public String postJob(Job job) {
        String jobID = this.databaseReference.child(JOBS_REFERENCE).push().getKey();
        job.setJobID(jobID);
        databaseReference.child(JOBS_REFERENCE).child(jobID).setValue(job);

        return jobID;
    }

    /**
     * Adds a job application ID to the list of applications for a specific job.
     * @param jobID The ID of the job.
     * @param jobApplicationID The ID of the job application to be added.
     */
    public void addJobApplicationID(String jobID, String jobApplicationID) {
        DatabaseReference jobReference = getJobReference(jobID);

        jobReference.child("jobApplicationIDs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> currentJobApplicationIDs = new ArrayList<>();
                if(snapshot.exists()) {
                    for(DataSnapshot snapShotChild : snapshot.getChildren()) {
                        currentJobApplicationIDs.add(snapShotChild.getValue(String.class));
                    }
                }
                currentJobApplicationIDs.add(jobApplicationID);
                jobReference.child("jobApplicationIDs").setValue(currentJobApplicationIDs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Job Manager", error.getMessage());
            }
        });
    }

    /**
     * Marks the job as closed in the database
     * @param jobID The ID of the job to be closed.
     */
    public void closeJob(String jobID) {
        DatabaseReference jobReference = getJobReference(jobID);
        jobReference.child("openToApplications").setValue(false);
    }

    /**
     * Updates the job in the database to accept an employee.
     * @param jobID         The ID of the job
     * @param employeeID    The ID of the accepted employee.
     */
    public void acceptEmployee(String jobID, String employeeID) {
        DatabaseReference jobReference = getJobReference(jobID);
        jobReference.child("acceptedEmployeeID").setValue(employeeID);

        EmployeeManager employeeManager = new EmployeeManager();
        employeeManager.addAcceptedJobID(employeeID, jobID);
    }

    /**
     * Retrieves a job from the database by its ID.
     * @param jobID     The ID of the job to retrieve.
     * @param callback  The callback to handle the asynchronously returned response.
     */
    public void getJobByID(String jobID, final JobLoadedByIDCallback callback) {
        DatabaseReference jobReference = getJobReference(jobID);

        jobReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Job job = snapshot.getValue(Job.class);
                    callback.onJobLoaded(job);
                } else {
                    callback.onJobNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onDataLoadError(error.getMessage());
            }
        });
    }

    /**
     * Retrieves a list of jobs from the database based on a list of job IDs.
     * @param jobIDs    The list of job IDs to retrieve.
     * @param callback  The callback to handle the asynchronously returned response.
     */
    public void getListOfJobs(List<String> jobIDs, final LoadAllJobsCallback callback) {
        Query query = databaseReference.child(JOBS_REFERENCE);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Job> jobs = new ArrayList<>();

                for(DataSnapshot snapShotChild : snapshot.getChildren()) {
                    Job job = snapShotChild.getValue(Job.class);
                    if (jobIDs.contains(job.getJobID())) {
                        jobs.add(job);
                    }
                }
                callback.onJobsLoaded(jobs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onDataLoadError(error.getMessage());
            }
        });
    }

    /**
     * Retrieves all jobs from the database.
     * @param callback  The callback to handle the asynchronously returned response.
     */
    public void getAllJobs(final LoadAllJobsCallback callback) {
        Query query = databaseReference.child(JOBS_REFERENCE);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Job> jobs = new ArrayList<>();

                for(DataSnapshot snapShotChild : snapshot.getChildren()) {
                    Job job = snapShotChild.getValue(Job.class);
                    jobs.add(job);
                }
                callback.onJobsLoaded(jobs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onDataLoadError(error.getMessage());
            }
        });
    }
    public void markJobAsCompleted(String jobID) {
        DatabaseReference jobReference = getJobReference(jobID);
        jobReference.child("completed").setValue(true);
        jobReference.child("paymentDate").setValue(new Date());

    }

    public void searchJobsFromEmployer(String employerID, final JobsLoadedByEmployerIDCallback callback) {
        Query query = databaseReference.child(JOBS_REFERENCE).orderByChild("employerID").equalTo(employerID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Job> jobs = new ArrayList<>();

                for (DataSnapshot snapShotChild : snapshot.getChildren()) {
                    Job job = snapShotChild.getValue(Job.class);
                    jobs.add(job);
                }
                callback.onJobsLoaded(jobs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onDataLoadError(error.getMessage());
            }
        });
    }


    private DatabaseReference getJobReference(String jobID) {
        return this.databaseReference.child(JOBS_REFERENCE).child(jobID);
    }

}
