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
        Collections.sort(this.attendedClients, new Comparator<AttendedClient>() {
            @Override
            public int compare(AttendedClient a, AttendedClient b) {
                return (a.getId() >  b.getId() ? 1: -1);
            }
        });
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
