package com.wecareit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecareit.R;
import com.wecareit.model.Tasks;
import com.wecareit.viewholder.StartTasksViewHolder;

import java.util.ArrayList;

public class StartTasksAdapter extends RecyclerView.Adapter<StartTasksViewHolder> {
    private Context context;
    private ArrayList<Tasks> tasks;

    public StartTasksAdapter(Context context, ArrayList<Tasks> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public StartTasksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_start, viewGroup, false);
        return new StartTasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StartTasksViewHolder newsViewHolder, int i) {
        newsViewHolder.setContent(tasks.get(i));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
