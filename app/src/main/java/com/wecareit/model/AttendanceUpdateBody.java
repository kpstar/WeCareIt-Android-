package com.wecareit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AttendanceUpdateBody extends BaseModel {
    @SerializedName("clients")
    private ArrayList<AttendedClient> attendedClients;

    @SerializedName("submitted")
    private boolean submitted;

    public ArrayList<AttendedClient> getAttendedClients() {
        return attendedClients;
    }

    public void setAttendedClients(ArrayList<AttendedClient> attendedClients) {
        this.attendedClients = attendedClients;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public AttendanceUpdateBody(ArrayList<AttendedClient> attendedClients, boolean submitted) {
        this.attendedClients = attendedClients;
        this.submitted = submitted;
    }
}
