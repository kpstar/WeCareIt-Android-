package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class Author extends BaseModel {
    @SerializedName("avatar")
    private String avatar;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("title")
    private String title;

}
