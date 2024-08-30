package com.example.quickcash.objects;

import com.example.quickcash.enums.Urgency;
import com.example.quickcash.interfaces.Builder;

import java.util.ArrayList;
import java.util.Date;

/**
 * Builder class for constructing Job objects.
 */
public class JobBuilder implements Builder<Job> {
    private String title;
    private Date startDate;
    private Date endDate;
    private Urgency urgency;
    private double salary;
    private Coordinates coordinates;
    private String employerID;

    /**
     * Default constructor for JobBuilder
     */
    public JobBuilder() {}

    /**
     * Sets the title of the Job.
     * @param title The title of the job.
     */
    public JobBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the start date of the job.
     * @param startDate A Date object representing the job's start date.
     */
    public JobBuilder setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Sets the end date of the job.
     * @param endDate   A Date object representing the job's end date.
     */
    public JobBuilder setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    /**
     * Sets the urgency of the job.
     * @param urgency   An Urgency enum representing the jobs priority level.
     */
    public JobBuilder setUrgency(Urgency urgency) {
        this.urgency = urgency;
        return this;
    }

    /**
     * Sets the salary of the job.
     * @param salary    The salary offered for the job.
     */
    public JobBuilder setSalary(double salary) {
        this.salary = salary;
        return this;
    }

    /**
     * Sets the coordinates of the job.
     * @param coordinates   A Coordinates object representing the location of the job.
     */
    public JobBuilder setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    /**
     * Sets the employer ID for the user that posted the job.
     * @param employerID    The employers ID.
     */
    public JobBuilder setEmployerID(String employerID) {
        this.employerID = employerID;
        return this;
    }

    /**
     * Resets the builder to its initial state by clearing all set properties.
     */
    public void reset() {
        this.title = null;
        this.startDate = null;
        this.endDate = null;
        this.urgency = null;
        this.salary = 0.0;
        this.coordinates = null;
        this.employerID = null;
    }

    /**
     * Builds a concrete Job object with the configured properties.
     * @return  A fully constructed Job object.
     */
    public Job build() {
        Job job = new Job();

        job.setJobID(null);
        job.setTitle(this.title);
        job.setStartDate(this.startDate);
        job.setEndDate(this.endDate);
        job.setUrgency(this.urgency);
        job.setSalary(this.salary);
        job.setCoordinates(this.coordinates);
        job.setEmployerID(this.employerID);
        job.setJobApplicationIDs(new ArrayList<>());
        job.setOpenToApplications(true);
        job.setAcceptedEmployeeID(null);
        job.setCompleted(false);

        return job;
    }

}
