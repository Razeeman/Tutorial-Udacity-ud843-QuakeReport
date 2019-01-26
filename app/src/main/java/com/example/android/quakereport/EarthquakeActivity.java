/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity
        implements LoaderCallbacks<List<Earthquake>> {

    @SuppressWarnings("unused")
    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    // url to request earthquake data from USGS site
    public static final String REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    // unique loader id
    private static final int LOADER_ID = 0;

    // references to avoid several calls to findViewById
    private EarthquakeAdapter mEarthquakeAdapter;
    private TextView mEmptyListTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ListView earthquakeListView = findViewById(R.id.list);
        mEarthquakeAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        earthquakeListView.setAdapter(mEarthquakeAdapter);

        mEmptyListTextView = findViewById(R.id.empty_list);
        earthquakeListView.setEmptyView(mEmptyListTextView);

        mProgressBar = findViewById(R.id.loading_spinner);

        // getSupportLoaderManager is deprecated as of API 28, but it is part of the tutorial
        // noinspection deprecation
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        // set clickListener on ListView Item to start intent to open web page with particular url
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake earthquake = mEarthquakeAdapter.getItem(position);
                Uri uri = Uri.parse(earthquake.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    /**
     * Update the UI.
     */
    private void updateUI(List<Earthquake> earthquakes) {
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mEarthquakeAdapter.clear();
            mEarthquakeAdapter.addAll(earthquakes);
        }
    }

    @NonNull
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new EarthquakeLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        // update view with new earthquakes
        updateUI(earthquakes);

        // set text for empty list
        mEmptyListTextView.setText(R.string.empty_list_message);

        // hide loading bar
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquake>> loader) {
        mEarthquakeAdapter.clear();
    }
}
