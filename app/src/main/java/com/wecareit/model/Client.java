package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class Client extends BaseModel {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public Client(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
