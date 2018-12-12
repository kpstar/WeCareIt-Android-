package com.wecareit.model;

import com.google.gson.Gson;

public class BaseModel {
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
