package com.example.rssongira.localbird;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by RSSongira on 4/1/2017.
 */
public class CityWel extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_wel);

    }
    public void GoToList(View v)
    {
        Intent i1=new Intent(CityWel.this,MainList.class);
        startActivity(i1);

    }
}
