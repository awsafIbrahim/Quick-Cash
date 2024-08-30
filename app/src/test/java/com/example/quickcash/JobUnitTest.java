package com.example.quickcash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.quickcash.enums.Urgency;
import com.example.quickcash.objects.Coordinates;
import com.example.quickcash.objects.Job;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobUnitTest {
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
    public void testJobConstructor() {
        Job job = new Job();
        assertNotNull(job);
    }

    @Test
    public void testGetJobID() {
        Job job = new Job();
        assertNull(job.getJobID());
    }

    @Test
    public void testSetJobID() {
        Job job = new Job();
        job.setJobID(testJobID);
        assertEquals(testJobID, job.getJobID());
    }

    @Test
    public void testGetTitle() {
        Job job = new Job();
        assertNull(job.getTitle());
    }

    @Test
    public void testSetTitle() {
        Job job = new Job();
        job.setTitle(testJobTitle);
        assertEquals(testJobTitle, job.getTitle());
    }

    @Test
    public void testGetStartDate() {
        Job job = new Job();
        assertNull(job.getStartDate());
    }

    @Test
    public void testSetStartDate() {
        Job job = new Job();
        job.setStartDate(testStartDate);
        assertEquals(testStartDate, job.getStartDate());
    }

    @Test
    public void testGetEndDate() {
        Job job = new Job();
        assertNull(job.getEndDate());
    }

    @Test
    public void testSetEndDate() {
        Job job = new Job();
        job.setEndDate(testEndDate);
        assertEquals(testEndDate, job.getEndDate());
    }

    @Test
    public void testGetDurationInMilliseconds() {
        Job job = new Job();
        job.setStartDate(testStartDate);
        job.setEndDate(testEndDate);
        assertEquals(MILLISECONDS_IN_24_HOURS, job.getDurationInMilliseconds());
    }

    @Test
    public void testGetUrgency() {
        Job job = new Job();
        assertNull(job.getUrgency());
    }

    @Test
    public void testSetUrgency() {
        Job job = new Job();
        job.setUrgency(testUrgency);
        assertEquals(testUrgency, job.getUrgency());
    }

    @Test
    public void testGetSalary() {
        Job job = new Job();
        assertEquals(0.0, job.getSalary(), DELTA_FOR_TESTING_DOUBLE_EQUALITY);
    }

    @Test
    public void testSetSalary() {
        Job job = new Job();
        job.setSalary(testSalary);
        assertEquals(testSalary, job.getSalary(), DELTA_FOR_TESTING_DOUBLE_EQUALITY);
    }

    @Test
    public void testGetCoordinates() {
        Job job = new Job();
        assertNull(job.getCoordinates());
    }

    @Test
    public void testSetCoordinates() {
        Job job = new Job();
        job.setCoordinates(testCoordinates);
        assertEquals(testCoordinates, job.getCoordinates());
    }

    @Test
    public void testGetEmployerID() {
        Job job = new Job();
        assertNull(job.getEmployerID());
    }

    @Test
    public void testSetEmployerID() {
        Job job = new Job();
        job.setEmployerID(testEmployerID);
        assertEquals(testEmployerID, job.getEmployerID());
    }

    @Test
    public void testGetJobApplicationIDs() {
        Job job = new Job();
        assertNull(job.getJobApplicationIDs());
    }

    @Test
    public void testSetJobApplicationIDs() {
        Job job = new Job();
        List<String> testJobApplicationIDs = new ArrayList<>();
        testJobApplicationIDs.add(testJobApplicationID1);
        testJobApplicationIDs.add(testJobApplicationID2);
        job.setJobApplicationIDs(testJobApplicationIDs);
        assertFalse(job.getJobApplicationIDs().isEmpty());
        assertEquals(testJobApplicationIDs, job.getJobApplicationIDs());
    }

    @Test
    public void testAddJobApplicationID() {
        Job job = new Job();
        job.setJobApplicationIDs(new ArrayList<>());
        job.addJobApplicationID(testJobApplicationID1);
        job.addJobApplicationID(testJobApplicationID2);

        List<String> jobApplicationIDs = job.getJobApplicationIDs();
        assertEquals(2, jobApplicationIDs.size());
        assertTrue(jobApplicationIDs.contains(testJobApplicationID1));
        assertTrue(jobApplicationIDs.contains(testJobApplicationID2));
    }

    @Test
    public void testIsOpenToApplications() {
        Job job = new Job();
        assertFalse(job.isOpenToApplications());
    }

    @Test
    public void testSetOpenToApplications() {
        Job job = new Job();
        job.setOpenToApplications(true);
        assertTrue(job.isOpenToApplications());
        job.setOpenToApplications(false);
        assertFalse(job.isOpenToApplications());
    }

    @Test
    public void testCloseJob() {
        Job job = new Job();
        job.setOpenToApplications(true);
        assertTrue(job.isOpenToApplications());

        job.closeJob();
        assertFalse(job.isOpenToApplications());
    }

    @Test
    public void testGetAcceptedEmployeeID() {
        Job job = new Job();
        assertNull(job.getAcceptedEmployeeID());
    }

    @Test
    public void testSetAcceptedEmployeeID() {
        Job job = new Job();
        assertNull(job.getAcceptedEmployeeID());

        job.setAcceptedEmployeeID(testEmployeeID);
        assertEquals(testEmployeeID, job.getAcceptedEmployeeID());
    }

    @Test
    public void testIsCompleted() {
        Job job = new Job();
        assertFalse(job.isCompleted());
    }

    @Test
    public void testSetCompleted() {
        Job job = new Job();
        job.setCompleted(true);
        assertTrue(job.isCompleted());
        job.setCompleted(false);
        assertFalse(job.isCompleted());
    }

    @Test
    public void testMarkJobCompleted() {
        Job job = new Job();
        assertFalse(job.isCompleted());

        job.markJobCompleted();
        assertTrue(job.isCompleted());
    }

}
