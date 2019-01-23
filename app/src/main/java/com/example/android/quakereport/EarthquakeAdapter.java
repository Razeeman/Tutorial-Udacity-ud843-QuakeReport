package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * {@link EarthquakeAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link Earthquake} objects.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();
    private static final String LOCATION_SEPARATOR = " of ";

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
        ViewHolder viewHolder;

        // if recycled view is already inflated, its elements can be reused
        // otherwise inflating on every scroll would be too expensive
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.earthquake_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.magnitudeTextView = convertView.findViewById(R.id.magnitude);
            viewHolder.locationOffsetTextView = convertView.findViewById(R.id.location_offset);
            viewHolder.locationPrimaryTextView = convertView.findViewById(R.id.location_primary);
            viewHolder.dateTextView = convertView.findViewById(R.id.date);
            viewHolder.timeTextView = convertView.findViewById(R.id.time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Earthquake earthquake = getItem(position);

        String formattedMagnitude = formatMagnitude(earthquake.getMagnitude());
        viewHolder.magnitudeTextView.setText(formattedMagnitude);

        GradientDrawable magnitudeCircle = (GradientDrawable) viewHolder.magnitudeTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        String fullLocation = earthquake.getLocation();
        String locationOffset;
        String locationPrimary;

        // splitting string in the form of "99km N of Temp Location"
        // into "99km N of" and "Temp Location"
        if (fullLocation.contains(LOCATION_SEPARATOR)) {
            locationPrimary = fullLocation.split(LOCATION_SEPARATOR)[1];
            locationOffset = fullLocation.substring(0, fullLocation.length() - locationPrimary.length());
        } else {
            // if no precise distance found, it is replaced with default string "Near the"
            locationOffset = getContext().getString(R.string.near_the);
            locationPrimary = fullLocation;
        }

        viewHolder.locationOffsetTextView.setText(locationOffset);
        viewHolder.locationPrimaryTextView.setText(locationPrimary);

        Date date = new Date(earthquake.getTime());

        String formattedDate = formatDate(date);
        viewHolder.dateTextView.setText(formattedDate);

        String formattedTime = formatTime(date);
        viewHolder.timeTextView.setText(formattedTime);

        return convertView;
    }

    /**
     * Return int color for magnitude in a specified range
     */
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        //converting the color resource ID into an actual integer color value
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    /**
     * Return the formatted date string (i.e. "Jan 01, 1990").
     */
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy", Locale.US);
        return dateFormat.format(date);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM").
     */
    private String formatTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);
        return timeFormat.format(date);
    }

    /**
     * Return the formatted magnitude string in the form of "99.9" (one decimal)
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }

    static class ViewHolder {
        TextView magnitudeTextView;
        TextView locationOffsetTextView;
        TextView locationPrimaryTextView;
        TextView dateTextView;
        TextView timeTextView;
    }
}
