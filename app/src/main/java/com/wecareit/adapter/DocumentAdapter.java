package com.wecareit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecareit.R;
import com.wecareit.model.Document;
import com.wecareit.viewholder.AttendanceViewHolder;
import com.wecareit.viewholder.DocumentViewHolder;

import java.util.ArrayList;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentViewHolder> {
    private Context context;
    private ArrayList<Document> documents;

    public DocumentAdapter(Context context, ArrayList<Document> documents) {
        this.context = context;
        this.documents = documents;
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_document, viewGroup, false);
        return new DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder documentViewHolder, int i) {
        documentViewHolder.setContent(documents.get(i));
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }
}
