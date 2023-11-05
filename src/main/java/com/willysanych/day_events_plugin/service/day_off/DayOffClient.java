package com.willysanych.day_events_plugin.service.day_off;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface DayOffClient {

    @GetExchange(url = "/today")
    public String checkDayOffToday(
            @RequestParam(name = "tz") String timezone,
            @RequestParam(name = "cc") String country
            );
}
