package com.example.rssongira.localbird;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by RSSongira on 4/1/2017.
 */
public class MainList extends AppCompatActivity implements LocationListener {
    String lat;
    String provider;
    protected Context context;
    double latitude, longitude;
    //    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    //TextView txt;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    Location loc;
    private ListView listView;
    private String names[] = {
            "Airport",
            "ATM",
            "Cafe",
            "Court",
            "Fire_Station",
            "gym",
            "Hospital",
            "Library",
            "Mall",
            "Movie",
            "restaurants",
            "Zoo"
    };

    private String desc[] = {
            "get the best comfort",
            "money near you",
            "enjoy time with ur loving one",
            "get justice",
            "get ur firetruck",
            "day start with fitness",
            "another god near u",
            "increase ur knowledge",
            "enjoy free time",
            "see ur stars",
            "spend quality time with ur family",
            "spend time with nature"

    };


    private Integer imageid[] = {
            R.drawable.airport,
            R.drawable.atm,
            R.drawable.cafe,
            R.drawable.court,
            R.drawable.fire_station,
            R.drawable.gym,
            R.drawable.hospital,
            R.drawable.library,
            R.drawable.mall,
            R.drawable.movie,
            R.drawable.restaurant,
            R.drawable.zoo


    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);
        // First Get The current Location
        //txt = (TextView) findViewById(R.id.t);

        Custom_MainList customList = new Custom_MainList(this, names, desc, imageid);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(customList);





        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            // txt.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        } catch (SecurityException e) {
            // dialogGPS(this.getContext()); // lets the user know there is a problem with the gps
            Toast.makeText(this, "Security Exception", Toast.LENGTH_SHORT).show();
        }
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //txt.setText("Latitude:" + loc.getLatitude() + ", Longitude:" + loc.getLongitude());
            latitude = loc.getLatitude();
            longitude=loc.getLongitude();

        } catch (Exception e) {
            Toast.makeText(this,"Security Exception",Toast.LENGTH_SHORT).show();
            // dialogGPS(this.getContext()); // lets the user know there is a problem with the gps
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "You Clicked " + names[i], Toast.LENGTH_SHORT).show();
                // Go to key api
                // Need To pass the current Location  to Display_First Activity as First
                // Creating a bundle Object
                Bundle b= new Bundle();
                // Storing Data into Bundle
                b.putString("type",names[i]);
                b.putString("Latitude", String.valueOf(latitude));
                b.putString("Longitude", String.valueOf(longitude));
                Intent i3 = new Intent(MainList.this,Display_First.class);
                i3.putExtras(b);
                startActivity(i3);


            }
        });

    }


    @Override
    public void onLocationChanged(Location location) {



            //txt.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
            latitude = loc.getLatitude();
            longitude = loc.getLongitude();

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
