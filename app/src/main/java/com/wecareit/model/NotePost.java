package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NotePost extends BaseModel {

    @SerializedName("area")
    private int area;

    @SerializedName("clients")
    private List<String> clients;

    @SerializedName("main_category")
    private int main_category;

    @SerializedName("major_keyword")
    private int major_keyword;

    @SerializedName("minor_keyword")
    private int minor_keyword;

    @SerializedName("summary")
    private String summary;

    @SerializedName("text")
    private String text;

    @SerializedName("backdated")
    private boolean backdated;


    public NotePost(int area, List<String> clients, int main_category, int major_keyword, int minor_keyword, String summary, String text, boolean backdated) {
        this.area = area;
        this.clients = clients;
        this.main_category = main_category;
        this.major_keyword = major_keyword;
        this.minor_keyword = minor_keyword;
        this.summary = summary;
        this.text = text;
        this.backdated = backdated;
    }

    public List<String> getClients() {
        return clients;
    }

    public int getArea() {
        return area;
    }

    public int getMain_category() {
        return main_category;
    }

    public int getMajor_keyword() {
        return major_keyword;
    }

    public int getMinor_keyword() {
        return minor_keyword;
    }

    public String getSummary() {
        return summary;
    }

    public String getText() {
        return text;
    }

}
