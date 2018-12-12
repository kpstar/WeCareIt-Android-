package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class AssignedTo extends BaseModel {

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("title")
    private String title;

    public AssignedTo(String avatar, int id, String name, String title){
        this.avatar = avatar;
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
