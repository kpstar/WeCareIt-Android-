package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseModel {
    @SerializedName("token")
    private String key;

    @SerializedName("user")
    private User user;

    public LoginResponse(String key, User user) {
        this.key = key;
        this.user = user;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
