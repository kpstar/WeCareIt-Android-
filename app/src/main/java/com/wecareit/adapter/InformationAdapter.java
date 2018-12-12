package com.wecareit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecareit.R;
import com.wecareit.fragments.information.InformationFragment;
import com.wecareit.model.InfoRes;
import com.wecareit.viewholder.InformationViewHolder;

import java.util.ArrayList;

public class InformationAdapter extends RecyclerView.Adapter<InformationViewHolder> {

    private Context context;
    private ArrayList<InfoRes> news;

    public InformationAdapter(Context context, ArrayList<InfoRes> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_information, viewGroup, false);
        return new InformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder newsViewHolder, int i) {
        newsViewHolder.setContent(news.get(i));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
