package com.willysanych.day_events_plugin.converter.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.willysanych.day_events_plugin.converter.EventsConverter;
import com.willysanych.day_events_plugin.entity.EventEntity;
import com.willysanych.day_events_plugin.parser.EventsParser;

@Service
public class CalendRuEventsConverter implements EventsConverter {

    private final String BRACKET_OPEN = "(";
    private final String BRACKET_CLOSE = ")";
    private final String SPACE = " ";
    private final String DOT = ".";
    private final String UNLINE_URL_MD = "[link]";
    private final String NEWLINE = "\n";

    private final EventsParser parser;

    public CalendRuEventsConverter(EventsParser parser) {
        this.parser = parser;
    }

    @Override
    public String getMessage() {
        List<EventEntity> events = parser.getEvents();

        List<String> formattedEvents = new ArrayList<String>();
        for (int i = 0; i < events.size(); i++) {
            EventEntity event = events.get(i);
            StringBuilder sb = new StringBuilder();

            appendIndex(sb, i);
            sb.append(SPACE);
            appendYear(sb, event);
            sb.append(SPACE);
            appendTitle(sb, event);
            sb.append(SPACE);
            appendLink(sb, event);

            formattedEvents.add(sb.toString());
        }

        String message = formattedEvents.stream().reduce((event1, event2) -> event1 + NEWLINE + event2).get();

        return message;
    }

    private void appendIndex(StringBuilder sb, int i) {
        sb.append(i + 1);
        sb.append(DOT);
    }

    private void appendYear(StringBuilder sb, EventEntity event) {
        sb.append(BRACKET_OPEN);
        sb.append(event.getYear());
        sb.append(BRACKET_CLOSE);
    }

    private void appendTitle(StringBuilder sb, EventEntity event) {
        sb.append(event.getTitle());
    }

    private void appendLink(StringBuilder sb, EventEntity event) {
        sb.append(BRACKET_OPEN);
        sb.append(UNLINE_URL_MD);
        sb.append(BRACKET_OPEN);
        sb.append(event.getLink());
        sb.append(BRACKET_CLOSE);
        sb.append(BRACKET_CLOSE);
    }
}
