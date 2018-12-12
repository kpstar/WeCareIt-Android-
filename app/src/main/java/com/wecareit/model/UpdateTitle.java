package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UpdateTitle extends BaseModel {

    @SerializedName("id")
    private int id;

    @SerializedName("Message")
    private String Message;

    @SerializedName("mentioned_users")
    private ArrayList<Integer> mentioned_users;

    @SerializedName("me_mentioned")
    private String me_mentioned;


    public UpdateTitle( int id, String Message, ArrayList<Integer> mentioned_users, String me_mentioned ){

        this.id = id;
        this.Message = Message;
        this.mentioned_users = mentioned_users;
        this.me_mentioned = me_mentioned;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public ArrayList<Integer> getMentioned_users() {
        return mentioned_users;
    }

    public void setMentioned_users(ArrayList<Integer> mentioned_users) {
        this.mentioned_users = mentioned_users;
    }

    public String getMe_mentioned() {
        return me_mentioned;
    }

    public void setMe_mentioned(String me_mentioned) {
        this.me_mentioned = me_mentioned;
    }
}
