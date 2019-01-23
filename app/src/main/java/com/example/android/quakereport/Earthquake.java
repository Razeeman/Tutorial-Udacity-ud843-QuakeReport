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

    // url on the USGS site for the particular earthquake
    private String mUrl;

    /**
     * Construct an earthquake object
     *
     * @param magnitude is a magnitude of the earthquake
     * @param location  is a location of the earthquake
     * @param time      is a time of the earthquake occurrence in milliseconds
     * @param url       is a url to the page of the earthquake
     */
    public Earthquake(double magnitude, String location, long time, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mTime = time;
        mUrl = url;
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

    public String getUrl() {
        return mUrl;
    }
}
