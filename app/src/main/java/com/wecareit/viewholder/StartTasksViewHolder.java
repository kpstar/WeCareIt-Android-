package com.wecareit.viewholder;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wecareit.R;
import com.wecareit.common.Global;
import com.wecareit.fragments.news.NewsFragment;
import com.wecareit.fragments.tasks.TasksFragment;
import com.wecareit.model.Tasks;

public class StartTasksViewHolder extends RecyclerView.ViewHolder {

    private Tasks tasks;

    private TextView mTitle;
    private TextView mTime;
    private LinearLayout lnMain;

    private ImageView mUserIconImg;

    public StartTasksViewHolder(@NonNull View itemView) {
        super(itemView);

        mTitle = (TextView) itemView.findViewById(R.id.tvTitle_startlist);
        mTime = (TextView) itemView.findViewById(R.id.tvTime_startlist);
        lnMain = (LinearLayout) itemView.findViewById(R.id.lnMain_startfragment);

        mUserIconImg = (ImageView) itemView.findViewById(R.id.iconImg_startlist);
        mUserIconImg.setImageResource(R.drawable.ic_task);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2,Color.LTGRAY);
        gd.setCornerRadius(3.0f);

        lnMain.setBackground(gd);

    }

    public void setContent(Tasks tasks) {
        this.tasks = tasks;

        mTitle.setText(tasks.getActivity());
        mTime.setText(tasks.getDeadline_date()+" - "+tasks.getAssigned_to().getName());

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.tasksFragment = TasksFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.tasksFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

    }

}
