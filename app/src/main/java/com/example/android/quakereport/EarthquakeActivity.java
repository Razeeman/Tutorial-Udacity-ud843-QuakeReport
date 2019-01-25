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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    public static final String REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    // reference to adapter to avoid several calls to findViewById
    private EarthquakeAdapter mEarthquakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ListView earthquakeListView = findViewById(R.id.list);
        mEarthquakeAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        earthquakeListView.setAdapter(mEarthquakeAdapter);

        // create an AsyncTask to perform network request to given URL on background thread and
        // update UI on the main thread.
        EarthquakeAsyncTask earthquakeAsyncTask = new EarthquakeAsyncTask(this);
        earthquakeAsyncTask.execute(REQUEST_URL);

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

    /**
     * AsyncTask to perform a network request on a background thread and update the UI.
     */
    private static class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {
        // weak reference to work with activity context in static class
        private WeakReference<EarthquakeActivity> mActivityWeakReference;

        EarthquakeAsyncTask(EarthquakeActivity context) {
            mActivityWeakReference = new WeakReference<>(context);
        }

        @Override
        protected List<Earthquake> doInBackground(String... url) {
            if (url == null) return null;

            // Query and return a list of earthquake occurrences.
            return QueryUtils.fetchEarthquakesData(REQUEST_URL);
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {
            if (earthquakes == null) return;

            EarthquakeActivity activity = mActivityWeakReference.get();
            if (activity == null || activity.isFinishing()) return;

            activity.updateUI(earthquakes);
        }
    }
}
