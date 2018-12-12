package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class Area extends BaseModel {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    public Area (int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
