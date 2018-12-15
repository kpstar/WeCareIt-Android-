package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class MonthlyKeyword extends BaseModel {

    @SerializedName("id")
    private int id;

    @SerializedName("label")
    private String label;

    public MonthlyKeyword (int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setTitle(String title) {
        this.label = title;
    }
}
