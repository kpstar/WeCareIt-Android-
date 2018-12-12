package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class Vehicle extends BaseModel {
    @SerializedName("address_start")
    private String addresssstart;

    @SerializedName("brand")
    private String brand;

    @SerializedName("icon")
    private String icon;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("odometer")
    private String odometer;

    @SerializedName("regno")
    private String regno;

    public Vehicle(String addresssstart, String brand, String icon, int id, String name, String odometer, String regno) {
        this.addresssstart = addresssstart;
        this.brand = brand;
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.odometer = odometer;
        this.regno = regno;
    }

    public String getAddresssstart() {
        return addresssstart;
    }

    public void setAddresssstart(String addresssstart) {
        this.addresssstart = addresssstart;
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
    }

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

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }
}
