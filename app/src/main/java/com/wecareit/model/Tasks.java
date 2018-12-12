package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

public class Tasks extends BaseModel {

    @SerializedName("activity")
    private String activity;

    @SerializedName("assigned_to")
    private AssignedTo assigned_to;

    @SerializedName("deadline_date")
    private String deadline_date;

    @SerializedName("status")
    private String status;
    public Tasks(String activity, AssignedTo assigned_to, String deadline_date, String status){
        this.activity = activity;
        this.assigned_to = assigned_to;
        this.deadline_date =deadline_date;
        this.status = status;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public AssignedTo getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(AssignedTo assigned_to) {
        this.assigned_to = assigned_to;
    }

    public String getDeadline_date() {
        return deadline_date;
    }

    public void setDeadline_date(String deadline_date) {
        this.deadline_date = deadline_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
