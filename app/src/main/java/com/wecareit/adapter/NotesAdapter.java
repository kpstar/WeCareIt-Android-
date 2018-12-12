package com.wecareit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecareit.R;
import com.wecareit.model.NotesRes;
import com.wecareit.viewholder.NotesViewHolder;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    private Context context;
    private ArrayList<NotesRes> notes;

    public NotesAdapter(Context context, ArrayList<NotesRes> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_notes, viewGroup, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder newsViewHolder, int i) {
        newsViewHolder.setContent(notes.get(i));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
