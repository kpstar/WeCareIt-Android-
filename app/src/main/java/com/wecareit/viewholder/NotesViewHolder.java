package com.wecareit.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.wecareit.R;
import com.wecareit.adapter.DownloadImage;
import com.wecareit.model.Client;
import com.wecareit.model.Main_Category;
import com.wecareit.model.NotesRes;

import java.util.ArrayList;

import ru.noties.markwon.Markwon;
import us.feras.mdv.MarkdownView;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    private NotesRes notes;
    private ArrayList<Client> clients;
    private Main_Category main_category;
    private Context context;
    private TextView mUsername;
    private TextView mTime, tvTime, tvDate;
    private TextView mTitle;
    private TextView mDesc;
    private ImageView mUserImg,ivFlag;
    private FlexboxLayout fbRow;
    //private TextView tvClient1,tvClient2,tvClient3,tvClient4;
    //private TextView tvArea1, tvArea2;
    //private TextView tvKeyword1, tvKeyword2, tvKeyword3, tvKeyword4, tvKeyword5, tvKeyword6;
    //private TextView tvCategory1, tvCategory2, tvCategory3;
    private CardView lnMain;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);

        context = itemView.getContext();

        mUsername = (TextView) itemView.findViewById(R.id.tvUsername_noteslist);
        mTime = (TextView) itemView.findViewById(R.id.tvTime_noteslist);
        tvTime = (TextView) itemView.findViewById(R.id.tvTime_notesrow);
        tvDate = (TextView) itemView.findViewById(R.id.tvDate_notesrow);
        mTitle = (TextView) itemView.findViewById(R.id.tvTitle_noteslist);
        mDesc = (TextView) itemView.findViewById(R.id.tvDesc_noteslist);
        /*tvClient1 = (TextView) itemView.findViewById(R.id.tvaccom1_noteslist);
        tvClient2 = (TextView) itemView.findViewById(R.id.tvaccom2_noteslist);
        tvClient3 = (TextView) itemView.findViewById(R.id.tvaccom3_noteslist);
        tvClient4 = (TextView) itemView.findViewById(R.id.tvaccom4_noteslist);
        tvArea1 = (TextView) itemView.findViewById(R.id.tvarea1_noteslist);
        tvArea2 = (TextView) itemView.findViewById(R.id.tvarea2_noteslist);
        tvKeyword1 = (TextView) itemView.findViewById(R.id.tvkey1_noteslist);
        tvKeyword2 = (TextView) itemView.findViewById(R.id.tvkey2_noteslist);
        tvKeyword3 = (TextView) itemView.findViewById(R.id.tvkey3_noteslist);
        tvKeyword4 = (TextView) itemView.findViewById(R.id.tvkey4_noteslist);
        tvKeyword5 = (TextView) itemView.findViewById(R.id.tvkey5_noteslist);
        tvKeyword6 = (TextView) itemView.findViewById(R.id.tvkey6_noteslist);
        tvCategory1 = (TextView) itemView.findViewById(R.id.tvcate1_noteslist);
        tvCategory2 = (TextView) itemView.findViewById(R.id.tvcate2_noteslist);
        tvCategory3 = (TextView) itemView.findViewById(R.id.tvcate3_noteslist);*/
        mUserImg = (ImageView) itemView.findViewById(R.id.userImg_noteslist);
        ivFlag = (ImageView) itemView.findViewById(R.id.ivFlag_notesrow);
        lnMain = (CardView) itemView.findViewById(R.id.lnMain_notesrow);
        fbRow = (FlexboxLayout) itemView.findViewById(R.id.fb_notesrow);

        /*GradientDrawable gd1 = new GradientDrawable();
        gd1.setShape(GradientDrawable.RECTANGLE);
        gd1.setColor(Color.argb(255,78, 177, 121 ));
        gd1.setCornerRadius(20.0f);
        *//*tvClient1.setBackground(gd1);
        tvClient2.setBackground(gd1);
        tvClient3.setBackground(gd1);
        tvClient4.setBackground(gd1);*//*
        GradientDrawable gd2 = new GradientDrawable();
        gd2.setShape(GradientDrawable.RECTANGLE);
        gd2.setCornerRadius(20.0f);
        gd2.setColor(Color.argb(255,143, 36, 107 ));
        *//*tvArea1.setBackground(gd2);
        tvArea2.setBackground(gd2);*//*
        GradientDrawable gd3 = new GradientDrawable();
        gd3.setShape(GradientDrawable.RECTANGLE);
        gd3.setCornerRadius(20.0f);
        gd3.setColor(Color.argb(255,92, 92, 92 ));
        *//*tvKeyword1.setBackground(gd3);
        tvKeyword2.setBackground(gd3);
        tvKeyword3.setBackground(gd3);
        tvKeyword4.setBackground(gd3);
        tvKeyword5.setBackground(gd3);
        tvKeyword6.setBackground(gd3);*//*
        GradientDrawable gd4 = new GradientDrawable();
        gd4.setShape(GradientDrawable.RECTANGLE);
        gd4.setCornerRadius(20.0f);
        gd4.setColor(Color.argb(255,92, 92, 92 ));*/
        /*tvCategory1.setBackground(gd4);
        tvCategory2.setBackground(gd4);
        tvCategory3.setBackground(gd4);*/

        GradientDrawable gd_main = new GradientDrawable();
        gd_main.setShape(GradientDrawable.RECTANGLE);
        gd_main.setStroke(2,Color.LTGRAY);
        gd_main.setCornerRadius(5.0f);
        lnMain.setBackground(gd_main);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setContent(NotesRes notes) {
        this.notes = notes;
        this.clients = notes.getClients();
        this.main_category = notes.getMain_category();

/*

        if (notes.getArea().getId() == 1){
            tvArea1.setVisibility(View.VISIBLE);
        } else if(notes.getArea().getId() == 2){
            tvArea2.setVisibility(View.VISIBLE);
        }
*/
        fbRow.removeAllViews();
        for (Client client: clients) {
            GradientDrawable gd_client = new GradientDrawable();
            gd_client.setShape(GradientDrawable.RECTANGLE);
            gd_client.setColor(Color.argb(255,76,175,80));
            gd_client.setCornerRadius(32.0f);
            TextView tvClient = new TextView(context);
            tvClient.setText(client.getName());
            tvClient.setBackground(gd_client);
            tvClient.setTextColor(Color.parseColor("#ffffff"));
            tvClient.setPadding(20,6,20,6);
            FlexboxLayout.LayoutParams params_client = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
            params_client.setMargins(10,6,10,6);
            tvClient.setLayoutParams(params_client);
            fbRow.addView(tvClient);
            /*switch (client.getId()) {
                case 1:
                    tvClient1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    tvClient2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    tvClient3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    tvClient4.setVisibility(View.VISIBLE);
                    break;

            }*/
        }

        GradientDrawable gd_category = new GradientDrawable();
        gd_category.setShape(GradientDrawable.RECTANGLE);
        gd_category.setColor(Color.argb(255,158,158,158));
        gd_category.setCornerRadius(32.0f);
        TextView tvCategory = new TextView(context);
        FlexboxLayout.LayoutParams params_category = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
        params_category.setMargins(10,6,10,6);
        if (notes.getMain_category() != null) {
            tvCategory.setText(notes.getMain_category().getTitle());
            tvCategory.setTextColor(Color.parseColor("#ffffff"));
            tvCategory.setPadding(20,6,20,6);
            tvCategory.setLayoutParams(params_category);
            tvCategory.setBackground(gd_category);
            fbRow.addView(tvCategory);
        }

//        TextView tvMainkey = new TextView(context);
//        if (notes.getMajor_keyword() != null) {
//            tvMainkey.setText(notes.getMajor_keyword().getTitle());
//            tvMainkey.setTextColor(Color.parseColor("#ffffff"));
//            tvMainkey.setPadding(20,6,20,6);
//            tvMainkey.setLayoutParams(params_category);
//            tvMainkey.setBackground(gd_category);
//            fbRow.addView(tvMainkey);
//        }

        TextView tvMinorkey = new TextView(context);
        if (notes.getMinor_keyword() != null) {
            tvMinorkey.setText(notes.getMinor_keyword().getTitle());
            tvMinorkey.setTextColor(Color.parseColor("#ffffff"));
            tvMinorkey.setPadding(20,6,20,6);
            tvMinorkey.setLayoutParams(params_category);
            tvMinorkey.setBackground(gd_category);
            fbRow.addView(tvMinorkey);
        }

        GradientDrawable gd_area = new GradientDrawable();
        gd_area.setShape(GradientDrawable.RECTANGLE);
        gd_area.setColor(Color.argb(255,233,30,99));
        gd_area.setCornerRadius(32.0f);

        TextView tvArea = new TextView(context);
        FlexboxLayout.LayoutParams params_area = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
        params_area.setMargins(10,6,10,6);
        if (notes.getArea() != null) {
            tvArea.setText(notes.getArea().getTitle());
            tvArea.setTextColor(Color.parseColor("#ffffff"));
            tvArea.setPadding(20,6,20,6);
            tvArea.setLayoutParams(params_area);
            tvArea.setBackground(gd_area);
            fbRow.addView(tvArea);
        }

        if (notes.isBackdated()){
            ivFlag.setVisibility(View.VISIBLE);
        } else {
            ivFlag.setVisibility(View.GONE);
        }

        mUsername.setText(notes.getAuthor().getName());
        mTime.setText(notes.getAuthor().getTitle());
        tvDate.setText(notes.getCreation_date().substring(0,10));
        tvTime.setText(notes.getCreation_date().substring(11,16));
        mTitle.setText(notes.getSummary());
//        mDesc.setText(notes.getText());
        Markwon.setMarkdown(mDesc, notes.getText());
        if (notes.getAuthor().getAvatar() != null) {
            new DownloadImage(mUserImg).execute(notes.getAuthor().getAvatar());
        } else {
            mUserImg.setImageDrawable(context.getDrawable(R.drawable.ic_user_template));
        }
    }
}
