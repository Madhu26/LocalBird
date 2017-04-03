package com.example.rssongira.localbird;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by RSSongira on 4/3/2017.
 */
public class Maps_Main extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap myGoogleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(googlePlayServiceAvailable())
        {
            Toast.makeText(this, "Perfect", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.maps_main);

            intitMap();

        }
        else
        {
            // No google Maps


        }

    }


    private void intitMap() {
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                myGoogleMap = googleMap; // here you set your Google map
                goToLocationZoom(21.167865,72.787199,15);

                // here you do the rest of your calculations with your map
            }
        });

        // This method automatically initializes map system system and view

    }

    // Check whether the user has the play service available
    public boolean googlePlayServiceAvailable()
    {
        GoogleApiAvailability api= GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS)
        {
            return true;
        }
        else if(api.isUserResolvableError(isAvailable))
        {
            Dialog dialog = api.getErrorDialog(this,isAvailable,0);
            // 0 is request Code
            dialog.show();

        }
        else
        {
            Toast.makeText(this,"Cannot play services",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myGoogleMap = googleMap;
        // 15 zoom level

        // Default Map points to 00 latitued longtiude Africa

    }
    private void goToLocationZoom(double lat, double lg,float zoom)
    {
        LatLng ll = new LatLng(lat,lg);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
        myGoogleMap.moveCamera(update);
        // Camera Update defines Camera move Used using CameraUpdateFactory

    }
    private void goToLocation(double lat, double lg)
    {
        LatLng ll = new LatLng(lat,lg);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        // move to lan and long

        myGoogleMap.moveCamera(update);
    }


}
