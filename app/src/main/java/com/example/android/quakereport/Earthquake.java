package com.example.android.quakereport;

/**
 * {@link Earthquake} represents a one earthquake occurrence.
 */
public class Earthquake {

    // magnitude of the earthquake
    private double mMagnitude;

    // location of the earthquake
    private String mLocation;

    // date of the earthquake
    private String mDate;

    /**
     * Construct an earthquake object
     *
     * @param magnitude is a magnitude of the earthquake
     * @param location  is a location of the earthquake
     * @param date      is a date of the earthquake
     */
    public Earthquake(double magnitude, String location, String date) {
        mMagnitude = magnitude;
        mLocation = location;
        mDate = date;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getDate() {
        return mDate;
    }
}
