package com.example.rssongira.localbird;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rssongira.localbird.data.Channel;
import com.example.rssongira.localbird.data.Item;
import com.example.rssongira.localbird.service.WeatherServiceCallback;
import com.example.rssongira.localbird.service.YahooService;

/**
 * Created by RSSongira on 4/2/2017.
 */
public class Weather_MainActivity extends AppCompatActivity implements WeatherServiceCallback
{
    private ImageView WeatherIconView;
    private TextView Temperature,Condition,Location;
    String place;
    private YahooService service;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        WeatherIconView=(ImageView)findViewById(R.id.na);
        Temperature=(TextView)findViewById(R.id.temperature);
        Condition=(TextView)findViewById(R.id.Condition);
        Location=(TextView)findViewById(R.id.location);


            service=new YahooService(this);
            // Showing Dialog
            dialog = new ProgressDialog(this);
            dialog.setTitle("Dialog Title");
            dialog.setMessage("Loading ............");
            dialog.show();

            service.RefereshWeather("Surat,Gujarat");






    }



    @Override
    public void ServiceSuccess(Channel channel) {
        dialog.hide();
        Item item =channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon_"+channel.getItem().getCondition().getCode(),null,getPackageName());
        @SuppressWarnings("deprication")
        Drawable weatherIcon = getResources().getDrawable(resourceId);
        WeatherIconView.setImageDrawable(weatherIcon);
        Location.setText(service.getLocation());
        Temperature.setText(item.getCondition().getTemperature()+"  "+channel.getUnits().getTemperature());
        Condition.setText(item.getCondition().getDescription());
    }



    @Override

    public void ServiceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this,"What"+exception.getMessage(),Toast.LENGTH_LONG).show();

    }
}
