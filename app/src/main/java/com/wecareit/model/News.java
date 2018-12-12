package com.wecareit.model;

import com.google.gson.annotations.SerializedName;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class News extends BaseModel {

    @SerializedName("message")
    private String message;

    @SerializedName("mentioned_users")
    private ArrayList<Integer> mentioned_users;


    public News( String message, ArrayList<Integer> mentioned_users ){

        this.message = message;
        this.mentioned_users = mentioned_users;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Integer> getMentioned_users() {
        return mentioned_users;
    }

    public void setMentioned_users(ArrayList<Integer> mentioned_users) {
        this.mentioned_users = mentioned_users;
    }
}
