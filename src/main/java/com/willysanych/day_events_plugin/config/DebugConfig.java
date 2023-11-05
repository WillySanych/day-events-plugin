package com.willysanych.day_events_plugin.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "debug-mode")
public class DebugConfig {

}
