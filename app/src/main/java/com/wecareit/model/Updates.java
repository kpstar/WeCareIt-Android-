package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class Updates extends BaseModel {

    @SerializedName("action")
    private String action;

    @SerializedName("action_time")
    private String action_time;

    @SerializedName("edited_object")
    private EditedObject edited_object;

    @SerializedName("type")
    private String type;

    @SerializedName("user")
    private AuthorRes user;

    public Updates(String action, String action_time, EditedObject edited_object, String type, AuthorRes user){
        this.action = action;
        this.action_time = action_time;
        this.edited_object = edited_object;
        this.type = type;
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction_time() {
        return action_time;
    }

    public void setAction_time(String action_time) {
        this.action_time = action_time;
    }

    public EditedObject getEdited_object() {
        return edited_object;
    }

    public void setEdited_object(EditedObject edited_object) {
        this.edited_object = edited_object;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AuthorRes getUser() {
        return user;
    }

    public void setUser(AuthorRes user) {
        this.user = user;
    }
}
