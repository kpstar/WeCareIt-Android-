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
import com.wecareit.fragments.news.NewsFragment;
import com.wecareit.fragments.notes.NotesFragment;
import com.wecareit.model.NewsResponse;

public class StartNewsViewHolder extends RecyclerView.ViewHolder {

    private NewsResponse newsResponse;

    private TextView mTitle;
    private TextView mTime;
    private LinearLayout lnMain;

    private ImageView mUserIconImg;

    public StartNewsViewHolder(@NonNull View itemView) {
        super(itemView);

        lnMain = (LinearLayout) itemView.findViewById(R.id.lnMain_startfragment);

        mTitle = (TextView) itemView.findViewById(R.id.tvTitle_startlist);
        mTime = (TextView) itemView.findViewById(R.id.tvTime_startlist);

        mUserIconImg = (ImageView) itemView.findViewById(R.id.iconImg_startlist);
        mUserIconImg.setImageResource(R.drawable.ic_news);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2,Color.LTGRAY);
        gd.setCornerRadius(3.0f);

        lnMain.setBackground(gd);

    }

    public void setContent(NewsResponse newsResponse) {
        this.newsResponse = newsResponse;
        Log.d("NewsStart",newsResponse.getMessage()+newsResponse.getAuthor().getName());
        mTitle.setText(newsResponse.getMessage());
        mTime.setText(newsResponse.getAuthor().getName()+ " " + newsResponse.getCreation_date().substring(0,10)+ " " + newsResponse.getCreation_date().substring(11,19) + "(" + newsResponse.getReply_count() + " " + "svar Nytt)");

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.newsFragment = NewsFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.newsFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

}
