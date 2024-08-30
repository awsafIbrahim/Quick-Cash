package com.example.quickcash.objects;

import android.location.Geocoder;

import com.example.quickcash.utilities.GeocoderHelper;

/**
 * Represents a set of coordinates (Latitude, Longitude).
 */
public class Coordinates {
    private double latitude;
    private double longitude;

    /**
     * Default constructor only used for reflection by Firebase Database.
     */
    public Coordinates() {}

    /**
     * Parametrised constructor used to create a set of coordinates.
     * @param latitude  The desired latitude.
     * @param longitude The desired longitude.
     */
    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Gets the latitude value of the set of coordinates.
     * @return  The latitude.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude value of the set of coordinates.
     * @return  The longitude.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Gets the address corresponding to the coordinates as a string.
     * @param geocoder  A geocoder object from the calling context.
     * @return          The address.
     */
    public String toFormattedString(Geocoder geocoder) {
        return GeocoderHelper.getAddressFromCoordinates(this, geocoder);
    }

}
