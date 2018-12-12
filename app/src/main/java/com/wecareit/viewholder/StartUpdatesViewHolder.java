package com.wecareit.viewholder;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wecareit.R;
import com.wecareit.common.Global;
import com.wecareit.fragments.document.DocumentFragment;
import com.wecareit.fragments.drivinglog.DrivingLogFragment;
import com.wecareit.fragments.information.InformationFragment;
import com.wecareit.model.Tasks;
import com.wecareit.model.Updates;

public class StartUpdatesViewHolder extends RecyclerView.ViewHolder {

    private Updates updates;

    private TextView mTitle;
    private TextView mTime;
    private LinearLayout lnMain;

    private ImageView mUserIconImg;

    public StartUpdatesViewHolder(@NonNull View itemView) {
        super(itemView);

        mTitle = (TextView) itemView.findViewById(R.id.tvTitle_startlist);
        mTime = (TextView) itemView.findViewById(R.id.tvTime_startlist);
        lnMain = (LinearLayout) itemView.findViewById(R.id.lnMain_startfragment);

        mUserIconImg = (ImageView) itemView.findViewById(R.id.iconImg_startlist);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2,Color.LTGRAY);
        gd.setCornerRadius(3.0f);

        lnMain.setBackground(gd);

    }

    public void setContent(Updates updates) {
        this.updates = updates;
        if(updates.getType().equals("file")){
            mUserIconImg.setImageResource(R.drawable.ic_document);
            mTitle.setText(updates.getUser().getName() + "laddade upp file.");
        } else if (updates.getType().equals("informationbox")) {
            mUserIconImg.setImageResource(R.drawable.ic_information);
            if (updates.getAction().equals("change")) {
                mTitle.setText(updates.getUser().getName() + " ändrade information under " + updates.getEdited_object().getName());
            } else if (updates.getAction().equals("addition")){
                mTitle.setText(updates.getUser().getName() + " lade till information under " + updates.getEdited_object().getName());
            }
        }

        mTime.setText(updates.getAction_time().substring(0,10));

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updates.getType().equals("file")){
                    Global.documentFragment = DocumentFragment.createInstance();
                    Global.fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, Global.documentFragment)
                            .addToBackStack(null)
                            .commit();
                    //Global.floatingButton.setVisibility(View.GONE);
                } else if (updates.getType().equals("informationbox")){
                    Global.informationFragment = InformationFragment.createInstance();
                    Global.fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, Global.informationFragment)
                            .addToBackStack(null)
                            .commit();
                    //Global.floatingButton.setVisibility(View.GONE);
                }
            }
        });

    }

}
