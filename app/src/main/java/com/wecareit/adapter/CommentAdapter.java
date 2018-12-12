package com.wecareit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.wecareit.R;
import com.wecareit.model.Comment;
import com.wecareit.model.News;
import com.wecareit.viewholder.CommentViewHolder;
import com.wecareit.viewholder.NewsViewHolder;

import java.util.List;

/*public class CommentAdapter extends ExpandableRecyclerViewAdapter<NewsViewHolder, CommentViewHolder> {
    public CommentAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public NewsViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_news, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public CommentViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_comment, parent, false);
        return new CommentViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(CommentViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Comment comment = (Comment) group.getItems().get(childIndex);
        holder.bind(comment);
    }

    @Override
    public void onBindGroupViewHolder(NewsViewHolder holder, int flatPosition, ExpandableGroup group) {
        final News news = (News) group;
        holder.bind(news);
    }
}*/
