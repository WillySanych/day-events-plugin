package com.willysanych.day_events_plugin.entity;

public class EventEntity {

    public EventEntity() {
        super();
    }

    private String title;
    private String link;
    private String year;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "EventEntity [title=" + title + ", link=" + link + ", year=" + year + "]";
    }
}
