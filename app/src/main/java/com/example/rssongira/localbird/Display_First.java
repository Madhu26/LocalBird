package com.example.rssongira.localbird;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by RSSongira on 4/1/2017.
 */
public class Display_First extends AppCompatActivity {
    ImageView image;
    String icon_url;
   // String place_lat;
    //String place_long;
   protected static String getlat;
    protected static String getlg;
    protected double lat;
    //Resources res = getResources(); /** from an Activity */

    protected  double lg;
    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    private static  String url;
    // URL to get contacts JSON

    ArrayList<HashMap<String, String>> contactList;
    // Key-value Pair



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_prepare);
        lv=(ListView)findViewById(R.id.list);
        image=(ImageView)findViewById(R.id.getImage);
        Intent i5 = getIntent();
        Bundle b1= i5.getExtras();
       getlat = b1.getString("Latitude");
        getlg = b1.getString("Longitude");
         url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+getlat+","+getlg+"&radius=900&type=restaurant&key=AIzaSyB5J0DVdARLzuVMVp7pQlSMYeqtbDAaUuo";
        // list_item_first is the each item structure
        contactList = new ArrayList<>();
        new GetContacts().execute();

    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Display_First.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler_First sh = new HttpHandler_First();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray contacts = null;
                    try {
                        contacts = jsonObj.getJSONArray("results");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("place_id");
                        String name = c.getString("name");
                        //String rating = c.getString("rating");
                         icon_url = c.getString("icon");
                        String vicinity = c.getString("vicinity");


                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("place_id", id);
                        contact.put("name", name);
                        contact.put("icon_url",icon_url);
                      //  contact.put("rating",rating);
                        contact.put("vicinity",vicinity);

                        contactList.add(contact);
                    }

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
            } else {
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

            ListAdapter adapter = new SimpleAdapter(
                    Display_First.this, contactList,R.layout.list_item_first, new String[]{"name",
                    "vicinity"}, new int[]{R.id.getName, R.id.getVic});
                    //new ImageLoadTask(icon_url, image).execute();
            //image.setImageDrawable(res.getDrawable(R.drawable.myimage));
                        lv.setAdapter(adapter);
        }

    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
        // LoadImage to be displace in List View from the url got from Json Data
        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }
}




