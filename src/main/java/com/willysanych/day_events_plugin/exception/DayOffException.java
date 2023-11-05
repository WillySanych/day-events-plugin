package com.willysanych.day_events_plugin.exception;

public class DayOffException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DayOffException(String message) {
        super(message);
    }
}
