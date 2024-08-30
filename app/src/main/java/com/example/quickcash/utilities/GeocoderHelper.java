package com.example.quickcash.utilities;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.example.quickcash.objects.Coordinates;

import java.io.IOException;
import java.util.List;

/**
 * Geocoder utility to convert between Coordinate objects and a string representing the closest
 * location defined by those coordinates according to geocoder.
 */
public class GeocoderHelper {
    /**
     * Retrieves the address from a set of provided Coordinates using the Geocoder.
     * @param coordinates   The coordinates for which to obtain the address.
     * @param geocoder      The Geocoder instance to use.
     * @return              The address corresponding to the Coordinates.
     */
    public static String getAddressFromCoordinates(Coordinates coordinates, Geocoder geocoder) {
        double latitude = coordinates.getLatitude();
        double longitude = coordinates.getLongitude();

        List<Address> addresses;
        String addressText = "Location not available";

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                addressText = address.getAddressLine(0);
            }
        } catch (IOException e) {
            Log.e("GeocoderHelper:getAddress", e.getMessage(), e);
        }

        return addressText;
    }

    /**
     * Retrieves the Coordinates from the provided address using the Geocoder.
     * @param address   The address string for which to obtain the Coordinates.
     * @param geocoder  The Geocoder instance to use.
     * @return          The Coordinates corresponding to the address.
     */
    public static Coordinates getCoordinatesFromAddressString(String address, Geocoder geocoder) {
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                return new Coordinates(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException e) {
            Log.e("GeocoderHelper:getCoordinates", e.getMessage(), e);
        }

        return null;
    }

}
