package com.example.quickcash;

import static org.junit.Assert.assertEquals;

import com.example.quickcash.objects.JobApplication;

import org.junit.Test;

public class JobApplicationUnitTest {
    private String testEmployerID = "kTtRMbL2DQNw";
    private final String testJobID = "12345";

    @Test
    public void testJobApplicationConstructor() {
        JobApplication jobApplication = new JobApplication(testEmployerID, testJobID);
        assertEquals(testEmployerID, jobApplication.getEmployeeID());
        assertEquals(testJobID, jobApplication.getJobID());
    }

    @Test
    public void testGetEmployeeID() {
        JobApplication jobApplication = new JobApplication(testEmployerID, testJobID);
        assertEquals(testEmployerID, jobApplication.getEmployeeID());
    }

    @Test
    public void testGetJobID() {
        JobApplication jobApplication = new JobApplication(testEmployerID, testJobID);
        assertEquals(testJobID, jobApplication.getJobID());
    }

}
