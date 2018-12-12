package com.wecareit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecareit.R;
import com.wecareit.model.EventsRes;
import com.wecareit.viewholder.EventsViewHolder;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsViewHolder> {
    private Context context;
    private ArrayList<EventsRes> news;

    public EventsAdapter(Context context, ArrayList<EventsRes> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_events, viewGroup, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder newsViewHolder, int i) {
        newsViewHolder.setContent(news.get(i));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
