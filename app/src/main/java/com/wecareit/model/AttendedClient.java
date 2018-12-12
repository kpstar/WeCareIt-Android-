package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class AttendedClient extends BaseModel {

    @SerializedName("id")
    private int id;

    @SerializedName("hours")
    private int hours;

    @SerializedName("client")
    private Client client;

    public AttendedClient(int id, int hours, Client client) {
        this.id = id;
        this.hours = hours;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
