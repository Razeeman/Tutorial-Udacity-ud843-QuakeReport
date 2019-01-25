package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Loads a list of earthquakes by using AsyncTask
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    // url to make request to
    private String mUrl;

    /**
     * Constructs a new EarthquakeLoader
     * @param context context of the activity
     * @param url     url to make request to
     */
    EarthquakeLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    /**
     * force start of the loader
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * Background thread
     */
    @Nullable
    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl == null) return null;
        return QueryUtils.fetchEarthquakesData(mUrl);
    }
}
