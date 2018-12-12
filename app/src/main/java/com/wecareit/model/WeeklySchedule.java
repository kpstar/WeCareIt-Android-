package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeeklySchedule extends BaseModel {
    @SerializedName("time")
    private ScheduleTime time;

    @SerializedName("id")
    private int id;

    @SerializedName("description")
    private String description;

    @SerializedName("name")
    private String name;

    @SerializedName("creation_date")
    private String creationdate;

    @SerializedName("users")
    private ArrayList<User> users;

    @SerializedName("clients")
    private ArrayList<Client> clients;
}
