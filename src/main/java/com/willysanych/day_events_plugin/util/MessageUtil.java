package com.willysanych.day_events_plugin.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageUtil {

    private final MessageSource messageSource;
    private final Locale locale;

    public MessageUtil(MessageSource messageSource, @Value("${locale}") String localeName) {
        this.messageSource = messageSource;
        this.locale = new Locale(localeName);
    }

    public String getLocalizedMessage(String key, Object[] args) {
        return messageSource.getMessage(key, args, locale);
    }
}
