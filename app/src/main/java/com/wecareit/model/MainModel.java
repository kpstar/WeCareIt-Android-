package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainModel extends BaseModel {

    @SerializedName("news")
    private ArrayList<NewsResponse> news;

    @SerializedName("events")
    private ArrayList<EventsRes> events;

    @SerializedName("tasks")
    private ArrayList<Tasks> tasks;

    @SerializedName("updates")
    private ArrayList<Updates> updates;

    public MainModel(ArrayList<NewsResponse> news, ArrayList<EventsRes> events, ArrayList<Tasks> tasks, ArrayList<Updates> updates) {
        this.news = news;
        this.events = events;
        this.tasks = tasks;
        this.updates = updates;
    }

    public ArrayList<NewsResponse> getNews() {
        return news;
    }

    public void setNews(ArrayList<NewsResponse> news) {
        this.news = news;
    }

    public ArrayList<EventsRes> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<EventsRes> events) {
        this.events = events;
    }

    public ArrayList<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Tasks> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Updates> getUpdates() {
        return updates;
    }

    public void setUpdates(ArrayList<Updates> updates) {
        this.updates = updates;
    }
}
