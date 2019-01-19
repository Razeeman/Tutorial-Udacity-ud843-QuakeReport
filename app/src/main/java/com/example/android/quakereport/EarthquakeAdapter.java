package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link EarthquakeAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link Earthquake} objects.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    /**
     * Custom constructor.
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the list elements.
     *
     * @param context     The current context. Used to inflate the layout file.
     * @param earthquakes A List of Earthquake objects to display in a list
     */
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    /**
     * Provides a view for an AdapterView
     *
     * @param position The position in the list of data that should be displayed
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // if recycled view is already inflated, its elements can be reused
        // otherwise inflating on every scroll would be too expensive
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        Earthquake earthquake = getItem(position);

        TextView magnitudeTextView = convertView.findViewById(R.id.magnitude);
        magnitudeTextView.setText(String.valueOf(earthquake.getMagnitude()));

        TextView locationTextView = convertView.findViewById(R.id.location);
        locationTextView.setText(earthquake.getLocation());

        TextView dateTextView = convertView.findViewById(R.id.date);
        dateTextView.setText(earthquake.getDate());

        return convertView;
    }
}
