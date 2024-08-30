package com.example.quickcash;

import static org.junit.Assert.assertEquals;

import com.example.quickcash.objects.Coordinates;

import org.junit.Test;

public class CoordinatesUnitTest {

    private final double DELTA_FOR_TESTING_DOUBLE_EQUALITY = 0.001;
    private final double testLatitude = 44.63758;
    private final double testLongitude = -63.58711;

    @Test
    public void testLocationConstructor() {
        Coordinates coordinates = new Coordinates(testLatitude, testLongitude);
        assertEquals(testLatitude, coordinates.getLatitude(), DELTA_FOR_TESTING_DOUBLE_EQUALITY);
        assertEquals(testLongitude, coordinates.getLongitude(), DELTA_FOR_TESTING_DOUBLE_EQUALITY);
    }

    @Test
    public void testGetLatitude() {
        Coordinates coordinates = new Coordinates(testLatitude, testLongitude);
        assertEquals(testLatitude, coordinates.getLatitude(), DELTA_FOR_TESTING_DOUBLE_EQUALITY);
    }

    @Test
    public void testGetLongitude() {
        Coordinates coordinates = new Coordinates(testLatitude, testLongitude);
        assertEquals(testLongitude, coordinates.getLongitude(), DELTA_FOR_TESTING_DOUBLE_EQUALITY);
    }

}
