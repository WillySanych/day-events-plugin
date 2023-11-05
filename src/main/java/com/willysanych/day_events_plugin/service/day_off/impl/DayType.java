package com.willysanych.day_events_plugin.service.day_off.impl;

public enum DayType {
    WORK_DAY(0),
    NON_WORKING_DAY(1),
    UNKNOWN(-1);

    private Integer code;

    DayType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static DayType getByCode(Integer code) {
        if (code == null) {
            return UNKNOWN;
        }

        for (DayType value : DayType.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }

        return UNKNOWN;
    }
}
