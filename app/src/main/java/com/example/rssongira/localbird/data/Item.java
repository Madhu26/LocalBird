package com.example.rssongira.localbird.data;

import org.json.JSONObject;

/**
 * Created by RSSongira on 4/2/2017.
 */
public class Item implements JSONPopulator {
    private Condition condition;
    public Condition getCondition()
    {
        return  condition;
    }

    @Override

    public void populate(JSONObject data) {
        condition  = new Condition();
        condition.populate(data.optJSONObject("condition"));

    }
}

