package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventspostBody extends BaseModel {

    @SerializedName("time")
    private Object time;

    @SerializedName("description")
    private String description;

    @SerializedName("name")
    private String name;

    @SerializedName("users")
    private ArrayList<Integer> users;

    @SerializedName("vehicles")
    private ArrayList<Integer> vehicles;

    @SerializedName("clients")
    private ArrayList<Integer> clients;


    public EventspostBody(Object time, String description, String name, ArrayList<Integer> users, ArrayList<Integer> vehicles, ArrayList<Integer> clients) {
        this.time = time;
        this.description = description;
        this.name = name;
        this.clients = clients;
        this.users = users;
        this.vehicles = vehicles;
    }

    public ArrayList<Integer> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Integer> clients) {
        this.clients = clients;
    }

    public ArrayList<Integer> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Integer> users) {
        this.users = users;
    }

    public ArrayList<Integer> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Integer> vehicles) {
        this.vehicles = vehicles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    public Object getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
