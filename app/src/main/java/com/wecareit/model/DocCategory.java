package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DocCategory extends BaseModel {

    @SerializedName("id")
    private int id;

    @SerializedName("documents")
    private ArrayList<Document> documents;

    @SerializedName("name")
    private String name;

    public DocCategory(int id, ArrayList<Document> documents, String name) {
        this.id = id;
        this.documents = documents;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
