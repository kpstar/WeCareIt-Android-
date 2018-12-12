package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsPost extends BaseModel {

    @SerializedName("message")
    private String message;

    @SerializedName("mentioned_users")
    private ArrayList<Userlist> mentioned_users;


    public NewsPost( String message, ArrayList<Userlist> mentioned_users ){

        this.message = message;
        this.mentioned_users = mentioned_users;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Userlist> getMentioned_users() {
        return mentioned_users;
    }

    public void setMentioned_users(ArrayList<Userlist> mentioned_users) {
        this.mentioned_users = mentioned_users;
    }

}
