package com.willysanych.day_events_plugin.util.locale.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.willysanych.day_events_plugin.util.locale.LocaleUtil;

@Component
public class PropertyLocaleUtil implements LocaleUtil {
    private final Locale locale;

    public PropertyLocaleUtil(@Value("${locale}") String localeName) {
        this.locale = new Locale(localeName);
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
