package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CompanyCarPost extends BaseModel {

    @SerializedName("address_end")
    private String address_end;

    @SerializedName("address_start")
    private String address_start;

    @SerializedName("clients")
    private ArrayList<Integer> clients;

    @SerializedName("category")
    private int category;

    @SerializedName("odometer_start")
    private String odometer_start;

    @SerializedName("odometer_end")
    private String odometer_end;

    @SerializedName("vehicle")
    private int vehicle;

    public CompanyCarPost (String address_end, String address_start, ArrayList<Integer> clients, int category, String odometer_start, String odometer_end, int vehicle) {
        this.address_end = address_end;
        this.address_start = address_start;
        this.clients = clients;
        this.category = category;
        this.odometer_start = odometer_start;
        this.odometer_end = odometer_end;
        this.vehicle = vehicle;
    }

    public String getAddress_end() {
        return address_end;
    }

    public void setAddress_end(String address_end) {
        this.address_end = address_end;
    }

    public String getAddress_start() {
        return address_start;
    }

    public void setAddress_start(String address_start) {
        this.address_start = address_start;
    }

    public ArrayList<Integer> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Integer> clients) {
        this.clients = clients;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getOdometer_start() {
        return odometer_start;
    }

    public void setOdometer_start(String odometer_start) {
        this.odometer_start = odometer_start;
    }

    public String getOdometer_end() {
        return odometer_end;
    }

    public void setOdometer_end(String odometer_end) {
        this.odometer_end = odometer_end;
    }

    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }
}
