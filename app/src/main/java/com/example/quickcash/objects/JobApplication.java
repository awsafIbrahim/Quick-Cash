package com.example.quickcash.objects;

/**
 * Represents a job application.
 */
public class JobApplication {
    private String employeeID;
    private String jobID;

    /**
     * Default constructor only used for reflection by Firebase Database.
     */
    public JobApplication() {}

    /**
     * Parameterized constructor for Job Application.
     * @param employeeID    The applying employee's ID.
     * @param jobID         The ID of the job being applied for.
     */
    public JobApplication(String employeeID, String jobID) {
        this.employeeID = employeeID;
        this.jobID = jobID;
    }

    /**
     * Gets the ID of the employee whom the job application belongs to.
     * @return  The employee ID.
     */
    public String getEmployeeID() {
        return this.employeeID;
    }

    /**
     * Gets the ID of the job which the application is directed to.
     * @return  The Job ID.
     */
    public String getJobID() {
        return this.jobID;
    }

}
