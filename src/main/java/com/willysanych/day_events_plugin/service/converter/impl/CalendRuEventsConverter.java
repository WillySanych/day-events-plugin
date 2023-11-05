package com.willysanych.day_events_plugin.service.converter.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.willysanych.day_events_plugin.entity.EventEntity;
import com.willysanych.day_events_plugin.service.converter.EventsConverter;
import com.willysanych.day_events_plugin.service.parser.EventsParser;
import com.willysanych.day_events_plugin.util.MessageUtil;

@Service
public class CalendRuEventsConverter implements EventsConverter {

    private final String BRACKET_OPEN = "(";
    private final String BRACKET_CLOSE = ")";
    private final String SPACE = " ";
    private final String DOT = ".";
    private final String UNLINE_URL_MD = "[link]";
    private final String NEWLINE = "\n";
    private final String BASE_HEADER_MESSAGE = "events.header.message";

    private final EventsParser parser;
    private final MessageUtil messageUtil;

    public CalendRuEventsConverter(EventsParser parser, MessageUtil messageUtil) {
        this.parser = parser;
        this.messageUtil = messageUtil;
    }

    @Override
    public String getMessage() {
        List<EventEntity> events = parser.getEvents();

        StringBuilder message = new StringBuilder();
        message.append(getMessageHeader());
        message.append(NEWLINE);

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
            sb.append(NEWLINE);

            message.append(sb.toString());
        }

        return message.toString();
    }

    private String getMessageHeader() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String monthName = calendar.getDisplayName(Calendar.MONTH,
                Calendar.LONG_FORMAT, LocaleContextHolder.getLocale());

        Object[] args = new Object[]{day, monthName};
        String baseMessage = messageUtil.getLocalizedMessage(BASE_HEADER_MESSAGE, args);

        String message = String.format(baseMessage, day, monthName);
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