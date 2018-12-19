package com.wecareit.viewholder;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
    private ImageView ivStatus, ivDeadline;
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
        ivDeadline = (ImageView) itemView.findViewById(R.id.ivDeadline_notification);

    }

    public void setContent(Tasks news) {
        this.news = news;

        if (checkDeadline(news.getDeadline_date()) && news.getStatus().equals("ACTIVE")) {
            ivDeadline.setVisibility(View.VISIBLE);
        } else {
            ivDeadline.setVisibility(View.GONE);
        }
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

    public boolean checkDeadline(String deadline) {
        int year = Integer.parseInt(deadline.split("-")[0]);
        int month = Integer.parseInt(deadline.split("-")[1]);
        int date = Integer.parseInt(deadline.split("-")[2]);

        Calendar calendar = Calendar.getInstance();

        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH) + 1;
        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);

        Log.e("Year mOnteh date=", String.format("%d %d %d",thisYear,thisMonth,thisDay));

        if (year > thisYear) return false;
        else if (year < thisYear) return true;
        else {
            if (month > thisMonth) return false;
            else if (month < thisMonth) return true;
            else {
                if (date > thisDay) return false;
                else if (date <  thisDay) return true;
                else return false;
            }
        }
    }
}
