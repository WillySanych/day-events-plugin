package com.willysanych.day_events_plugin.service.parser;

import java.util.List;

import com.willysanych.day_events_plugin.entity.EventEntity;

public interface EventsParser {
    public List<EventEntity> getEvents();
}
