package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class LogoutResponse extends BaseModel {
    @SerializedName("detail")
    private String detail;

    public LogoutResponse(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
