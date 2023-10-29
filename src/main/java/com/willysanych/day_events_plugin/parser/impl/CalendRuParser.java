package com.willysanych.day_events_plugin.parser.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.willysanych.day_events_plugin.entity.EventEntity;
import com.willysanych.day_events_plugin.exception.ParseException;
import com.willysanych.day_events_plugin.parser.Parser;

@Service
public class CalendRuParser implements Parser {

    Logger logger = LoggerFactory.getLogger(CalendRuParser.class);

    private final String STRING_DASH = "-";
    private final String ITEMS_NET_CLASS = ".itemsNet";
    private final String TITLE_CLASS = ".title";
    private final String ANCHOR = "a";
    private final String YEAR_CLASS = ".year ";
    private final String HREF_ATTRIBUTE = "href";

    private Document document;
    private String baseUrl;

    public CalendRuParser(@Value("${parser.baseUrl}") String url) {
        this.baseUrl = url;
        loadDocument();
        getEvents();
    }

    public List<EventEntity> getEvents() {
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

        return events;
    }

    private void loadDocument() {
        String url = formatUrl();
        logger.debug(url);

        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new ParseException("Unable to load page for parsing");
        }
    }

    private String formatUrl() {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        StringBuilder url = new StringBuilder()
                .append(baseUrl)
                .append(month)
                .append(STRING_DASH)
                .append(day);

        return url.toString();
    }
}
