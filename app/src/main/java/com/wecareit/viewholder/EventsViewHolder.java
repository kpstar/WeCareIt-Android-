package com.wecareit.viewholder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.UnicodeSetSpanner;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.wecareit.LoginActivity;
import com.wecareit.R;
import com.wecareit.common.Global;
import com.wecareit.fragments.events.EventsFragment;
import com.wecareit.fragments.events.EventsUpdateFragment;
import com.wecareit.model.AuthorRes;
import com.wecareit.model.Client;
import com.wecareit.model.EventsRes;
import com.wecareit.model.Vehicles;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsViewHolder extends RecyclerView.ViewHolder {

    private EventsRes news;

    private int flag_expand=0;
    private String flag_date = " ";

    Context context;

    private ArrayList<AuthorRes> array_users;
    private ArrayList<Vehicles> array_vehicles;
    private ArrayList<Client> array_client;
    private TextView mTimeTitle;
    private TextView mTime;
    private TextView mTitle;
    private TextView mDesc;
    private ImageView mMore;
    private ImageView mExpImag;
    private LinearLayout lnDesc;
    private RelativeLayout lnRow;
    private FlexboxLayout fbRow;
    private int Id;

    public EventsViewHolder(@NonNull View itemView) {
        super(itemView);

        context = itemView.getContext();

        mTimeTitle = (TextView) itemView.findViewById(R.id.tvTimetile_eventslist);
        mTime = (TextView) itemView.findViewById(R.id.tvTime_eventslist);
        mTitle = (TextView) itemView.findViewById(R.id.tvTitle_eventslist);
        mDesc = (TextView) itemView.findViewById(R.id.tvDesc_eventslist);
        mMore= (ImageView) itemView.findViewById(R.id.ivMore_event);
        mExpImag = (ImageView) itemView.findViewById(R.id.ivExpand_eventslist);
        lnDesc = (LinearLayout) itemView.findViewById(R.id.lnDesc_eventslist);
        lnRow = (RelativeLayout) itemView.findViewById(R.id.lnRow_eventsfragment);
        fbRow = (FlexboxLayout) itemView.findViewById(R.id.fb_eventsrow);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(4,Color.LTGRAY);
        gd.setCornerRadius(5.0f);
        lnRow.setBackground(gd);
        GradientDrawable gd1 = new GradientDrawable();
        gd1.setShape(GradientDrawable.RECTANGLE);
        gd1.setStroke(2,Color.LTGRAY);
        gd1.setCornerRadius(3.0f);
        lnDesc.setBackground(gd1);

        lnRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_expand ==0) {
                    lnDesc.setVisibility(View.VISIBLE);
                    mExpImag.setRotation(180);
                    flag_expand =1;
                } else {
                    lnDesc.setVisibility(View.GONE);
                    mExpImag.setRotation(0);
                    flag_expand =0;
                }
            }
        });

        mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

//        mEditImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                EventsFragment.flag_update = 1;
//                EventsFragment.id_update = news.getId();
//                EventsFragment.name_update = news.getName();
//                EventsFragment.description_update = news.getDescription();
//                EventsFragment.startdate_update = news.getTime().getLower();
//                EventsFragment.enddate_update = news.getTime().getUpper();
//                EventsFragment.users.add(1);
//                EventsFragment.users.add(2);
//
//
//                Global.eventsUpdateFragment = EventsUpdateFragment.createInstance();
//                Global.fragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, Global.eventsUpdateFragment)
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
//
//        mDelImag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*if(flag_expand ==0) {
//                    lnDesc.setVisibility(View.VISIBLE);
//                    flag_expand =1;
//                } else {
//                    lnDesc.setVisibility(View.GONE);
//                    flag_expand =0;
//                }*/
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//                alertDialog.setMessage("Är du säker på att du vill ta bort \"" + news.getName()+", "+news.getTime().getLower()+" - "+news.getTime().getUpper()+"\"?");
//                alertDialog.setPositiveButton("Avbryt", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                alertDialog.setNegativeButton("Ta bort", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        deleteData();
//
//                    }
//                });
//
//                AlertDialog dialog = alertDialog.create();
//                dialog.show();
//
//            }
//        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_events, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_edit) {
                    weeklyEdit();
                } else if (menuItem.getItemId() == R.id.menu_remove) {
                    weeklyRemove();
                }
                return false;
            }
        });
    }

    public void weeklyEdit() {
        EventsFragment.flag_update = 1;
        EventsFragment.id_update = news.getId();
        EventsFragment.name_update = news.getName();
        EventsFragment.description_update = news.getDescription();
        EventsFragment.startdate_update = news.getTime().getLower();
        EventsFragment.enddate_update = news.getTime().getUpper();
//        EventsFragment.users.add(1);
//        EventsFragment.users.add(2);


        Global.eventsUpdateFragment = EventsUpdateFragment.createInstance();
        Global.fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Global.eventsUpdateFragment)
                .addToBackStack(null)
                .commit();
    }

    public void weeklyRemove() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("Är du säker på att du vill ta bort \"" + news.getName()+", "+news.getTime().getLower()+" - "+news.getTime().getUpper()+"\"?");
        alertDialog.setPositiveButton("Avbryt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setNegativeButton("Ta bort", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteData();

            }
        });
        alertDialog.show();
    }

    public void setContent(EventsRes news) {
        this.news = news;

        mTimeTitle.setText(news.getTime().getLower().substring(0,10)+"("+news.getName()+")");
        mTime.setText(news.getTime().getLower().substring(11,16) + " - " + news.getTime().getUpper().substring(11,16));
        mTitle.setText(news.getName());
        mDesc.setText(news.getDescription());
        if(flag_date.equals(news.getTime().getLower().substring(0,10))){
            lnRow.setVisibility(View.GONE);
            flag_date = news.getTime().getLower().substring(0, 10);
        } else {
            flag_date = news.getTime().getLower().substring(0, 10);
        }

        EventsFragment.clients_update = new ArrayList<String>();
        EventsFragment.vehicles_update = new ArrayList<String>();
        EventsFragment.users_update = new ArrayList<String>();

        Id = news.getId();
        Log.e("ID", String.valueOf(Id));

        array_vehicles = new ArrayList<Vehicles>();
        array_vehicles = news.getVehicles();
        for(Vehicles vehicles: array_vehicles ){

            EventsFragment.vehicles_update.add(vehicles.getName());
            GradientDrawable gd_vehicle = new GradientDrawable();
            gd_vehicle.setShape(GradientDrawable.RECTANGLE);
            gd_vehicle.setColor(Color.argb(255,3,155,229));
            gd_vehicle.setCornerRadius(20.0f);
            TextView textView = new TextView(context);
            textView.setText(vehicles.getName());
            textView.setBackground(gd_vehicle);
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setPadding(20,6,20,6);
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(3,6,3,6);
            textView.setLayoutParams(params);
            fbRow.addView(textView);
        }

        array_users = new ArrayList<AuthorRes>();
        array_users = news.getUsers();
        for(AuthorRes authorRes: array_users ){

            EventsFragment.users_update.add(authorRes.getName());
            GradientDrawable gd_user = new GradientDrawable();
            gd_user.setShape(GradientDrawable.RECTANGLE);
            gd_user.setColor(Color.argb(255,243,243,243));
            gd_user.setCornerRadius(20.0f);
            TextView textView = new TextView(context);
            textView.setText(authorRes.getName());
            textView.setBackground(gd_user);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setPadding(20,6,20,6);
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(3,6,3,6);
            textView.setLayoutParams(params);
            fbRow.addView(textView);
        }

        array_client = new ArrayList<Client>();
        array_client = news.getClients();
        for(Client client: array_client ){

            EventsFragment.clients_update.add(client.getName());
            GradientDrawable gd_client = new GradientDrawable();
            gd_client.setShape(GradientDrawable.RECTANGLE);
            gd_client.setColor(Color.argb(255,76,175,80));
            gd_client.setCornerRadius(20.0f);
            TextView textView = new TextView(context);
            textView.setText(client.getName());
            textView.setBackground(gd_client);
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setPadding(20,6,20,6);
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(3,6,3,6);
            textView.setLayoutParams(params);
            fbRow.addView(textView);
        }

    }

    public void deleteData(){

        Call<String> call = Global.getAPIService.deleteWeeklySchedule("Token " + Global.token, Id);
        Log.e("ID Value = ", String.valueOf(Id));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 401){
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
                Global.eventsFragment = EventsFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.eventsFragment)
                        .addToBackStack(null)
                        .commit();

            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

}
