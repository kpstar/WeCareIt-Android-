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
import com.wecareit.fragments.information.InformationFragment;
import com.wecareit.model.Author;
import com.wecareit.model.AuthorRes;
import com.wecareit.model.InfoRes;

public class InformationViewHolder extends RecyclerView.ViewHolder {

    private InfoRes info;

    private TextView mUsername;
    private TextView mTime;
    private TextView mMessage;
    private TextView mTitle;
    private ImageView mEditImg;
    private LinearLayout lnMain;
    //private ImageView mResImag;

    public InformationViewHolder(@NonNull View itemView) {
        super(itemView);

        mUsername = (TextView) itemView.findViewById(R.id.tvUsername_informationlist);
        mTime = (TextView) itemView.findViewById(R.id.tvTime_informationlist);
        mMessage = (TextView) itemView.findViewById(R.id.tvDsec_informationlist);
        mTitle = (TextView) itemView.findViewById(R.id.tvTitle_informationlist);
        //mUserImg = (ImageView) itemView.findViewById(R.id.userImg_newslist);
        mEditImg = (ImageView) itemView.findViewById(R.id.btnEdit_informationlist);
        lnMain = (LinearLayout) itemView.findViewById(R.id.row_information);
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2,Color.LTGRAY);
        gd.setCornerRadius(5.0f);
        lnMain.setBackground(gd);
    }

    public void setContent(InfoRes info) {
        this.info = info;

        String time = info.getCreation_date().substring(0,10)+"  "+info.getCreation_date().substring(11,16);

        mTime.setText(time);
        mMessage.setText(info.getText());
        mUsername.setText(" "+info.getAuthor().getName());
        mTitle.setText(info.getName());

        mEditImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformationFragment.stTitle = info.getName();
                InformationFragment.stDesc = info.getText();
                InformationFragment.id_information = info.getId();
                InformationFragment.flagEdit_informationfragment = 1;
                Global.informationFragment = InformationFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.informationFragment)
                        .addToBackStack(null)
                        .commit();
                Global.floatingButton.setVisibility(View.GONE);
            }
        });

    }


}
