package com.example.android.quakereport;

/**
 * {@link Earthquake} represents a one earthquake occurrence.
 */
public class Earthquake {

    // magnitude of the earthquake
    private double mMagnitude;

    // location of the earthquake
    private String mLocation;

    // time of the earthquake in milliseconds
    private long mTime;

    /**
     * Construct an earthquake object
     *
     * @param magnitude is a magnitude of the earthquake
     * @param location  is a location of the earthquake
     * @param time      is a time of the earthquake occurrence in milliseconds
     */
    public Earthquake(double magnitude, String location, long time) {
        mMagnitude = magnitude;
        mLocation = location;
        mTime = time;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public long getTime() {
        return mTime;
    }
}
