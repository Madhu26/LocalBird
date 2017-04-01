package com.example.rssongira.localbird;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by RSSongira on 4/1/2017.
 */
public class MainList extends AppCompatActivity implements  LocationListener{
    String lat;
    String provider;
    protected Context context;
    double latitude,longitude;
//    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    TextView txt;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);
        // First Get The current Location
        txt = (TextView) findViewById(R.id.t);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            // txt.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        } catch (SecurityException e) {
            // dialogGPS(this.getContext()); // lets the user know there is a problem with the gps
            Toast.makeText(this,"Security Exception",Toast.LENGTH_SHORT).show();
        }
        try {
            loc= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            txt.setText("Latitude:" + loc.getLatitude() + ", Longitude:" + loc.getLongitude());
            latitude = loc.getLatitude();
            longitude=loc.getLongitude();

        } catch (SecurityException e) {
            Toast.makeText(this,"Security Exception",Toast.LENGTH_SHORT).show();
            // dialogGPS(this.getContext()); // lets the user know there is a problem with the gps
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        txt.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        latitude = loc.getLatitude();
        longitude=loc.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void ResturantClick(View v)
    {
        // Go to key api
        // Need To pass the current Location  to Display_First Activity as First
        // Creating a bundle Object
        Bundle b= new Bundle();
        // Storing Data into Bundle
        b.putString("Latitude", String.valueOf(latitude));
        b.putString("Longitude", String.valueOf(longitude));
        Intent i3 = new Intent(MainList.this,Display_First.class);
        i3.putExtras(b);
        startActivity(i3);

    }
}
