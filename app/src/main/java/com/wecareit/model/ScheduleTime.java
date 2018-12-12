package com.wecareit.model;

import com.google.gson.annotations.SerializedName;
import com.wecareit.model.BaseModel;

public class ScheduleTime extends BaseModel {
    @SerializedName("upper")
    private String upper;

    @SerializedName("bounds")
    private String bounds;

    @SerializedName("lower")
    private String lower;

    public ScheduleTime(String upper, String bounds, String lower) {
        this.upper = upper;
        this.bounds = bounds;
        this.lower = lower;
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
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
}
