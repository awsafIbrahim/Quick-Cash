package com.example.quickcash.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employer in the app.
 */
public class Employer extends User {
    private List<String> postedJobIDs;
    private List<String> preferredEmployeeIDs;

    /**
     * Default constructor only used for reflection by Firebase Database.
     */
    public Employer() {}

    /**
     * Parameterized constructor for Employer.
     * @param id    Unique ID assigned by Firebase Authenticator.
     * @param name  Name chosen by user on sign-up.
     * @param email Email of the user, used to sign up.
     */
    public Employer(String id, String name, String email) {
        super(id, name, email);
        this.postedJobIDs = new ArrayList<>();
        this.preferredEmployeeIDs = new ArrayList<>();
    }

    /**
     * Gets the list of posted job IDs belonging to the user.
     * @return  The list of job IDs.
     */
    public List<String> getPostedJobIDs() {
        return this.postedJobIDs;
    }

    /**
     * Add a job ID to the list of posted job IDs.
     * @param jobID The job ID to be added.
     */
    public void addJobID(String jobID) {
        this.postedJobIDs.add(jobID);
    }

    /**
     * Gets the list of preferred employee IDs that the employer has set to preffered employee.
     * @return  The list of preferred employee IDs
     */
    public List<String> getPreferredEmployeeIDs() {
        if (this.preferredEmployeeIDs == null) {
            this.preferredEmployeeIDs = new ArrayList<>();
        }
        return this.preferredEmployeeIDs;
    }

    /**
     * Add an employee's ID to the list of preferred employer IDs.
     * @param preferredEmployeeID   The ID of the employee to add.
     */
    public void addPreferredEmployeeID(String preferredEmployeeID) {
        if (this.preferredEmployeeIDs == null) {
            this.preferredEmployeeIDs = new ArrayList<>();
        }
        this.preferredEmployeeIDs.add(preferredEmployeeID);
    }

}
