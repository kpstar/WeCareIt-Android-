package com.wecareit.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wecareit.R;
import com.wecareit.common.Message;
import com.wecareit.model.Attendance;
import com.wecareit.model.AttendedClient;

public class AttendanceViewHolder extends RecyclerView.ViewHolder {
    private AttendedClient attendedClient;

    private TextView mClientName;
    private ImageView mDecrease;
    private TextView mHours;
    private ImageView mIncrease;
    private TextView mDescription;
    private RelativeLayout mControls;

    public AttendanceViewHolder(@NonNull View itemView) {
        super(itemView);

        mClientName = (TextView) itemView.findViewById(R.id.attendance_row_name);
        mDecrease = (ImageView) itemView.findViewById(R.id.attendance_row_decrease);
        mHours = (TextView) itemView.findViewById(R.id.attendance_row_hours);
        mIncrease = (ImageView) itemView.findViewById(R.id.attendance_row_increase);
        mDescription = (TextView) itemView.findViewById(R.id.attendance_row_description);
        mControls = (RelativeLayout) itemView.findViewById(R.id.attendance_row_controls);

        mDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hours = Integer.valueOf(mHours.getText().toString());
                if (hours == 0) hours = 0;
                else hours--;
                mHours.setText(String.valueOf(hours));
                attendedClient.setHours(hours);
            }
        });
        mIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hours = Integer.valueOf(mHours.getText().toString());
                mHours.setText(String.valueOf(++hours));
                attendedClient.setHours(hours);
            }
        });
    }

    public void setContent(AttendedClient attendedClient, boolean submitted) {
        this.attendedClient = attendedClient;

        mClientName.setText(attendedClient.getClient().getName());
        mHours.setText(String.valueOf(attendedClient.getHours()));
        if(attendedClient.getHours()==0){
            mDescription.setText("Inga rapporterade");
        }
        if (submitted) {
            mDecrease.setVisibility(View.GONE);
            mIncrease.setVisibility(View.GONE);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_TOP, RelativeLayout.TRUE);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            mControls.setLayoutParams(layoutParams);
            mDescription.setVisibility(View.VISIBLE);
        } else {
            if (attendedClient.getHours() > 0) {
                mDecrease.setVisibility(View.GONE);
                mIncrease.setVisibility(View.GONE);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.ALIGN_TOP, RelativeLayout.TRUE);
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                mControls.setLayoutParams(layoutParams);
                mDescription.setVisibility(View.VISIBLE);
            } else {
                mDecrease.setVisibility(View.VISIBLE);
                mIncrease.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                mControls.setLayoutParams(layoutParams);
                mDescription.setVisibility(View.GONE);
            }
        }

    }
}
