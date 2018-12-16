package com.wecareit.fragments.events;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecareit.LoginActivity;
import com.wecareit.MultiSelectSpinner;
import com.wecareit.MultiSpinner;
import com.wecareit.R;
import com.wecareit.adapter.EventsAdapter;
import com.wecareit.adapter.SpinnerAdapter;
import com.wecareit.common.Global;
import com.wecareit.common.MyDate;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.model.AuthorRes;
import com.wecareit.model.Client;
import com.wecareit.model.EventsRes;
import com.wecareit.model.User;
import com.wecareit.model.Vehicle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends TemplateFragment implements MultiSpinner.MultiSpinnerListener {

    public static int flag_update = 0;
    public static int id_update = 0;
    public static String name_update = "", description_update = "", startdate_update = "", enddate_update = "";
    public static ArrayList<String> clients_update, users_update , vehicles_update  ;
    private ArrayList<EventsRes> eventsRes;
    /*private ArrayList<Client> clients;
    private ArrayList<Vehicle> vehicles;*/
    public static ArrayList<Integer> users;
    private ArrayList<Integer> user_id, client_id, vehicle_id;

    private RecyclerView mRecyclerView;
    private Calendar day1Calendar;
    private Calendar day2Calendar;
    private Spinner spActivities;
    //private MultiSpinner spEmployees, spAccomm;
    private MultiSelectSpinner spVehicle, spEmployees, spAccomm;
    private LinearLayout lnEmpty, lnVehicles, lnAccom, lnActivity, lnEmployee, lnExpand;
    private Button btnClear, btnUsed;
    private int flag_expand = 0;
    private String day1, day2, day_start, stringofyear;
    private TextView tvEmpty;
    private ScrollView main_scroll;

    public static EventsFragment createInstance() {
        return new EventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // this is how we load a specific layout
        return inflater.inflate(R.layout.fragment_events, container, false);

    }

    public void onItemsSelected(boolean[] selected){
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Global.toolbar.setNavigationIcon(R.drawable.ic_side_menu);
        Global.toolbar.setTitle("VECKOSCHEMA");

        Global.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.eventsUpdateFragment = EventsUpdateFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.eventsUpdateFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        spAccomm = view.findViewById(R.id.accommospinner_eventsfragment);
        spAccomm.setItems(Global.clientslist);

        spVehicle = view.findViewById(R.id.vehiclespinner_eventsfragment);
        spVehicle.setItems(Global.vehicleslist);

        spActivities = view.findViewById(R.id.activitiesspinner_eventsfragment);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EventsFragment.this.getContext(), R.array.activities_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spActivities.setAdapter(adapter);

        spEmployees = view.findViewById(R.id.employeesspinner_eventsfragment);
        spEmployees.setItems(Global.userslist);

        mRecyclerView = view.findViewById(R.id.framgment_events_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        lnExpand = view.findViewById(R.id.expandLinear_eventsfragment);
        lnEmpty = view.findViewById(R.id.emptylinear_eventsfragment);
        lnVehicles = view.findViewById(R.id.lnVehicl_eventsfragment);
        lnAccom = view.findViewById(R.id.lnAccommo_eventsfragment);
        lnActivity = view.findViewById(R.id.lnActivities_eventsfragment);
        lnEmployee = view.findViewById(R.id.lnEmployees_eventsfragment);
        btnClear = view.findViewById(R.id.btnclear_eventsfragment);
        btnUsed = view.findViewById(R.id.btnused_eventsfragment);

        tvEmpty = view.findViewById(R.id.tvEmpty_eventsfragment);
        main_scroll = view.findViewById(R.id.scrollview_eventsfragment);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(4,Color.LTGRAY);
        gd.setCornerRadius(5.0f);
        lnEmpty.setBackground(gd);

        GradientDrawable gd1 = new GradientDrawable();
        gd1.setShape(GradientDrawable.RECTANGLE);
        gd1.setStroke(2,Color.LTGRAY);
        gd1.setCornerRadius(3.0f);
        lnExpand.setBackground(gd1);

        GradientDrawable gd_spinner = new GradientDrawable();
        gd_spinner.setShape(GradientDrawable.RECTANGLE);
        gd_spinner.setStroke(2,Color.LTGRAY);
        gd_spinner.setCornerRadius(3.0f);
        lnVehicles.setBackground(gd_spinner);
        lnAccom.setBackground(gd_spinner);
        lnActivity.setBackground(gd_spinner);
        lnEmployee.setBackground(gd_spinner);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spAccomm.setItems(Global.clientslist);
                spAccomm.setSelection(0);
                spVehicle.setItems(Global.vehicleslist);
                spEmployees.setItems(Global.userslist);
                spActivities.setSelection(0);
            }
        });

        btnUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnExpand.setVisibility(View.GONE);
                flag_expand = 0;
                mRecyclerView.setVisibility(View.GONE);
                getfilterString();
                //filterData();
                final Handler h = new Handler();
                h.postDelayed(new Runnable()
                {
                    private long time = 0;

                    @Override
                    public void run()
                    {
                        filterData();
                    }
                }, 2000);

            }
        });

        day1Calendar = Calendar.getInstance();
        day2Calendar = Calendar.getInstance();
        day1Calendar.add(Calendar.DAY_OF_MONTH,  Calendar.MONDAY-day1Calendar.get(Calendar.DAY_OF_WEEK));
        day2Calendar.add(Calendar.DAY_OF_MONTH,  Calendar.SUNDAY-day2Calendar.get(Calendar.DAY_OF_WEEK)+7);
        day1 = day1Calendar.getTime().toString().substring(4,10);
        day2 = day2Calendar.getTime().toString().substring(4,10);
        stringofyear = day1Calendar.getTime().toString().substring(30,34);

        tvEmpty.setText("Inga aktiviteter än för " + day1 + " - " + day2);


        filterData();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_events_filter, menu);
        this.setHasOptionsMenu(true);
        if (menu == null) {
            System.out.println("menu is null");
            return;
        }
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.getItem(0);
        View view = item.getActionView();
        day1Calendar = Calendar.getInstance();
        day2Calendar = Calendar.getInstance();
        ImageView tvtime_menu = (ImageView) view.findViewById(R.id.menu_item_events_filter_date);
        day1Calendar.add(Calendar.DAY_OF_MONTH,  Calendar.MONDAY-day1Calendar.get(Calendar.DAY_OF_WEEK));
        day2Calendar.add(Calendar.DAY_OF_MONTH,  Calendar.SUNDAY-day2Calendar.get(Calendar.DAY_OF_WEEK)+7);
        day2 = day2Calendar.getTime().toString().substring(4,10);
        getDayofStart();
//        tvtime_menu.setText("Vecka " + day1 + "-" + day2);
        ImageView mPrevDate = (ImageView) view.findViewById(R.id.menu_item_events_filter_prev);
        mPrevDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day1Calendar.add(Calendar.DAY_OF_MONTH,  Calendar.MONDAY-day1Calendar.get(Calendar.DAY_OF_WEEK) - 7 );
                day1 = day1Calendar.getTime().toString().substring(4,10);
                day2Calendar.add(Calendar.DAY_OF_MONTH,  Calendar.SUNDAY-day2Calendar.get(Calendar.DAY_OF_WEEK) - 7);
                day2 = day2Calendar.getTime().toString().substring(4,10);
//                tvtime_menu.setText("Vecka " + day1 + "-" + day2);
                stringofyear = day1Calendar.getTime().toString().substring(30,34);
                tvEmpty.setText("Inga aktiviteter än för " + day1 + " - " + day2);
                getDayofStart();
                filterData();
            }
        });

        ImageView mNextDate = (ImageView) view.findViewById(R.id.menu_item_events_filter_next);
        mNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day1Calendar.add(Calendar.DAY_OF_MONTH,  Calendar.MONDAY-day1Calendar.get(Calendar.DAY_OF_WEEK) + 7);
                day1 = day1Calendar.getTime().toString().substring(4,10);
                day2Calendar.add(Calendar.DAY_OF_MONTH,  Calendar.SUNDAY-day2Calendar.get(Calendar.DAY_OF_WEEK) + 7);
                day2 = day2Calendar.getTime().toString().substring(4,10);
                stringofyear = day1Calendar.getTime().toString().substring(30,34);
//                tvtime_menu.setText("Vecka " + day1 + "-" + day2);
                tvEmpty.setText("Inga aktiviteter än för " + day1 + " - " + day2);
                getDayofStart();
                filterData();
            }
        });

        ImageView mExpande = (ImageView) view.findViewById(R.id.menu_item_events_filter_expand);
        mExpande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_expand == 0){
                    lnExpand.setVisibility(View.VISIBLE);
                    flag_expand = 1;
                } else {
                    lnExpand.setVisibility(View.GONE);
                    flag_expand = 0;
                }
            }
        });

    }

    public void getDayofStart(){
        String month1 = day1.substring(0,3);
        String month2 = "";
        switch (month1){
            case "Jan":
                month2 = "01";
                break;

            case "Feb":
                month2 = "02";
                break;
            case "Mar":
                month2 = "03";
                break;

            case "Apr":
                month2 = "04";
                break;
            case "May":
                month2 = "05";
                break;

            case "Jun":
                month2 = "06";
                break;
            case "Jul":
                month2 = "07";
                break;

            case "Aug":
                month2 = "08";
                break;
            case "Sep":
                month2 = "09";
                break;

            case "Oct":
                month2 = "10";
                break;
            case "Nov":
                month2 = "11";
                break;

            case "Dec":
                month2 = "12";
                break;
        }
        day_start = stringofyear+"-"+month2+"-"+day1.substring(4,6);
    }

    public void loadData(){

        Call<ArrayList<EventsRes>> call = Global.getAPIService.readWeeklySchedule("Token " + Global.token, day_start);

        call.enqueue(new Callback<ArrayList<EventsRes>>() {
            @Override
            public void onResponse(Call<ArrayList<EventsRes>> call, Response<ArrayList<EventsRes>> response) {
                if(response.code() == 401){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                if (response.isSuccessful()) {
                    eventsRes = response.body();
                    if(eventsRes.isEmpty()){
                        lnEmpty.setVisibility(View.VISIBLE);
                        main_scroll.setVisibility(View.GONE);
                    } else {
                        lnEmpty.setVisibility(View.GONE);
                        main_scroll.setVisibility(View.VISIBLE);
                        for (EventsRes eventsres : eventsRes) {
                            EventsAdapter adapter = new EventsAdapter(EventsFragment.this.getContext(), eventsRes);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<EventsRes>> call, Throwable t) {
            }
        });
    }

    public void filterData(){

        Call<ArrayList<EventsRes>> call = Global.getAPIService.filterWeeklySchedule("Token " + Global.token, client_id , user_id, vehicle_id, day_start);

        call.enqueue(new Callback<ArrayList<EventsRes>>() {
            @Override
            public void onResponse(Call<ArrayList<EventsRes>> call, Response<ArrayList<EventsRes>> response) {
                if(response.code() == 401){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                Log.d("######",response.code()+"");
                Log.d("######",response.body().toString());
                if (response.isSuccessful()) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    eventsRes = response.body();
                    Log.d("Success",eventsRes.toString());
                    if(eventsRes.isEmpty()){
                        lnEmpty.setVisibility(View.VISIBLE);
                        main_scroll.setVisibility(View.GONE);
                    } else {
                        lnEmpty.setVisibility(View.GONE);
                        main_scroll.setVisibility(View.VISIBLE);
                        for (EventsRes eventsres : eventsRes) {
                            Log.d("Success",eventsres.toJSON());
                            EventsAdapter adapter = new EventsAdapter(EventsFragment.this.getContext(), eventsRes);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<EventsRes>> call, Throwable t) {
            }
        });
    }

    public void getfilterString(){
        user_id = new ArrayList<Integer>();
        client_id = new ArrayList<Integer>();
        vehicle_id = new ArrayList<Integer>();

        List<String> splited_client = spAccomm.getSelectedStrings();
        List<String> splited_vehicle = spVehicle.getSelectedStrings();
        List<String> splited_user = spEmployees.getSelectedStrings();
        if(!spAccomm.getSelectedStrings().isEmpty()) {

            ArrayList<Client> array_client = Global.clients;
            for (Client client: array_client){
                for(String sel: splited_client){
                    if(client.getName().equals(sel)){
                        client_id.add(client.getId());
                    }
                }
            }
        } else {
            client_id = null;
        }

        if(!spVehicle.getSelectedStrings().isEmpty()) {
            ArrayList<Vehicle> array_vehicle = Global.vehicles;
            for (Vehicle vehicle: array_vehicle){
                for(String sel: splited_vehicle){
                    if(vehicle.getName().equals(sel)){
                        vehicle_id.add(vehicle.getId());
                    }
                }
            }
        } else {
            vehicle_id = null;
        }

        if(!spEmployees.getSelectedStrings().isEmpty()) {
            ArrayList<AuthorRes> array_user = Global.users;
            for (AuthorRes authorRes: array_user){
                for(String sel: splited_user){
                    if(authorRes.getName().equals(sel)){
                        user_id.add(authorRes.getId());
                    }
                }
            }
        } else {
            user_id = null;
        }
    }
}
