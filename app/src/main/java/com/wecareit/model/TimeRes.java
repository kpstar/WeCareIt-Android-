package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class TimeRes extends BaseModel {

    @SerializedName("bounds")
    private String bounds;

    @SerializedName("lower")
    private String lower;

    @SerializedName("upper")
    private String upper;

    public TimeRes(String bounds, String lower, String upper){
        this.bounds = bounds;
        this.lower = lower;
        this.upper = upper;
    }

    public String getBounds() {
        return bounds;
    }

    public void setBounds(String bounds) {
        this.bounds = bounds;
    }

    public String getLower() {
        return lower;
    }

    public void setLower(String lower) {
        this.lower = lower;
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }
}
