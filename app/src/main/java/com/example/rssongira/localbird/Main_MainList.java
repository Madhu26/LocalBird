package com.example.rssongira.localbird;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by RSSongira on 4/4/2017.
 */
public class Main_MainList extends AppCompatActivity {

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
        setContentView(R.layout.main_mainlist);

        Custom_MainList customList = new Custom_MainList(this, names, desc, imageid);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(customList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "You Clicked " + names[i], Toast.LENGTH_SHORT).show();
            }
        });
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}