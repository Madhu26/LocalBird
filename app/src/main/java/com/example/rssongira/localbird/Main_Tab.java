package com.example.rssongira.localbird;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.widget.Toast;

/**
 * Created by RSSongira on 4/2/2017.
 */
public class Main_Tab extends TabActivity {
    TabHost tabhost;
    TabHost.TabSpec t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab);
        //Toast.makeText(Main_Tab.this, "Inside tha tab class", Toast.LENGTH_SHORT).show();
       tabhost=(TabHost)findViewById(android.R.id.tabhost);
        tabhost.setup();
// t1 t2
        t1=tabhost.newTabSpec("Tab1");
        t1.setContent(R.id.tab1);
        t1.setIndicator("Local");
        Intent i=new Intent(Main_Tab.this,MainList.class);
        t1.setContent(i);


        t2=tabhost.newTabSpec("Tab2");
        t2.setContent(R.id.tab2);
        t2.setIndicator("Weather Today");
        Intent i1=new Intent(Main_Tab.this,Weather_MainActivity.class);
        t2.setContent(i1);

        tabhost.addTab(t1);
        tabhost.addTab(t2);

    }


}