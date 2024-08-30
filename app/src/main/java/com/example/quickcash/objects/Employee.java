package com.example.quickcash.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an employee in the app.
 */
public class Employee extends User {
    private List<String> jobApplicationIDs;
    private List<String> acceptedJobIDs;
    private List<String> preferredEmployerIDs;


    /**
     * Default constructor only used for reflection by Firebase Database.
     */
    public Employee() {}

    /**
     * Parameterized constructor for Employee.
     * @param id    Unique ID assigned by Firebase Authenticator.
     * @param name  Name chosen by user on sign-up.
     * @param email Email of the user, used to sign up.
     */
    public Employee(String id, String name, String email) {
        super(id, name, email);
        this.jobApplicationIDs = new ArrayList<>();
        this.acceptedJobIDs = new ArrayList<>();
        this.preferredEmployerIDs = new ArrayList<>();

    }

    /**
     * Gets the list of job application IDs belonging to the user.
     * @return  The list of job application IDs.
     */
    public List<String> getJobApplicationIDs() {
        return this.jobApplicationIDs;
    }

    /**
     * Add a job application ID for the user.
     * @param jobApplicationID  The job ID to be added.
     */
    public void addJobApplicationID(String jobApplicationID) {
        this.jobApplicationIDs.add(jobApplicationID);
    }

    /**
     * Gets the list of job IDs that the user has been accepted for.
     * @return  The list of accepted job IDs.
     */
    public List<String> getAcceptedJobIDs() {
        return this.acceptedJobIDs;
    }

    /**
     * Add a job ID to the list of accepted job IDs.
     * @param acceptedJobID The job ID to be added.
     */
    public void addAcceptedJobID(String acceptedJobID) {
        this.acceptedJobIDs.add(acceptedJobID);
    }

    /**
     * Gets the list of employer IDs that the employee has set to preferred employer.
     * @return  The list of preferred employer IDs
     */
    public List<String> getPreferredEmployerIDs() {
        if (this.preferredEmployerIDs == null) {
            this.preferredEmployerIDs = new ArrayList<>();
        }
        return this.preferredEmployerIDs;
    }

    /**
     * Add an employer's ID to the list of preferred employer IDs.
     * @param preferredEmployerID   The ID of the employer to add.
     */
    public void addPreferredEmployerID(String preferredEmployerID) {
        if (this.preferredEmployerIDs == null) {
            this.preferredEmployerIDs = new ArrayList<>();
        }
        this.preferredEmployerIDs.add(preferredEmployerID);
    }



}
