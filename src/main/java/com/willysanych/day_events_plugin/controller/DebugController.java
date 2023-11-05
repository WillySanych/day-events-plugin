package com.willysanych.day_events_plugin.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.willysanych.day_events_plugin.config.DebugConfig;
import com.willysanych.day_events_plugin.service.sender.EventsSenderService;

@RestController
@RequestMapping("api/debug")
@ConditionalOnBean(DebugConfig.class)
public class DebugController {

    private final EventsSenderService eventsSenderService;

    public DebugController(EventsSenderService eventsSenderService) {
        this.eventsSenderService = eventsSenderService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendEventsToKatya() {
        eventsSenderService.sendEventsToKatya();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
