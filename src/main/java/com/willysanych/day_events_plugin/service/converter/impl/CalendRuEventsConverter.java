package com.willysanych.day_events_plugin.service.converter.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.willysanych.day_events_plugin.entity.EventEntity;
import com.willysanych.day_events_plugin.service.converter.EventsConverter;
import com.willysanych.day_events_plugin.service.parser.EventsParser;
import com.willysanych.day_events_plugin.util.MessageUtil;
import com.willysanych.day_events_plugin.util.StringUtils;

@Service
public class CalendRuEventsConverter implements EventsConverter {

    private final String BASE_HEADER_MESSAGE = "events.header";
    private final String EVENTS_LINK_MESSAGE = "events.link";

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
        message.append(StringUtils.NEWLINE);

        for (int i = 0; i < events.size(); i++) {
            EventEntity event = events.get(i);
            StringBuilder sb = new StringBuilder();

            appendIndex(sb, i);
            sb.append(StringUtils.SPACE);
            appendYear(sb, event);
            sb.append(StringUtils.SPACE);
            appendTitle(sb, event);
            sb.append(StringUtils.SPACE);
            appendLink(sb, event);
            sb.append(StringUtils.NEWLINE);

            message.append(sb.toString());
        }

        return message.toString();
    }

    private String getMessageHeader() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT,
                LocaleContextHolder.getLocale());

        Object[] args = new Object[] { day, monthName };
        String baseMessage = messageUtil.getLocalizedMessage(BASE_HEADER_MESSAGE, args);

        String message = String.format(baseMessage, day, monthName);
        return message;
    }

    private void appendIndex(StringBuilder sb, int i) {
        sb.append(i + 1);
        sb.append(StringUtils.DOT);
    }

    private void appendYear(StringBuilder sb, EventEntity event) {
        sb.append(StringUtils.BRACKET_OPEN);
        sb.append(event.getYear());
        sb.append(StringUtils.BRACKET_CLOSE);
    }

    private void appendTitle(StringBuilder sb, EventEntity event) {
        sb.append(event.getTitle());
    }

    private void appendLink(StringBuilder sb, EventEntity event) {
        sb.append(StringUtils.BRACKET_OPEN);
        sb.append(StringUtils.SQUARE_BRACKET_OPEN);
        sb.append(messageUtil.getLocalizedMessage(EVENTS_LINK_MESSAGE, null));
        sb.append(StringUtils.SQUARE_BRACKET_CLOSE);
        sb.append(StringUtils.BRACKET_OPEN);
        sb.append(event.getLink());
        sb.append(StringUtils.BRACKET_CLOSE);
        sb.append(StringUtils.BRACKET_CLOSE);
    }
}
