package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventsRes extends BaseModel {

    @SerializedName("time")
    private TimeRes time;

    @SerializedName("id")
    private int id;

    @SerializedName("description")
    private String description;

    @SerializedName("name")
    private String name;

    @SerializedName("creation_date")
    private String creation_date;

    @SerializedName("users")
    private ArrayList<AuthorRes> users;

    @SerializedName("vehicles")
    private ArrayList<Vehicles> vehicles;

    @SerializedName("clients")
    private ArrayList<Client> clients;

    public EventsRes(TimeRes time, int id, String description, String name, String creation_date, ArrayList<AuthorRes> users, ArrayList<Vehicles> vehicles, ArrayList<Client> clients){
        this.time = time;
        this.id = id;
        this.description = description;
        this.name = name;
        this.creation_date = creation_date;
        this.users = users;
        this.vehicles = vehicles;
        this.clients = clients;
    }

    public TimeRes getTime() {
        return time;
    }

    public void setTime(TimeRes time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<AuthorRes> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<AuthorRes> users) {
        this.users = users;
    }

    public ArrayList<Vehicles> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicles> vehicles) {
        this.vehicles = vehicles;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }
}
