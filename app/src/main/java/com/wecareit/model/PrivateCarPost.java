package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PrivateCarPost extends BaseModel {

    @SerializedName("address_end")
    private String address_end;

    @SerializedName("address_start")
    private String address_start;

    @SerializedName("clients")
    private ArrayList<Integer> clients;

    @SerializedName("category")
    private int category;

    @SerializedName("date")
    private String date ;

    @SerializedName("km")
    private String km;

    @SerializedName("regno")
    private String regno;

    public PrivateCarPost (String address_end, String address_start, ArrayList<Integer> clients, int category, String date, String km, String regno) {
        this.address_end = address_end;
        this.address_start = address_start;
        this.clients = clients;
        this.category = category;
        this.date = date;
        this.km = km;
        this.regno = regno;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }
}
