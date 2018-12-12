package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class Vehicles extends BaseModel {

    /*@SerializedName("address_start")
    private String address_start;

    @SerializedName("brand")
    private String brand;

    @SerializedName("icon")
    private String icon;*/

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    /*@SerializedName("odometer")
    private String odometer;*/

    @SerializedName("regno")
    private String regno;

    public Vehicles(int id, String name, String regno) {
        /*this.address_start = address_start;
        this.brand = brand;
        this.icon = icon;*/
        this.id = id;
        this.name = name;
        //this.odometer = odometer;
        this.regno = regno;
    }

    /*public String getAddress_start() {
        return address_start;
    }

    public void setAddress_start(String address_start) {
        this.address_start = address_start;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }*/

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }
}
