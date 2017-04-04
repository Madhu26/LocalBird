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
import android.view.View;
import android.widget.AdapterView;
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
import java.util.List;
import java.util.Map;

/**
 * Created by RSSongira on 4/1/2017.
 */
public class Display_First extends AppCompatActivity {
//    ImageView image;
    String icon_url;
    String plag;
    String plng;
   // String place_lat;
    //String place_long;
    String type;
    Bundle b7=new Bundle();
   protected static String getlat;
    protected static String getlg;
    protected double lat;
    //Resources res = getResources(); /** from an Activity */

    protected  double lg;
    private String TAG = Display_First.class.getSimpleName();
    private String id;
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
  //      image=(ImageView)findViewById(R.id.getImage);
        Intent i5 = getIntent();
        Bundle b1= i5.getExtras();
       getlat = b1.getString("Latitude");
        getlg = b1.getString("Longitude");
        type= b1.getString("type");
        Toast.makeText(Display_First.this,"Inside the display "+type,Toast.LENGTH_SHORT).show();
         url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+getlat+","+getlg+"&radius=900&type="+type+"&key=AIzaSyB5J0DVdARLzuVMVp7pQlSMYeqtbDAaUuo";
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
                        plag = c.getJSONObject("geometry").getJSONObject("location").getString("lat");
                        plng = c.getJSONObject("geometry").getJSONObject("location").getString("lat");
                        id = c.getString("place_id");
                       b7.putString(String.valueOf(i),id);
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
                    "vicinity","icon_url"}, new int[]{R.id.getName, R.id.getVic,R.id.getImage});
                   // new ImageLoadTask("https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png", image).execute();


                        lv.setAdapter(adapter);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

               String PLACE_ID = b7.getString(String.valueOf(arg2));
                    //Toast.makeText(Display_First.this,"Lets Check ",Toast.LENGTH_SHORT).show();
                   Intent i=new Intent(Display_First.this, Details_Main.class);
                    i.putExtra("PLACE_ID", PLACE_ID);
                    //i.putExtras(b7);
                   startActivity(i);
                }
            });

        }

    }

    /*public class MainActivity extends AppCompatActivity {
        ListView lv;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            lv = (ListView) findViewById(R.id.listView);
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("uri",
                    "http://upload.wikimedia.org/wikipedia/commons/thumb/f/f9/Wiktionary_small.svg/350px-Wiktionary_small.svg.png");
            //here u can add as many uri as u want
            data.add(map);
            MySimpleAdapter adapter = new MySimpleAdapter(MainActivity.this, data,
                    R.layout.row, new String[] {}, new int[] {});
            lv.setAdapter(adapter);
        }
    }**/
}




