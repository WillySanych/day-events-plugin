package com.willysanych.day_events_plugin.util;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.willysanych.day_events_plugin.util.locale.LocaleUtil;

@Component
public class MessageUtil {

    private final MessageSource messageSource;
    private final LocaleUtil localeUtil;

    public MessageUtil(MessageSource messageSource, LocaleUtil localeUtil) {
        this.messageSource = messageSource;
        this.localeUtil = localeUtil;
    }

    public String getLocalizedMessage(String key, Object[] args) {
        return messageSource.getMessage(key, args, localeUtil.getLocale());
    }
}
