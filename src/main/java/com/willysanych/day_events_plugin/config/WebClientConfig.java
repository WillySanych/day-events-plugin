package com.willysanych.day_events_plugin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.willysanych.day_events_plugin.sender.EventsSenderClient;

@Configuration
public class WebClientConfig {

    private final String TOKEN = "Token ";

    @Value("${katya.url}")
    private String baseKatyaUrl;

    @Value("${katya.token}")
    private String token;

    @Bean
    EventsSenderClient eventsSender() {
        WebClient client = WebClient.builder()
                .baseUrl(baseKatyaUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, TOKEN + token)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client))
                .build();

        return factory.createClient(EventsSenderClient.class);
    }
}
