package com.wecareit.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecareit.R;
import com.wecareit.model.Tasks;
import com.wecareit.viewholder.TasksViewHolder;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksViewHolder> {

    private Context context;
    private ArrayList<Tasks> news;

    public TasksAdapter(Context context, ArrayList<Tasks> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_tasks, viewGroup, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder newsViewHolder, int i) {
        newsViewHolder.setContent(news.get(i));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
