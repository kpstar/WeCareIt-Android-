package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotesRes extends BaseModel {

    @SerializedName("area")
    private Area area;

    @SerializedName("author")
    private AuthorRes author;

    @SerializedName("backdated")
    private boolean backdated;

    @SerializedName("clients")
    private ArrayList<Client> clients;

    @SerializedName("creation_date")
    private String creation_date;

    @SerializedName("id")
    private int id;

    @SerializedName("main_category")
    private Main_Category main_category;

    @SerializedName("major_keyword")
    private Major_Keyword major_keyword;

    @SerializedName("minor_keyword")
    private Minor_Keywords minor_keyword;

    @SerializedName("striked")
    private boolean striked;

    @SerializedName("summary")
    private String summary;

    @SerializedName("text")
    private String text;

    public NotesRes(Area area, AuthorRes author, boolean backdated, ArrayList<Client> clients, String creation_date, int id,
    Main_Category main_category, Major_Keyword major_keyword, Minor_Keywords minor_keyword, boolean striked, String summary, String text) {
        this.area = area;
        this.author = author;
        this.backdated = backdated;
        this.clients = clients;
        this.creation_date = creation_date;
        this.id = id;
        this.main_category = main_category;
        this.major_keyword = major_keyword;
        this.minor_keyword = minor_keyword;
        this.striked = striked;
        this.summary = summary;
        this.text = text;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public AuthorRes getAuthor() {
        return author;
    }

    public void setAuthor(AuthorRes author) {
        this.author = author;
    }

    public boolean isBackdated() {
        return backdated;
    }

    public void setBackdated(boolean backdated) {
        this.backdated = backdated;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
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

    public Main_Category getMain_category() {
        return main_category;
    }

    public void setMain_category(Main_Category main_category) {
        this.main_category = main_category;
    }

    public Major_Keyword getMajor_keyword() {
        return major_keyword;
    }

    public void setMajor_keyword(Major_Keyword major_keyword) {
        this.major_keyword = major_keyword;
    }

    public Minor_Keywords getMinor_keyword() {
        return minor_keyword;
    }

    public void setMinor_keyword(Minor_Keywords minor_keyword) {
        this.minor_keyword = minor_keyword;
    }

    public boolean isStriked() {
        return striked;
    }

    public void setStriked(boolean striked) {
        this.striked = striked;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
