package com.wecareit.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Attendance extends BaseModel {
    @SerializedName("clients")
    private ArrayList<AttendedClient> attendedClients;

    @SerializedName("submitted")
    private boolean submitted;

    public ArrayList<AttendedClient> getAttendedClients() {
        return this.attendedClients;
    }

    public void setClients(ArrayList<AttendedClient> attendedClients) {
        this.attendedClients = attendedClients;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public Attendance(ArrayList<AttendedClient> attendedClients, boolean submitted) {
        this.attendedClients = attendedClients;
        this.submitted = submitted;
    }


}
