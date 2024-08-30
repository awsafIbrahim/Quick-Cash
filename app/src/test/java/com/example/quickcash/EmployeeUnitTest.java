package com.example.quickcash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.quickcash.objects.Coordinates;
import com.example.quickcash.objects.Employee;

import org.junit.Test;

public class EmployeeUnitTest {
    private final double DELTA_FOR_TESTING_FLOAT_EQUALITY = 0.001;
    private final String testUserID = "123456789";
    private final String testName = "John Doe";
    private final String testEmail = "abc123@gmail.com";
    private final String testID = "12345";

    @Test
    public void testEmployeeConstructor() {
        Employee employee = new Employee(testUserID, testName, testEmail);
        assertEquals(testUserID, employee.getID());
        assertEquals(testName, employee.getName());
        assertEquals(testEmail, employee.getEmail());
        assertNull(employee.getCoordinates());
        assertEquals(0, employee.getJobApplicationIDs().size());
        assertEquals(0, employee.getAcceptedJobIDs().size());
        assertEquals(0, employee.getPreferredEmployerIDs().size());
    }

    @Test
    public void testGetID() {
        Employee employee = new Employee(testUserID, testName, testEmail);
        assertEquals(testUserID, employee.getID());
    }

    @Test
    public void testGetName() {
        Employee employee = new Employee(testUserID, testName, testEmail);
        assertEquals(testName, employee.getName());
    }

    @Test
    public void testGetEmail() {
        Employee employee = new Employee(testUserID, testName, testEmail);
        assertEquals(testEmail, employee.getEmail());
    }

    @Test
    public void testGetCoordinates() {
        Employee employee = new Employee(testUserID, testName, testEmail);
        assertNull(employee.getCoordinates());
    }

    @Test
    public void testSetCoordinates() {
        Employee employee = new Employee(testUserID, testName, testEmail);
        assertNull(employee.getCoordinates());
        Coordinates testCoordinates = new Coordinates(0, 0);
        employee.setCoordinates(testCoordinates);
        assertNotNull(employee.getCoordinates());
    }

    @Test
    public void testGetApplicationIDs() {
        Employee employee = new Employee(testUserID, testName, testEmail);
        assertEquals(0, employee.getJobApplicationIDs().size());
    }

    @Test
    public void testAddJobApplicationID() {
        Employee employee = new Employee(testUserID, testName, testEmail);
        employee.addJobApplicationID(testID);
        assertEquals(1, employee.getJobApplicationIDs().size());
        assertEquals(testID, employee.getJobApplicationIDs().get(0));
    }

    @Test
    public void testGetAcceptedJobIDs() {
        Employee employee = new Employee(testUserID, testName, testEmail);
        assertEquals(0, employee.getAcceptedJobIDs().size());
    }

    @Test
    public void testAddAcceptedJobID() {
        Employee employee = new Employee(testUserID, testName, testEmail);
        employee.addAcceptedJobID(testID);
        assertEquals(1, employee.getAcceptedJobIDs().size());
        assertEquals(testID, employee.getAcceptedJobIDs().get(0));
    }

    @Test
    public void testGetPreferredEmployerIDs() {
        Employee employee = new Employee(testUserID, testName, testEmail);
        assertEquals(0, employee.getPreferredEmployerIDs().size());
    }

    @Test
    public void testAddPreferredEmployerID() {
        Employee employee = new Employee(testUserID, testName, testEmail);
        employee.addPreferredEmployerID(testID);
        assertEquals(1, employee.getPreferredEmployerIDs().size());
        assertEquals(testID, employee.getPreferredEmployerIDs().get(0));
    }

    @Test
    public void testInitialRating() {
        Employee employee = new Employee(testUserID, testName, testEmail);
        assertFalse(employee.hasRating());
        assertEquals(-1.0f, employee.getRating(), DELTA_FOR_TESTING_FLOAT_EQUALITY);
    }

    @Test
    public void testAddRating() {
        Employee employee = new Employee(testUserID, testName, testEmail);

        employee.addRating(5.0f);
        assertTrue(employee.hasRating());
        assertEquals(5.0f, employee.getRating(), DELTA_FOR_TESTING_FLOAT_EQUALITY);

        employee.addRating(1.0f);
        assertTrue(employee.hasRating());
        assertEquals(3.0f, employee.getRating(), DELTA_FOR_TESTING_FLOAT_EQUALITY);
    }

}
