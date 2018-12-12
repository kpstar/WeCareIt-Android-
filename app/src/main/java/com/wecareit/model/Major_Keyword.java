package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Major_Keyword extends BaseModel {

    @SerializedName("id")
    private int id;

    @SerializedName("minor_keywords")
    private ArrayList<Minor_Keywords> minor_keywords;

    @SerializedName("title")
    private String title;

    public Major_Keyword(int id, ArrayList<Minor_Keywords> minor_keywords, String title) {
        this.id = id;
        this.minor_keywords = minor_keywords;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Minor_Keywords> getMinor_keywords() {
        return minor_keywords;
    }

    public void setMinor_keywords(ArrayList<Minor_Keywords> minor_keywords) {
        this.minor_keywords = minor_keywords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
