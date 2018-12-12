package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class Document extends BaseModel {

    @SerializedName("filename")
    private String filename;

    @SerializedName("date")
    private String date;

    @SerializedName("top_five")
    private boolean topfive;

    @SerializedName("url")
    private String url;


    public Document(String filename, String date, boolean topfive, String url) {
        this.filename = filename;
        this.date = date;
        this.topfive = topfive;
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isTopfive() {
        return topfive;
    }

    public void setTopfive(boolean topfive) {
        this.topfive = topfive;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
