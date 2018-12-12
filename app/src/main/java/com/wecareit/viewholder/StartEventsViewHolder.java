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
import com.wecareit.fragments.events.EventsFragment;
import com.wecareit.fragments.news.NewsFragment;
import com.wecareit.model.AuthorRes;
import com.wecareit.model.Client;
import com.wecareit.model.EventsRes;
import com.wecareit.model.Vehicle;
import com.wecareit.model.Vehicles;

import java.util.ArrayList;

public class StartEventsViewHolder extends RecyclerView.ViewHolder {

    private EventsRes eventsRes;

    private TextView mTitle;
    private TextView mTime;
    private LinearLayout lnMain;

    private ImageView mUserIconImg;

    public StartEventsViewHolder(@NonNull View itemView) {
        super(itemView);

        lnMain = (LinearLayout) itemView.findViewById(R.id.lnMain_startfragment);

        mTitle = (TextView) itemView.findViewById(R.id.tvTitle_startlist);
        mTime = (TextView) itemView.findViewById(R.id.tvTime_startlist);

        mUserIconImg = (ImageView) itemView.findViewById(R.id.iconImg_startlist);
        mUserIconImg.setImageResource(R.drawable.ic_weekly_schedule);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2, Color.LTGRAY);
        gd.setCornerRadius(3.0f);

        lnMain.setBackground(gd);

    }

    public void setContent(EventsRes eventsRes) {
        this.eventsRes = eventsRes;
        //Log.d("NewsStart",newsResponse.getMessage()+newsResponse.getAuthor().getName());
        ArrayList<AuthorRes> users;
        ArrayList<Client> clients;
        ArrayList<Vehicles> vehicles;
        users = eventsRes.getUsers();
        clients = eventsRes.getClients();
        vehicles = eventsRes.getVehicles();
        String st_Desc = "";
        for (AuthorRes authorRes : users) {
            st_Desc = st_Desc + "," + authorRes.getName();
        }
        for (Client client : clients) {
            st_Desc = st_Desc + "," + client.getName();
        }
        for (Vehicles vehicles1 : vehicles) {
            st_Desc = st_Desc + "," + vehicles1.getName();
        }
        mTitle.setText(eventsRes.getTime().getLower().substring(11, 16) + " - " + eventsRes.getTime().getUpper().substring(11, 16) + eventsRes.getName());
        mTime.setText(st_Desc.substring(1));

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.eventsFragment = EventsFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.eventsFragment)
                        .addToBackStack(null)
                        .commit();
                Global.floatingButton.setVisibility(View.VISIBLE);

            }
        });
    }
}
