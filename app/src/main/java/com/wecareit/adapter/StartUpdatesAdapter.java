package com.wecareit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecareit.R;
import com.wecareit.model.Updates;
import com.wecareit.viewholder.StartUpdatesViewHolder;

import java.util.ArrayList;

public class StartUpdatesAdapter extends RecyclerView.Adapter<StartUpdatesViewHolder> {
    private Context context;
    private ArrayList<Updates> updates;

    public StartUpdatesAdapter(Context context, ArrayList<Updates> updates) {
        this.context = context;
        this.updates = updates;
    }

    @NonNull
    @Override
    public StartUpdatesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_start, viewGroup, false);
        return new StartUpdatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StartUpdatesViewHolder newsViewHolder, int i) {
        newsViewHolder.setContent(updates.get(i));
    }

    @Override
    public int getItemCount() {
        return updates.size();
    }
}
