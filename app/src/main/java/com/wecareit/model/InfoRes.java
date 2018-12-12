package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class InfoRes {

    @SerializedName("author")
    private AuthorRes author;

    @SerializedName("creation_date")
    private String creation_date;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("text")
    private String text;

    public InfoRes(AuthorRes author, String creation_date, int id, String name, String text){
        this.author = author;
        this.creation_date = creation_date;
        this.id = id;
        this.name = name;
        this.text = text;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
