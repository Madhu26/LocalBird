package com.example.rssongira.localbird;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Going to start Application
    }
    public void Move(View v)
    {
        Intent i=new Intent(MainActivity.this,CityWel.class);
        startActivity(i);
    }
}
