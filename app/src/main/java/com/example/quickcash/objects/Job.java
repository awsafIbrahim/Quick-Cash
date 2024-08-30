package com.example.quickcash.objects;

import android.location.Geocoder;

import com.example.quickcash.enums.Urgency;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Represents a job.
 */
public class Job {
    private String jobID;
    private String title;
    private Date startDate;
    private Date endDate;
    private Urgency urgency;
    private double salary;
    private Coordinates coordinates;
    private String employerID;
    private List<String> jobApplicationIDs;
    private boolean openToApplications;
    private String acceptedEmployeeID;
    private Date paymentDate;
    private boolean completed;

    /**
     * Default constructor only used for reflection by Firebase Database.
     */
    public Job() {}

    /**
     * Sets the job ID.
     * @param jobID An ID to define the job.
     */
    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    /**
     * Gets the ID of the job.
     * @return The job ID.
     */
    public String getJobID() {
        return this.jobID;
    }

    /**
     * Sets the job title.
     * @param title The job title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the title of the job.
     * @return  The job title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the job's start date.
     * @param startDate A Date object representing the job's start date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the start date of the job.
     * @return  A Date object representing the job's start date.
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Sets the end date of the job.
     * @param endDate A Date object representing the job's end date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the end date of the job.
     * @return  A Date object representing the job's end date.
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Gets the duration of the job in milliseconds.
     * @return  The duration of the job in milliseconds.
     */
    public long getDurationInMilliseconds() {
        return endDate.getTime() - startDate.getTime();
    }

    /**
     * Sets the urgency level of the job.
     * @param urgency   An Urgency enum representing the priority of the job.
     */
    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    /**
     * Gets the urgency level of the job.
     * @return  An Urgency enum representing the priority of the job.
     */
    public Urgency getUrgency() {
        return this.urgency;
    }

    /**
     * Sets the salary offered for the job.
     * @param salary    The salary offered for the job.
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the salary offered for the job.
     * @return  The salary offered for the job.
     */
    public double getSalary() {
        return this.salary;
    }

    /**
     * Sets the coordinates associated with the job.
     * @param coordinates   A Coordinates object representing the location of the job.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Gets the coordinates associated with the job.
     * @return  A Coordinates object representing the location of the job.
     */
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    /**
     * Sets the ID of the employer who posted the job.
     * @param employerID    The employer's ID.
     */
    public void setEmployerID(String employerID) {
        this.employerID = employerID;
    }

    /**
     * Gets the ID of the employer who posted the job.
     * @return  The employer's ID.
     */
    public String getEmployerID() {
        return this.employerID;
    }

    /**
     * Sets the list of job application IDs associated with the job.
     * @param jobApplicationIDs A list of job application IDs.
     */
    public void setJobApplicationIDs(List<String> jobApplicationIDs) {
        this.jobApplicationIDs = jobApplicationIDs;
    }

    /**
     * Gets a list of job application IDs associated with the job.
     * @return  A list of job application IDs.
     */
    public List<String> getJobApplicationIDs() {
        return this.jobApplicationIDs;
    }

    /**
     * Adds a job application ID to the list of applications for the job.
     * @param jobApplicationID  The ID of the job application to be added.
     */
    public void addJobApplicationID(String jobApplicationID) {
        this.jobApplicationIDs.add(jobApplicationID);
    }

    /**
     * Sets the status of whether the job is open to applications.
     * @param isOpen    true to set the job open to applications, false otherwise.
     */
    public void setOpenToApplications(boolean isOpen) {
        this.openToApplications = isOpen;
    }

    /**
     * Checks if the job is currently open to new applications.
     * @return  true if the job is open to applications, false otherwise.
     */
    public boolean isOpenToApplications() {
        return this.openToApplications;
    }

    /**
     * Marks the job as closed to new applications.
     */
    public void closeJob() {
        this.openToApplications = false;
    }

    /**
     * Gets the ID of the employee accepted for the job.
     * @return  The ID of the accepted employee, or null if not yet accepted.
     */
    public String getAcceptedEmployeeID() {
        return this.acceptedEmployeeID;
    }

    /**
     * Sets the ID of the employee accepted for the job.
     * @param employeeID    The ID of the accepted employee.
     */
    public void setAcceptedEmployeeID(String employeeID) {
        this.acceptedEmployeeID = employeeID;
    }

    /**
     * Sets the status of if the job has been completed.
     * @param status    The desired completion status of the job.
     */
    public void setCompleted(boolean status) {
        this.completed = status;
    }

    /**
     * Checks if the job has been marked as completed.
     * @return  true if the job is marked as completed, false otherwise.
     */
    public boolean isCompleted() {
        return this.completed;
    }

    /**
     * Marks the job as completed.
     */
    public void markJobCompleted() {
        this.completed = true;
    }

    /**
     * Gets the details of the job as a formatted string.
     * @param geocoder  A geocoder object from the calling context.
     * @return          The job details as a string.
     */
    public String toFormattedString(Geocoder geocoder) {
        return String.format(Locale.getDefault(),
                "Start Date: %s%nEnd Date: %s%nSalary: $%.2f%nUrgency: %s%nLocation: %s",
                this.startDate.toString(), this.endDate.toString(), this.salary,
                this.urgency.toString(), this.coordinates.toFormattedString(geocoder));
    }
    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

}
