package com.wecareit.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.wecareit.InformationEditActivity;
import com.wecareit.NavigationActivity;
import com.wecareit.R;
import com.wecareit.common.Global;
import com.wecareit.fragments.information.InformationFragment;
import com.wecareit.model.Author;
import com.wecareit.model.AuthorRes;
import com.wecareit.model.InfoRes;

import ru.noties.markwon.Markwon;

public class InformationViewHolder extends RecyclerView.ViewHolder {

    private InfoRes info;

    private TextView mUsername;
    private TextView mTime;
    private TextView mMessage;
    private TextView mTitle;
    private ImageView mEditImg;
    private CardView lnMain;
    private Context context;
    //private ImageView mResImag;

    public InformationViewHolder(@NonNull View itemView) {
        super(itemView);

        context = itemView.getContext();
        mUsername = (TextView) itemView.findViewById(R.id.tvUsername_informationlist);
        mTime = (TextView) itemView.findViewById(R.id.tvTime_informationlist);
        mMessage = (TextView) itemView.findViewById(R.id.tvDsec_informationlist);
        mTitle = (TextView) itemView.findViewById(R.id.tvTitle_informationlist);
        //mUserImg = (ImageView) itemView.findViewById(R.id.userImg_newslist);
        mEditImg = (ImageView) itemView.findViewById(R.id.btnEdit_informationlist);
        lnMain = (CardView) itemView.findViewById(R.id.row_information);
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
        Markwon.setMarkdown(mMessage, info.getText());
        mUsername.setText(" "+info.getAuthor().getName());
        mTitle.setText(info.getName());

        mEditImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_register, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent mIntent = new Intent((NavigationActivity)context, InformationEditActivity.class);
                mIntent.putExtra("Title", info.getName());
                mIntent.putExtra("Desc", info.getText());
                mIntent.putExtra("InfoId", info.getId());
                mIntent.putExtra("EditFlag", 1);
                context.startActivity(mIntent);
                return true;
            }
        });
    }


}
