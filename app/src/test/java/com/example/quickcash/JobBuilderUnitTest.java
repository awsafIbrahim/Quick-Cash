package com.example.quickcash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.quickcash.enums.Urgency;
import com.example.quickcash.objects.Coordinates;
import com.example.quickcash.objects.Job;
import com.example.quickcash.objects.JobBuilder;

import org.junit.Test;

import java.util.Date;

public class JobBuilderUnitTest {
    private final double DELTA_FOR_TESTING_DOUBLE_EQUALITY = 0.001;
    private final long MILLISECONDS_IN_24_HOURS = 24 * 60 * 60 * 1000;
    private String testJobTitle = "Java JUnit Test Developer";
    private Date testStartDate = new Date();
    private Date testEndDate = new Date(testStartDate.getTime() + (MILLISECONDS_IN_24_HOURS));
    private Urgency testUrgency = Urgency.MEDIUM;
    private double testSalary = 3.50;
    private final double testLatitude = 44.63758;
    private final double testLongitude = -63.58711;
    private Coordinates testCoordinates = new Coordinates(testLatitude, testLongitude);
    private String testEmployerID = "kTtRMbL2DQNw";
    private final String testJobID = "12345";
    private final String testJobApplicationID1 = "wTAyEKZujBV2";
    private final String testJobApplicationID2 = "vzXVxY2AS3z3";
    private final String testEmployeeID = "us8ckqGKtGCF";

    @Test
    public void testBuild() {
        JobBuilder jobBuilder = new JobBuilder();
        jobBuilder.setTitle(testJobTitle);
        jobBuilder.setStartDate(testStartDate);
        jobBuilder.setEndDate(testEndDate);
        jobBuilder.setUrgency(testUrgency);
        jobBuilder.setSalary(testSalary);
        jobBuilder.setCoordinates(testCoordinates);
        jobBuilder.setEmployerID(testEmployerID);

        Job job = jobBuilder.build();

        assertNull(job.getJobID());
        assertEquals(testJobTitle, job.getTitle());
        assertEquals(testStartDate, job.getStartDate());
        assertEquals(testEndDate, job.getEndDate());
        assertEquals(testUrgency, job.getUrgency());
        assertEquals(testSalary, job.getSalary(), DELTA_FOR_TESTING_DOUBLE_EQUALITY);
        assertEquals(testCoordinates, job.getCoordinates());
        assertEquals(testEmployerID, job.getEmployerID());
        assertNotNull(job.getJobApplicationIDs());
        assertTrue(job.getJobApplicationIDs().isEmpty());
        assertTrue(job.isOpenToApplications());
        assertNull(job.getAcceptedEmployeeID());
        assertFalse(job.isCompleted());
    }

    @Test
    public void testReset() {
        JobBuilder jobBuilder = new JobBuilder();
        jobBuilder.setTitle(testJobTitle);
        jobBuilder.setStartDate(testStartDate);
        jobBuilder.setEndDate(testEndDate);
        jobBuilder.setUrgency(testUrgency);
        jobBuilder.setSalary(testSalary);
        jobBuilder.setCoordinates(testCoordinates);
        jobBuilder.setEmployerID(testEmployerID);
        jobBuilder.reset();

        Job job = jobBuilder.build();

        assertNull(job.getJobID());
        assertNull(job.getTitle());
        assertNull(job.getStartDate());
        assertNull(job.getEndDate());
        assertNull(job.getUrgency());
        assertEquals(0.0, job.getSalary(), DELTA_FOR_TESTING_DOUBLE_EQUALITY);
        assertNull(job.getCoordinates());
        assertNull(job.getEmployerID());
        assertNotNull(job.getJobApplicationIDs());
        assertTrue(job.getJobApplicationIDs().isEmpty());
        assertTrue(job.isOpenToApplications());
        assertNull(job.getAcceptedEmployeeID());
        assertFalse(job.isCompleted());
    }

    @Test
    public void testConstructor() {
        JobBuilder jobBuilder = new JobBuilder();
        assertNotNull(jobBuilder);
    }

    @Test
    public void testSetTitle() {
        JobBuilder jobBuilder = new JobBuilder();
        jobBuilder.setTitle(testJobTitle);

        Job job = jobBuilder.build();
        assertEquals(testJobTitle, job.getTitle());
    }

    @Test
    public void testSetStartDate() {
        JobBuilder jobBuilder = new JobBuilder();
        jobBuilder.setStartDate(testStartDate);

        Job job = jobBuilder.build();
        assertEquals(testStartDate, job.getStartDate());
    }

    @Test
    public void testSetEndDate() {
        JobBuilder jobBuilder = new JobBuilder();
        jobBuilder.setEndDate(testEndDate);

        Job job = jobBuilder.build();
        assertEquals(testEndDate, job.getEndDate());
    }

    @Test
    public void testSetUrgency() {
        JobBuilder jobBuilder = new JobBuilder();
        jobBuilder.setUrgency(testUrgency);

        Job job = jobBuilder.build();
        assertEquals(testUrgency, job.getUrgency());
    }

    @Test
    public void testSetSalary() {
        JobBuilder jobBuilder = new JobBuilder();
        jobBuilder.setSalary(testSalary);

        Job job = jobBuilder.build();
        assertEquals(testSalary, job.getSalary(), DELTA_FOR_TESTING_DOUBLE_EQUALITY);
    }

    @Test
    public void testSetCoordinates() {
        JobBuilder jobBuilder = new JobBuilder();
        jobBuilder.setCoordinates(testCoordinates);

        Job job = jobBuilder.build();
        assertEquals(testCoordinates, job.getCoordinates());
    }

    @Test
    public void testSetEmployerID() {
        JobBuilder jobBuilder = new JobBuilder();
        jobBuilder.setEmployerID(testEmployerID);

        Job job = jobBuilder.build();
        assertEquals(testEmployerID, job.getEmployerID());
    }

}
