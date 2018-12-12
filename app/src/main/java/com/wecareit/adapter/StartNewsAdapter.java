package com.wecareit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecareit.R;
import com.wecareit.model.NewsResponse;
import com.wecareit.viewholder.NewsViewHolder;
import com.wecareit.viewholder.StartNewsViewHolder;

import java.util.ArrayList;

public class StartNewsAdapter extends RecyclerView.Adapter<StartNewsViewHolder> {
    private Context context;
    private ArrayList<NewsResponse> news;

    public StartNewsAdapter(Context context, ArrayList<NewsResponse> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public StartNewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_start, viewGroup, false);
        return new StartNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StartNewsViewHolder startNewsViewHolder, int i) {
        startNewsViewHolder.setContent(news.get(i));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
