package com.wecareit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecareit.R;
import com.wecareit.model.EventsRes;
import com.wecareit.viewholder.StartEventsViewHolder;

import java.util.ArrayList;

public class StartEventsAdapter extends RecyclerView.Adapter<StartEventsViewHolder> {
    private Context context;
    private ArrayList<EventsRes> news;

    public StartEventsAdapter(Context context, ArrayList<EventsRes> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public StartEventsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_start, viewGroup, false);
        return new StartEventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StartEventsViewHolder startNewsViewHolder, int i) {
        startNewsViewHolder.setContent(news.get(i));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
