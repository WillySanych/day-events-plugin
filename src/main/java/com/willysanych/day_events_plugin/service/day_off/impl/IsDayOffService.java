package com.willysanych.day_events_plugin.service.day_off.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.willysanych.day_events_plugin.exception.DayOffException;
import com.willysanych.day_events_plugin.service.day_off.DayOffClient;
import com.willysanych.day_events_plugin.service.day_off.DayOffService;

@Service
public class IsDayOffService implements DayOffService {

    @Value("${day-off.timezone}")
    private String timezoneParam;
    @Value("${day-off.country}")
    private String countryParam;

    private final DayOffClient dayOffClient;

    public IsDayOffService(DayOffClient dayOffClient) {
        this.dayOffClient = dayOffClient;
    }

    @Override
    public boolean checkDayOffToday() {
        String dayCode = dayOffClient.checkDayOffToday(timezoneParam, countryParam);
        DayType type = DayType.UNKNOWN;

        try {
            type = DayType.getByCode(Integer.valueOf(dayCode));
        } catch (NumberFormatException e) {
            throw new DayOffException("Unable to parse day code from external service");
        }

        switch (type) {
        case WORK_DAY:
            return false;
        case NON_WORKING_DAY:
            return true;
        default:
            throw new DayOffException("Unable to define is day off");
        }
    }

}
