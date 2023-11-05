package com.willysanych.day_events_plugin.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.willysanych.day_events_plugin.config.DebugConfig;
import com.willysanych.day_events_plugin.service.day_off.DayOffService;
import com.willysanych.day_events_plugin.service.sender.EventsSenderService;

@RestController
@RequestMapping("api/debug")
@ConditionalOnBean(DebugConfig.class)
public class DebugController {

    private final EventsSenderService eventsSenderService;
    private final DayOffService dayOffService;

    public DebugController(EventsSenderService eventsSenderService, DayOffService dayOffService) {
        this.eventsSenderService = eventsSenderService;
        this.dayOffService = dayOffService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendEventsToKatya() {
        eventsSenderService.sendEventsToKatya();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/check-day-off")
    public ResponseEntity<Boolean> checkDayOff() {
        boolean isDayOff = dayOffService.checkDayOffToday();
        return new ResponseEntity<Boolean>(isDayOff, HttpStatus.OK);
    }
}
