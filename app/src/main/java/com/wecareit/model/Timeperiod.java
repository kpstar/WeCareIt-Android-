package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class Timeperiod extends BaseModel {
    @SerializedName("lower")
    private String lower;

    @SerializedName("upper")
    private String upper;

    public Timeperiod (String lower, String upper) {
        this.lower = lower;
        this.upper = upper;
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
