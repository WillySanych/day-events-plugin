package com.willysanych.day_events_plugin.sender;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import com.willysanych.day_events_plugin.dto.EventDto;

public interface EventsSenderClient {

    @PostExchange(url = "/send", contentType = "application/json")
    public void sendEvents(@RequestBody EventDto dto);
}
