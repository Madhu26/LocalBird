package com.example.rssongira.localbird.data;

import com.example.rssongira.localbird.data.JSONPopulator;

import org.json.JSONObject;

/**
 * Created by RSSongira on 4/2/2017.
 */
public class Units implements JSONPopulator {
    private  String temperature;
    // Faherenheit

    public String getTemperature()
    {
        return  temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature=data.optString("temperature");
        // F

    }
}

