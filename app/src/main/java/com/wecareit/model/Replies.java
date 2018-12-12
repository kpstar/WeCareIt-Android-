package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Replies extends BaseModel {

    @SerializedName("author")
    private Author author;

    @SerializedName("creation_date")
    private String creation_date;

    @SerializedName("id")
    private int id;

    @SerializedName("message")
    private String message;

    @SerializedName("mentioned_users")
    private String mentioned_users;

    @SerializedName("seen_by")
    private Author seen_by;

    public Replies(Author author, String creation_date, int id, String message, String mentioned_users, Author seen_by) {
        this.author = author;
        this.creation_date = creation_date;
        this.id = id;
        this.message = message;
        this.mentioned_users = mentioned_users;
        this.seen_by = seen_by;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
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

    public String getMentioned_users() {
        return mentioned_users;
    }

    public void setMentioned_users(String mentioned_users) {
        this.mentioned_users = mentioned_users;
    }

    public Author getSeen_by() {
        return seen_by;
    }

    public void setSeen_by(Author seen_by) {
        this.seen_by = seen_by;
    }
}
