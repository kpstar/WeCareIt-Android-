package com.wecareit.viewholder;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wecareit.R;
import com.wecareit.adapter.DownloadImage;
import com.wecareit.fragments.tasks.TasksFragment;
import com.wecareit.model.AssignedTo;
import com.wecareit.model.Tasks;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class TasksViewHolder extends RecyclerView.ViewHolder {

    private Tasks news;
    private AssignedTo author;
    private String stringTime = "";
    private int flag_comment=0;

    private TextView mUsername;
    private TextView mTime;
    private TextView mTitle;
    private ImageView ivStatus;
    private CircleImageView ivUser;
    private RelativeLayout rlUser;
    private LinearLayout lnMain, lnUser;

    public TasksViewHolder(@NonNull View itemView) {
        super(itemView);

        mUsername = (TextView) itemView.findViewById(R.id.tvUsername_taskslist);
        mTime = (TextView) itemView.findViewById(R.id.tvTime_taskslist);
        mTitle = (TextView) itemView.findViewById(R.id.tvTitle_taskslist);
        ivStatus = (ImageView) itemView.findViewById(R.id.ivCheck_taskslist);
        ivUser = (CircleImageView) itemView.findViewById(R.id.imgAvatar_taskslist);
        rlUser = (RelativeLayout) itemView.findViewById(R.id.rlUser_tasksrow);
        lnMain = (LinearLayout) itemView.findViewById(R.id.row_tasks);
        lnUser = (LinearLayout) itemView.findViewById(R.id.lnUser_taskrow);

    }

    public void setContent(Tasks news) {
        this.news = news;

        mTime.setText(news.getDeadline_date());
        mTitle.setText(news.getActivity());
        mUsername.setText(news.getAssigned_to().getName());
        if(news.getStatus().equals("COMPL")){
            ivStatus.setBackgroundResource(R.drawable.ic_complete);
        } else if (news.getStatus().equals("CANCL")){
            ivStatus.setBackgroundResource(R.drawable.ic_cancel);
        }
        if(news.getAssigned_to().getAvatar()!=null){
            new DownloadImage(ivUser).execute(news.getAssigned_to().getAvatar());
        } else {

        }
        GradientDrawable gd_user = new GradientDrawable();
        gd_user.setShape(GradientDrawable.RECTANGLE);
        gd_user.setColor(Color.argb(255,240,240,240));
        gd_user.setCornerRadius(30.0f);
        lnUser.setBackground(gd_user);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2,Color.LTGRAY);
        gd.setCornerRadius(5.0f);
        lnMain.setBackground(gd);
    }
}
