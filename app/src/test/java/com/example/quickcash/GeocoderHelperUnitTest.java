package com.example.quickcash;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import android.location.Address;
import android.location.Geocoder;

import com.example.quickcash.objects.Coordinates;
import com.example.quickcash.utilities.GeocoderHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class GeocoderHelperUnitTest {

    private final double DELTA_FOR_TESTING_DOUBLE_EQUALITY = 0.001;
    @Mock
    private Geocoder mockGeocoder;
    @Mock
    private Address mockAddress;
    private double testLatitude = 44.63749;
    private double testLongitude = -63.58739;
    private String testAddress = "Goldberg Computer Science Bldg, 6050 University Ave, Halifax, NS B3H 1W5";

    @Before
    public void setUp() throws IOException {
        when(mockAddress.getLatitude()).thenReturn(testLatitude);
        when(mockAddress.getLongitude()).thenReturn(testLongitude);
        when(mockAddress.getAddressLine(0)).thenReturn(testAddress);

        List<Address> mockAddresses = new ArrayList<>();
        mockAddresses.add(mockAddress);

        when(mockGeocoder.getFromLocation(testLatitude, testLongitude, 1))
                .thenReturn(mockAddresses);
        when(mockGeocoder.getFromLocationName(testAddress, 1))
                .thenReturn(mockAddresses);
    }

    @Test
    public void testGetAddressFromCoordinates() {
        String returnedAddress = GeocoderHelper.getAddressFromCoordinates(
                new Coordinates(testLatitude, testLongitude),
                mockGeocoder);

        assertEquals(testAddress, returnedAddress);
    }

    @Test
    public void testGetCoordinatesFromAddressString() {
        Coordinates returnedCoordinates = GeocoderHelper.getCoordinatesFromAddressString(
                testAddress, mockGeocoder);

        assertEquals(testLatitude, returnedCoordinates.getLatitude(), DELTA_FOR_TESTING_DOUBLE_EQUALITY);
        assertEquals(testLongitude, returnedCoordinates.getLongitude(), DELTA_FOR_TESTING_DOUBLE_EQUALITY);
    }

}
