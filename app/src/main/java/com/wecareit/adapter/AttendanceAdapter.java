package com.wecareit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wecareit.R;
import com.wecareit.common.Message;
import com.wecareit.model.AttendedClient;
import com.wecareit.model.Client;
import com.wecareit.viewholder.AttendanceViewHolder;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceViewHolder> {
    private Context context;
    private ArrayList<AttendedClient> attendedClients;
    private boolean submitted = false;

    public AttendanceAdapter(Context context, ArrayList<AttendedClient> attendedClients) {
        this.context = context;
        this.attendedClients = attendedClients;
    }

    public AttendanceAdapter(ArrayList<AttendedClient> attendedClients) {
        this.attendedClients = attendedClients;
    }

    public ArrayList<Client> getClients() {
        ArrayList<Client> clients = new ArrayList<Client>();
        for (AttendedClient attendedClient : attendedClients) {
            clients.add(attendedClient.getClient());
        }
        return clients;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_attendance, viewGroup, false);

        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder attendanceViewHolder, int i) {
        attendanceViewHolder.setContent(attendedClients.get(i), submitted);
    }

    @Override
    public int getItemCount() {
        return attendedClients.size();
    }

    public ArrayList<AttendedClient> getAttendedClients() {
        return attendedClients;
    }
}
