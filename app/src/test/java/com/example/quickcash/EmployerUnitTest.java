package com.example.quickcash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.quickcash.objects.Coordinates;
import com.example.quickcash.objects.Employer;

import org.junit.Test;

import java.util.List;

public class EmployerUnitTest {
    private final String testUserID = "123456789";
    private final String testName = "John Doe";
    private final String testEmail = "abc123@gmail.com";
    private final String testJobID1 = "My2Nj5vUvfKJ";
    private final String testJobID2 = "q9YFatHGPjRs";
    private final String testID = "12345";

    @Test
    public void testEmployerConstructor() {
        Employer employer = new Employer(testUserID, testName, testEmail);
        assertEquals(testUserID, employer.getID());
        assertEquals(testName, employer.getName());
        assertEquals(testEmail, employer.getEmail());
        assertTrue(employer.getPostedJobIDs().isEmpty());
        assertNull(employer.getCoordinates());
        assertEquals(0, employer.getPostedJobIDs().size());
    }

    @Test
    public void testGetID() {
        Employer employer = new Employer(testUserID, testName, testEmail);
        assertEquals(testUserID, employer.getID());
    }

    @Test
    public void testGetName() {
        Employer employer = new Employer(testUserID, testName, testEmail);
        assertEquals(testName, employer.getName());
    }

    @Test
    public void testGetEmail() {
        Employer employer = new Employer(testUserID, testName, testEmail);
        assertEquals(testEmail, employer.getEmail());
    }

    @Test
    public void testGetCoordinates() {
        Employer employer = new Employer(testUserID, testName, testEmail);
        assertNull(employer.getCoordinates());
    }

    @Test
    public void testSetCoordinates() {
        Employer employer = new Employer(testUserID, testName, testEmail);
        assertNull(employer.getCoordinates());
        Coordinates testCoordinates = new Coordinates(0, 0);
        employer.setCoordinates(testCoordinates);
        assertNotNull(employer.getCoordinates());
    }

    @Test
    public void testGetPostedJobIDs() {
        Employer employer = new Employer(testUserID, testName, testEmail);
        assertTrue(employer.getPostedJobIDs().isEmpty());
    }

    @Test
    public void testAddJobID() {
        Employer employer = new Employer(testUserID, testName, testEmail);
        employer.addJobID(testJobID1);
        employer.addJobID(testJobID2);

        List<String> postedJobIDs = employer.getPostedJobIDs();
        assertEquals(2, postedJobIDs.size());
        assertTrue(postedJobIDs.contains(testJobID1));
        assertTrue(postedJobIDs.contains(testJobID2));
    }

    @Test
    public void testGetPreferredEmployeeIDs() {
        Employer employer = new Employer(testUserID, testName, testEmail);
        assertEquals(0, employer.getPreferredEmployeeIDs().size());
    }

    @Test
    public void testAddPreferredEmployeeID() {
        Employer employer = new Employer(testUserID, testName, testEmail);
        employer.addPreferredEmployeeID(testID);
        assertEquals(1, employer.getPreferredEmployeeIDs().size());
        assertEquals(testID, employer.getPreferredEmployeeIDs().get(0));
    }

}
