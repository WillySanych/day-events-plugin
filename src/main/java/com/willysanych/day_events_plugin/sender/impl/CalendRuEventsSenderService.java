package com.willysanych.day_events_plugin.sender.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.willysanych.day_events_plugin.converter.EventsConverter;
import com.willysanych.day_events_plugin.dto.EventDto;
import com.willysanych.day_events_plugin.sender.EventsSenderClient;
import com.willysanych.day_events_plugin.sender.EventsSenderService;

@Service
public class CalendRuEventsSenderService implements EventsSenderService {

    Logger logger = LoggerFactory.getLogger(CalendRuEventsSenderService.class);

    private final EventsConverter eventsConverter;
    private final EventsSenderClient eventsSender;

    @Value("${katya.message-type}")
    private String messageType;

    @Value("#{'${katya.chats}'.split(',')}")
    private List<String> chats;

    public CalendRuEventsSenderService(EventsConverter eventsConverter, EventsSenderClient eventsSender) {
        this.eventsConverter = eventsConverter;
        this.eventsSender = eventsSender;
    }

    //TODO make scheduled
    @Override
    public void sendEventsToKatya() {
        logger.debug("Sending to chats:" + chats.toString());
        for (String chat : chats) {
            EventDto dto = getEventDto(chat);

            eventsSender.sendEvents(dto);
        }
        logger.debug("Sending completed");
    }

    private EventDto getEventDto(String chat) {
        EventDto dto = new EventDto();

        dto.setMessage(eventsConverter.getMessage());
        dto.setType(messageType);
        dto.setChatName(chat);

        return dto;
    }
}
