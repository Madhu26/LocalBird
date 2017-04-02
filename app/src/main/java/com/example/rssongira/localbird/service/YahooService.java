package com.example.rssongira.localbird.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.rssongira.localbird.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by RSSongira on 4/2/2017.
 */
public class YahooService  {
    WeatherServiceCallback callback;
    private String location;
    private Exception error;
    public YahooService(WeatherServiceCallback callback)
    {
        this.callback = callback;

    }
    public void RefereshWeather(final String location)
    {
        this.location = location;
        AsyncTask<String, Void, String> execute = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                // notrunning in Ui Thread
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u= 'c'", params[0]);

                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));
                // endpoint  = http path with query inserted
                try {
                    URL url = new URL(endpoint);
                    // pass as URL
                    URLConnection connection = url.openConnection();
                    // open Connection to URL

                    InputStream inputstream = connection.getInputStream();
                    // Page string get
                    // inputstream  = JSON
                    BufferedReader reader =new BufferedReader(new InputStreamReader(inputstream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line= reader.readLine())!=null)
                    {
                        result.append(line);
                    }
                    return  result.toString();
                    // Result contains full append data =JSON DATA
                }
                catch (Exception e)
                {
                    error = e;
                }
                /*catch (MalformedURLException e) {
                    //e.printStackTrace();
                    error = e;
                    return  null;
                } catch (IOException e) {
                   // e.printStackTrace();
                    error = e;
                }**/


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                // Thorwn to indicate link is wrong
                if(s==null && error!=null){
                    callback.ServiceFailure(error);
                    return;
                }
                try {
                    JSONObject data =new JSONObject(s);
                    JSONObject queryResults  = data.optJSONObject("query");
                    int count = queryResults.optInt("count");
                    if(count == 0)
                    {
                        callback.ServiceFailure(new LocationWeatherException("No weather Information"+location));
                        return;
                    }
                    Channel ch = new Channel();
                    ch.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    callback.ServiceSuccess(ch);

                }catch (JSONException e)
                {
                    //e.printStackTrace();
                    callback.ServiceFailure(e);
                }
                super.onPostExecute(s);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }.execute(location);

    }
    public String getLocation()
    {
        return  location;
    }
    public class LocationWeatherException extends  Exception{
        public  LocationWeatherException(String detailMessage)
        {
            super(detailMessage);
        }
    }
}

