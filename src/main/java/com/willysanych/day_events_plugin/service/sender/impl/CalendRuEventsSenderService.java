package com.willysanych.day_events_plugin.service.sender.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.willysanych.day_events_plugin.dto.EventDto;
import com.willysanych.day_events_plugin.service.converter.EventsConverter;
import com.willysanych.day_events_plugin.service.day_off.DayOffService;
import com.willysanych.day_events_plugin.service.sender.EventsSenderClient;
import com.willysanych.day_events_plugin.service.sender.EventsSenderService;

@Service
public class CalendRuEventsSenderService implements EventsSenderService {

    Logger logger = LoggerFactory.getLogger(CalendRuEventsSenderService.class);

    private final EventsConverter eventsConverter;
    private final EventsSenderClient eventsSender;
    private final DayOffService dayOffService;

    @Value("${katya.message-type}")
    private String messageType;

    @Value("#{'${katya.chats}'.split(',')}")
    private List<String> chats;

    public CalendRuEventsSenderService(EventsConverter eventsConverter, EventsSenderClient eventsSender, DayOffService dayOffService) {
        this.eventsConverter = eventsConverter;
        this.eventsSender = eventsSender;
        this.dayOffService = dayOffService;
    }

    @Scheduled(cron = "${scheduler.send-events.cron}", zone = "${scheduler.timezone}")
    @Override
    public void sendEventsToKatya() {
        if (dayOffService.checkDayOffToday()) {
            return;
        }

        logger.debug("Sending to chats:" + chats.toString());
        for (String chat : chats) {
            EventDto dto = getEventDto(chat);

            eventsSender.sendEvents(dto);
            logger.debug("Successfully sent to chat: " + chat);
        }
        logger.debug("Sending to all chats completed");
    }

    private EventDto getEventDto(String chat) {
        EventDto dto = new EventDto();

        dto.setMessage(eventsConverter.getMessage());
        dto.setType(messageType);
        dto.setChatName(chat);

        return dto;
    }
}
