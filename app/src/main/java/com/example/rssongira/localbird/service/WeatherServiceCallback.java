package com.example.rssongira.localbird.service;

import com.example.rssongira.localbird.data.Channel;

/**
 * Created by RSSongira on 4/2/2017.
 */
public interface WeatherServiceCallback {
    // Check for channel type
    void ServiceSuccess(Channel channel);
    void ServiceFailure(Exception exception);


}
