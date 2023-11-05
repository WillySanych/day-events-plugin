package com.willysanych.day_events_plugin.service.parser.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.willysanych.day_events_plugin.entity.EventEntity;
import com.willysanych.day_events_plugin.exception.ParseException;
import com.willysanych.day_events_plugin.service.parser.EventsParser;

@Service
public class CalendRuParser implements EventsParser {

    Logger logger = LoggerFactory.getLogger(CalendRuParser.class);

    private final String STRING_DASH = "-";
    private final String ITEMS_NET_CLASS = ".itemsNet";
    private final String TITLE_CLASS = ".title";
    private final String ANCHOR = "a";
    private final String YEAR_CLASS = ".year ";
    private final String HREF_ATTRIBUTE = "href";

    private String baseUrl;
    private Document document;
    private List<EventEntity> events;

    public CalendRuParser(@Value("${parser.baseUrl}") String baseUrl) {
        this.baseUrl = baseUrl;
        loadAndParseEvents();
    }

    @Scheduled(cron = "${scheduler.load-events.cron}", zone = "${scheduler.timezone}")
    private void loadAndParseEvents() {
        loadDocument();
        parseEvents();
    }

    @Override
    public List<EventEntity> getEvents() {
        return events;
    }

    private void parseEvents() {
        if (document == null) {
            return;
        }

        Element eventRootElem = document.selectFirst(ITEMS_NET_CLASS);

        List<EventEntity> events = eventRootElem.children().stream().flatMap(eventElem -> {
            Element title = eventElem.selectFirst(TITLE_CLASS).selectFirst(ANCHOR);
            Element year = eventElem.selectFirst(YEAR_CLASS);

            if (title == null && year == null) {
                return Stream.empty();
            }

            EventEntity event = new EventEntity();
            if (title != null) {
                event.setTitle(title.text());
                event.setLink(title.attr(HREF_ATTRIBUTE));
            }
            if (year != null) {
                event.setYear(year.text());
            }

            logger.debug(event.toString());

            return Stream.of(event);
        }).toList();

        this.events = events;
    }

    private void loadDocument() {
        String url = formatUrl();

        try {
            logger.debug("Started loading document from page: " + url);
            document = Jsoup.connect(url).get();
            logger.debug("Document loaded successful");
        } catch (IOException e) {
            throw new ParseException("Unable to load page for parsing");
        }
    }

    private String formatUrl() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;

        StringBuilder url = new StringBuilder()
                .append(baseUrl)
                .append(month)
                .append(STRING_DASH)
                .append(day);

        return url.toString();
    }
}
