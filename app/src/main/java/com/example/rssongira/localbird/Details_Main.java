package com.example.rssongira.localbird;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by RSSongira on 4/3/2017.
 */
public class Details_Main extends FragmentActivity implements OnMapReadyCallback {
    String lat;
    String lng;
    GoogleMap myGoogleMap;
    TextView tname,tplace;
    private String TAG = Details_Main.class.getSimpleName();
        private String Id;
    String name;
    String formatted_address;
    private ProgressDialog pDialog;
    //private ListView lv;
    Bundle b9 = new Bundle();

    // URL to get contacts JSON
    private static String url;
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(googlePlayServiceAvailable())
        {
            //Toast.makeText(this, "Perfect", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.placesdetails_list);

            intitMap();

        }
        else
        {
            // No google Maps
            Toast.makeText(Details_Main.this, "Sorry Map Service Unavailable", Toast.LENGTH_SHORT).show();

        }

        //setContentView(R.layout.placesdetails_list);
        tname=(TextView)findViewById(R.id.name);
        tplace=(TextView)findViewById(R.id.place_id);

        Id = getIntent().getExtras().getString("PLACE_ID");

        url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+Id+"&key=AIzaSyB5J0DVdARLzuVMVp7pQlSMYeqtbDAaUuo";

        contactList = new ArrayList<>();

     //   lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();
    }

    private void intitMap() {
        MapFragment fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);
        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                myGoogleMap = googleMap; // here you set your Google map
                String tlag = b9.getString("lat");
                String tlng = b9.getString("lng");

                goToLocationZoom(Double.parseDouble(tlag),Double.parseDouble(tlng),15);

                // here you do the rest of your calculations with your map
            }
        });

        // This method automatically initializes map system system and view

    }

    public void GPSIntent(View v)
    {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=40.7143528,-74.0059731"));
        startActivity(intent);

    }

    private boolean googlePlayServiceAvailable() {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myGoogleMap = googleMap;
    }

   /* private void intitMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myfragment);
        mapFragment.getMapAsync(this);
        // This method automatically initializes map system system and view
    }**/
   /* private boolean googlePlayServiceAvailable() {
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


**/
    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void>  {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Details_Main.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Toast.makeText(MainActivity.this,"Inside do ",Toast.LENGTH_LONG).show();
            HttpHandler_First sh = new HttpHandler_First();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            //Toast.makeText(MainActivity.this,"JSONSTR",Toast.LENGTH_LONG).show();
            if (jsonStr != null) {

                try {
                    // Toast.makeText(MainActivity.this,"Inside TRY STARt",Toast.LENGTH_SHORT).show();
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    //Toast.makeText(MainActivity.this,"object cfreated success",Toast.LENGTH_LONG).show();
                    // Getting JSON Array node
                    JSONObject contacts = null;
                    try
                    {
                        contacts = jsonObj.getJSONObject("result");

                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    JSONObject sys=contacts.getJSONObject("geometry").getJSONObject("location");
                    lat=sys.getString("lat");
                    lng=sys.getString("lng");
                        b9.putString("lat",lat);
                        b9.putString("lng",lng);

                    // looping through All Contacts
                    //for (int i = 0; i < contacts.length(); i++) {
                    //JSONObject c = contacts.getJSONObject();

                     name = contacts.getString("name");
                     formatted_address = contacts.getString("formatted_address");
                    //String email = c.getString("email");
                    // String address = c.getString("address");
                    //String gender = c.getString("gender");

                    // Phone node is JSON Object
                       /* JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");*/

                    // tmp hash map for single contact
                    HashMap<String, String> contact = new HashMap<>();

                    // adding each child node to HashMap key => value
                    contact.put("name", name);
                    contact.put("formatted_address", formatted_address);

                    //contact.put("email", email);
                    //contact.put("mobile", mobile);

                    // adding contact to contact list
                    contactList.add(contact);

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            }
            else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            /*ListAdapter adapter = new SimpleAdapter(
                    Details_Main.this, contactList,R.layout.placesdetails_list, new String[]{"name","formatted_address"
            }, new int[]{R.id.name, R.id.place_id});
                Toast.makeText(Details_Main.this,"Lat : "+lat+"Lng :"+lng,Toast.LENGTH_SHORT).show();
            lv.setAdapter(adapter);**/
            tplace.setText(formatted_address);
            tname.setText(name);
        }


    }

   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        myGoogleMap = googleMap;
        //goToLocationZoom();
      //  goToLocationZoom(Double.parseDouble(lat),Double.parseDouble(lng),15);
        goToLocationZoom(21.167865,72.787199,15);
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

**/
}

