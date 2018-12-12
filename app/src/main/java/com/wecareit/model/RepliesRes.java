package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RepliesRes extends BaseModel {

    @SerializedName("author")
    private AuthorRes author;

    @SerializedName("creation_date")
    private String creation_date;

    @SerializedName("id")
    private int id;

    @SerializedName("message")
    private String message;

    @SerializedName("mentioned_users")
    private ArrayList<Mentioned_users> mentioned_users;

    @SerializedName("seen_by")
    private ArrayList<Seen_by> seen_by;

    public RepliesRes(AuthorRes author, String creation_date, int id, String message, ArrayList<Mentioned_users> mentioned_users, ArrayList<Seen_by> seen_by){
        this.author = author;
        this.creation_date = creation_date;
        this.id = id;
        this.message = message;
        this.mentioned_users = mentioned_users;
        this.seen_by = seen_by;
    }

    public AuthorRes getAuthor() {
        return author;
    }

    public void setAuthor(AuthorRes author) {
        this.author = author;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Mentioned_users> getMentioned_users() {
        return mentioned_users;
    }

    public void setMentioned_users(ArrayList<Mentioned_users> mentioned_users) {
        this.mentioned_users = mentioned_users;
    }

    public ArrayList<Seen_by> getSeen_by() {
        return seen_by;
    }

    public void setSeen_by(ArrayList<Seen_by> seen_by) {
        this.seen_by = seen_by;
    }

}
