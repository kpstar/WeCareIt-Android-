package com.wecareit.fragments.events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.chinalwb.are.AREditText;
import com.chinalwb.are.styles.toolbar.IARE_Toolbar;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Bold;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Italic;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_ListBullet;
import com.chinalwb.are.styles.toolitems.IARE_ToolItem;
import com.wecareit.LoginActivity;
import com.wecareit.MultiSelectSpinner;
import com.wecareit.MultiSpinner;
import com.wecareit.R;
import com.wecareit.adapter.EventsAdapter;
import com.wecareit.common.Global;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.model.AuthorRes;
import com.wecareit.model.Client;
import com.wecareit.model.Events;
import com.wecareit.model.EventsRes;
import com.wecareit.model.EventspostBody;
import com.wecareit.model.Timeperiod;
import com.wecareit.model.Vehicle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.noties.markwon.Markwon;

public class EventsUpdateFragment extends TemplateFragment {

    private Spinner spTimeinterval;
    private MultiSpinner spVehicle, spEmployee, spClient;
    private EditText etHeader, etStartdate, etStarttime;
    private AREditText etDescription;
    private IARE_Toolbar mToolbar;
    private LinearLayout lnUser, lnVehicle, lnTimeinterval,lnClient, lnHeader, lnDate, lnTime;
    private EventspostBody postBody;
    private TextView tvHeading, tvDesc;
    private Timeperiod timeperiod;
    private ArrayList<Integer> user_id,vehicle_id, client_id;
    private String time_start, time_end, name,description;

    private Calendar myCalendar = Calendar.getInstance();

    public static EventsUpdateFragment createInstance() {
        return new EventsUpdateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_events_update, container, false);
    }

    public void onItemsSelected(boolean[] selected){
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Global.toolbar.setTitle("SKAPA AKTIVITET");
        Global.floatingButton.setVisibility(View.GONE);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etHeader = (EditText) view.findViewById(R.id.editHeader_eventsupdatefragment);
        etDescription = view.findViewById(R.id.arEditText);
        etStartdate = (EditText) view.findViewById(R.id.editDatePicker_eventsupdatefragment);
        etStarttime = (EditText) view.findViewById(R.id.editTimePicker_eventsupdatefragment);

        spTimeinterval = (Spinner) view.findViewById(R.id.timeintervalSpinner_eventsupdatefragment);
        spVehicle = (MultiSpinner) view.findViewById(R.id.vehicleSpinner_eventsupdatefragment);
        spEmployee = (MultiSpinner) view.findViewById(R.id.userSpinner_eventsupdatefragment);
        spClient = (MultiSpinner) view.findViewById(R.id.clientSpinner_eventsupdatefragment);

        tvDesc = (TextView) view.findViewById(R.id.txtDescription);
        tvHeading = (TextView) view.findViewById(R.id.txtHeading);

        Date mDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        etStartdate.setText(df.format(mDate));

        mToolbar = view.findViewById(R.id.areToolbar);
        IARE_ToolItem bold = new ARE_ToolItem_Bold();
        IARE_ToolItem italic = new ARE_ToolItem_Italic();
        IARE_ToolItem listBullet = new ARE_ToolItem_ListBullet();
        mToolbar.addToolbarItem(bold);
        mToolbar.addToolbarItem(italic);
        mToolbar.addToolbarItem(listBullet);

        etDescription.setToolbar(mToolbar);

        etStartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EventsUpdateFragment.this.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etStarttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EventsUpdateFragment.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String selectedhr,selectedmin;
                        if(selectedHour<10){
                            selectedhr = "0" + selectedHour;
                        } else {
                            selectedhr = "" + selectedHour;
                        }
                        if(selectedMinute<10){
                            selectedmin = "0" + selectedMinute;
                        } else {
                            selectedmin = "" + selectedMinute;
                        }
                        etStarttime.setText( selectedhr + ":" + selectedmin);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        if (EventsFragment.flag_update == 1){
            etHeader.setText(EventsFragment.name_update);
//            etDescription.setText(EventsFragment.description_update);
            Markwon.setMarkdown(etDescription, EventsFragment.description_update.replace("\"", ""));
            etStartdate.setText(EventsFragment.startdate_update.substring(0,10));
            int hr1 = Integer.valueOf(EventsFragment.startdate_update.substring(11,13));
            int min1 = Integer.valueOf(EventsFragment.startdate_update.substring(14,16));
            int hr2 = Integer.valueOf(EventsFragment.enddate_update.substring(11,13));
            int min2 = Integer.valueOf(EventsFragment.enddate_update.substring(14,16));
            etStarttime.setText(EventsFragment.startdate_update.substring(11,16));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.eventsupdatetimeintervals_array);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spTimeinterval.setAdapter(adapter);
            spTimeinterval.setSelection(4);
            spVehicle.setItemsWithOptions(Global.vehicleslist, EventsFragment.vehicles_update);
//            spVehicle.setSelection(EventsFragment.vehicles_update);
            spEmployee.setItemsWithOptions(Global.userslist, EventsFragment.clients_update);
            spClient.setItemsWithOptions(Global.clientslist, EventsFragment.clients_update);
//            spEmployee.setSelection(EventsFragment.clients_update);
//            spClient.setSelection(EventsFragment.clients_update);
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.eventsupdatetimeintervals_array);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            spTimeinterval.setAdapter(adapter);
            spVehicle.setItems(Global.vehicleslist, "Alla");
            spEmployee.setItems(Global.userslist, "Alla");
            spClient.setItems(Global.clientslist, "Alla");
        }

        lnTimeinterval = (LinearLayout) view.findViewById(R.id.lnTimeinterval_eventupdate);
        lnVehicle = (LinearLayout) view.findViewById(R.id.lnVehicle_eventupdate);
        lnUser = (LinearLayout) view.findViewById(R.id.lnUser_eventupdate);
        lnClient = (LinearLayout) view.findViewById(R.id.lnClient_eventupdate);
        lnHeader = (LinearLayout) view.findViewById(R.id.lnHeader_eventsupdate);
        lnDate = (LinearLayout) view.findViewById(R.id.lnDate_eventsupdate);
        lnTime = (LinearLayout) view.findViewById(R.id.lnTime_eventsupdate);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2,Color.LTGRAY);
        gd.setCornerRadius(3.0f);
        lnHeader.setBackground(gd);
        lnDate.setBackground(gd);
        lnTime.setBackground(gd);
        lnTimeinterval.setBackground(gd);
        lnVehicle.setBackground(gd);
        lnUser.setBackground(gd);
        lnClient.setBackground(gd);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        this.setHasOptionsMenu(true);
        if (menu == null) {
            System.out.println("menu is null");
            return;
        }
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.getItem(0);
        View view = item.getActionView();
        TextView tv_save = (TextView) view.findViewById(R.id.btnSave_eventsmenu);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsFragment.flag_update = 0;
                boolean flag = true;
                if(etHeader.getText().toString().isEmpty()){
                    tvHeading.setTextColor(getResources().getColor(R.color.colorCleaarBtn));
                    etHeader.setHint(R.string.errorHint);
                    etHeader.setHintTextColor(getResources().getColor(R.color.colorCleaarBtn));
                    flag = false;
                }
                if(etDescription.getText().toString().isEmpty()){
                    tvDesc.setTextColor(getResources().getColor(R.color.colorCleaarBtn));
                    etDescription.setHint(R.string.errorHint);
                    etDescription.setHintTextColor(getResources().getColor(R.color.colorCleaarBtn));
                    flag = false;
                }
                if (!flag) return;
                int min_span = 0, hr_span = 0, end_hr=0, end_min=0;
                String st_hr, st_min, st_enddate;
                time_start = etStartdate.getText().toString()+"T"+etStarttime.getText().toString();
                String time_span = spTimeinterval.getSelectedItem().toString();
                String[] splited = time_span.split("\\s+");
                if (splited[1].equals("minuter"))
                    min_span = Integer.valueOf(splited[0]);
                else {
                    hr_span = Integer.valueOf(splited[0]);
                    if(splited.length >3){
                        min_span = Integer.valueOf(splited[2]);
                    } else {
                        min_span = 0;
                    }
                }

                if (Integer.valueOf(etStarttime.getText().toString().substring(3,5)) + min_span>60){
                    end_min = Integer.valueOf(etStarttime.getText().toString().substring(3,5)) + min_span - 60;
                    hr_span = hr_span+1;
                } else {
                    end_min = Integer.valueOf(etStarttime.getText().toString().substring(3,5)) + min_span;
                }
                if (Integer.valueOf(etStarttime.getText().toString().substring(0,2)) + hr_span>24){
                    end_hr = Integer.valueOf(etStarttime.getText().toString().substring(0,2)) + hr_span - 24;
                    String dt = etStartdate.getText().toString();  // Start date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    try {
                        c.setTime(sdf.parse(dt));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    c.add(Calendar.DATE, 1);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    st_enddate = sdf1.format(c.getTime());
                } else {
                    st_enddate = etStartdate.getText().toString();
                    end_hr = Integer.valueOf(etStarttime.getText().toString().substring(0,2)) + hr_span;
                }
                if(end_hr<10){
                    st_hr = "0"+end_hr;
                } else {
                    st_hr = ""+end_hr;
                }
                if(end_min<10){
                    st_min = "0"+end_min;
                } else {
                    st_min = ""+end_min;
                }


                time_end = st_enddate+"T"+st_hr+":"+st_min;
                timeperiod = new Timeperiod(time_start,time_end);
                name = etHeader.getText().toString();
                description = etDescription.getText().toString();
                getIDist();

                if (description.isEmpty() || name.isEmpty() || time_end.isEmpty() || time_start.isEmpty()) {
                    Toast.makeText(getContext(), "Det här fältet är obligatoriskt.", Toast.LENGTH_LONG).show();
                    return;
                }
                //Log.d(time_start,time_end+name+description);
                if (EventsFragment.flag_update == 0){
                    postEvent();
                } else {
                    updateEvent();
                }
            }
        });
    }

    public void getIDist(){
        user_id = new ArrayList<Integer>();
        client_id = new ArrayList<Integer>();
        vehicle_id = new ArrayList<Integer>();
        String clients = spClient.getSelectedItem().toString();
        String[] splited_client = clients.split(", ");
        String vehicles = spVehicle.getSelectedItem().toString();
        String[] splited_vehicle = vehicles.split(", ");
        String users = spEmployee.getSelectedItem().toString();
        String[] splited_user = users.split(", ");

        for (int i=0; i<spVehicle.getItems().size(); i++) {
            if (spVehicle.getSelected()[i] == true) {
                vehicle_id.add(i + 1);
            }
        }

        for (int i=0; i<spClient.getItems().size(); i++) {
            if (spClient.getSelected()[i] == true) {
                client_id.add(i + 1);
            }
        }

        for (int i=0; i<spEmployee.getItems().size(); i++) {
            if (spEmployee.getSelected()[i] == true) {
                user_id.add(i + 1);
            }
        }

//        if(users.equals("Alla")){
//            for (AuthorRes authoRres : Global.users) {
//                user_id.add(authoRres.getId());
//            }
//        } else {
//            for (AuthorRes authoRres : Global.users) {
//                for (int i = 0; i < splited_user.length; i++) {
//                    //Log.d(splited_user[i], "" + authoRres.getName());
//                    if (splited_user[i].equals(authoRres.getName())) {
//                        user_id.add(authoRres.getId());
//                    }
//                }
//            }
//        }
//        if(clients.equals("Alla")){
//            for (Client client : Global.clients) {
//                client_id.add(client.getId());
//            }
//        } else {
//            for (int i = 0; i < splited_client.length; i++) {
//                for (Client client : Global.clients) {
//                    if (splited_client[i].equals(client.getName()))
//                        client_id.add(client.getId());
//                }
//            }
//        }
//        if(vehicles.equals("Alla")){
//            for (Vehicle vehicle : Global.vehicles) {
//                vehicle_id.add(vehicle.getId());
//            }
//        } else {
//            for (int i = 0; i < splited_vehicle.length; i++) {
//                for (Vehicle vehicle : Global.vehicles) {
//                    if (splited_vehicle[i].equals(vehicle.getName()))
//                        vehicle_id.add(vehicle.getId());
//                }
//            }
//        }

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etStartdate.setText(sdf.format(myCalendar.getTime()));
    }

    public void postEvent(){

        postBody =new EventspostBody(timeperiod, description, name, user_id, vehicle_id, client_id);

        Call<EventsRes> call = Global.getAPIService.writeWeeklySchedule("Token " + Global.token, postBody);

        call.enqueue(new Callback<EventsRes>() {
            @Override
            public void onResponse(Call<EventsRes> call, Response<EventsRes> response) {
                if(response.code() == 401){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                //Log.d("Success",""+response.code());
                if (response.isSuccessful()) {
                    Global.eventsFragment = EventsFragment.createInstance();
                    Global.fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, Global.eventsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
            @Override
            public void onFailure(Call<EventsRes> call, Throwable t) {
            }
        });
    }

    public void updateEvent(){
        postBody =new EventspostBody(timeperiod, description, name, user_id, vehicle_id, client_id);

        Call<EventsRes> call = Global.getAPIService.updateWeeklySchedule("Token " + Global.token, EventsFragment.id_update, postBody);

        call.enqueue(new Callback<EventsRes>() {
            @Override
            public void onResponse(Call<EventsRes> call, Response<EventsRes> response) {
                if(response.code() == 401){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                //Log.d("Success",""+response.code());
                if (response.isSuccessful()) {
                    EventsFragment.flag_update = 0;
                    Global.eventsFragment = EventsFragment.createInstance();
                    Global.fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, Global.eventsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
            @Override
            public void onFailure(Call<EventsRes> call, Throwable t) {
            }
        });
    }

}
