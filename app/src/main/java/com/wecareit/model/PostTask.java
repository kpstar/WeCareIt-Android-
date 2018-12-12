package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PostTask extends BaseModel {

    @SerializedName("activity")
    private String activity;

    @SerializedName("assigned_users")
    private ArrayList<Integer> assigned_users;

    @SerializedName("deadline_date")
    private String deadline_date;

    public PostTask(String activity, ArrayList<Integer> assigned_users, String deadline_date) {
        this.activity = activity;
        this.assigned_users = assigned_users;
        this.deadline_date = deadline_date;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public ArrayList<Integer> getAssigned_users() {
        return assigned_users;
    }

    public void setAssigned_users(ArrayList<Integer> assigned_users) {
        this.assigned_users = assigned_users;
    }

    public String getDeadline_date() {
        return deadline_date;
    }

    public void setDeadline_date(String deadline_date) {
        this.deadline_date = deadline_date;
    }
}
