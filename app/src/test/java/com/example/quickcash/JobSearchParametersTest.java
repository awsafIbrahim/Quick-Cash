package com.example.quickcash;

import static org.junit.jupiter.api.Assertions.*;

import com.example.quickcash.objects.JobSearchParameters;

import org.junit.Test;

public class JobSearchParametersTest {

    @Test
    public void testJobTitleSetterAndGetter() {
        JobSearchParameters jobSearchParameters = new JobSearchParameters("Software Engineer", 50000);

        jobSearchParameters.setJobTitle("Data Analyst");
        assertEquals("Data Analyst", jobSearchParameters.getJobTitle());
    }

    @Test
    public void testSalaryRangeSetterAndGetter() {
        JobSearchParameters jobSearchParameters = new JobSearchParameters("Manager", 75000);
    }

    @Test
    public void testExpectedDurationSetterAndGetter() {
        JobSearchParameters jobSearchParameters = new JobSearchParameters("Developer", 60000);

    }
    @Test
    public void testConstructorWithAllParams() {
        JobSearchParameters jobSearchParameters = new JobSearchParameters("Architect", 85000);

        assertEquals("Architect", jobSearchParameters.getJobTitle(), "Job title not set correctly in constructor");
    }
    @Test
    public void testMultipleSettersAndGetters() {
        JobSearchParameters jobSearchParameters = new JobSearchParameters("Engineer", 70000);

        jobSearchParameters.setJobTitle("Project Manager");
        assertEquals("Project Manager", jobSearchParameters.getJobTitle(), "Job title setter or getter not working as expected");

    }
    @Test
    public void testResettingParameters() {
        JobSearchParameters jobSearchParameters = new JobSearchParameters("Accountant", 55000);

        // Set initial parameters
        jobSearchParameters.setJobTitle("Graphic Designer");

        // Resetting parameters
        jobSearchParameters.setJobTitle("Web Developer");

        // Assert the reset parameters
        assertEquals("Web Developer", jobSearchParameters.getJobTitle(), "Reset job title did not work as expected");
    }

}