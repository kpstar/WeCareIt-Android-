package com.wecareit.fragments.attendance;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wecareit.R;
import com.wecareit.adapter.AttendanceAdapter;
import com.wecareit.common.Global;
import com.wecareit.common.Message;
import com.wecareit.common.MyDate;
import com.wecareit.fragments.DatePickerFragment;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.model.Attendance;
import com.wecareit.model.AttendanceUpdateBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceFragment extends TemplateFragment {

    private MyDate _selectedDate = new MyDate();
    Calendar myCalendar = Calendar.getInstance();
    private String nowdate;
    private TextView mSelectedDateView;
    private TextView mSubmitStatusView;
    private RecyclerView mRecyclerView;
    private Button mSubmit;
    private Button mSave;
    private ImageView mPrevDate;
    private ImageView mCalendar;
    private ImageView mNextDate;
    private RelativeLayout rlTime;

    private DatePickerDialog mDatePickerDialog;

    public static AttendanceFragment createInstance() {
        return new AttendanceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // this is how we load a specific layout
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _selectedDate = MyDate.getInstance();

        Global.toolbar.setTitle("NÄRVAROLISTA");

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        nowdate = df.format(c);

        mSelectedDateView = (TextView) getView().findViewById(R.id.fragment_attendance_date);
        mSubmitStatusView = (TextView) getView().findViewById(R.id.fragment_attendance_submit_status);
        String day = Global.dateString(nowdate);
        mSelectedDateView.setText(day);
        //mSubmitStatusView.setText(" - ej inskickad");

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.fragment_attendance_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rlTime = (RelativeLayout) getView().findViewById(R.id.fragment_attendance_date_board);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(3,Color.LTGRAY);
        gd.setCornerRadius(5.0f);
        rlTime.setBackground(gd);

        mSubmit = (Button) getView().findViewById(R.id.fragment_attendance_submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Attendance body = new Attendance(((AttendanceAdapter) mRecyclerView.getAdapter()).getAttendedClients(), true);
                Message.showObject(body);

                Call<ResponseBody> call = Global.getAPIService.updateAttendance("Token " + Global.token, _selectedDate.toString(MyDate.MyDateFormat.ISO8601), body);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Message.showObject(response.message());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        mSave = (Button) getView().findViewById(R.id.fragment_attendance_save);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Attendance body = new Attendance(((AttendanceAdapter) mRecyclerView.getAdapter()).getAttendedClients(), false);
                Message.showObject(body);

                Call<ResponseBody> call = Global.getAPIService.updateAttendance("Token " + Global.token, _selectedDate.toString(MyDate.MyDateFormat.ISO8601), body);
                call.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Message.showObject(response.message());
                        Log.d("response_code",""+response.code());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_calendar, menu);
        this.setHasOptionsMenu(true);
        if (menu == null) {
            System.out.println("menu is null");
            return;
        }
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.getItem(0);
        View view = item.getActionView();
        mPrevDate = (ImageView) view.findViewById(R.id.calendar_menu_item_prev);
        //mPrevDate = menu.getItem(R.menu.menu_calendar).getActionView().findViewById(R.id.calendar_menu_item_prev);
        mPrevDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = dateFormat.parse(nowdate);
                }catch (Exception ex){

                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, -1);
                nowdate = dateFormat.format(calendar.getTime());
                //tvEmpty.setText("Inga anteckningar än för " + nowdate);

                loadData();
                if (mNextDate.getVisibility() == View.GONE) {
                    mNextDate.setVisibility(View.VISIBLE);
                }
            }
        });

        mCalendar = (ImageView) view.findViewById(R.id.calendar_menu_item_calendar);
        mCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        mNextDate = (ImageView) view.findViewById(R.id.calendar_menu_item_next);
        if (_selectedDate.isToday()) mNextDate.setVisibility(View.GONE);
        mNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    date = dateFormat.parse(nowdate);
                }catch (Exception ex){

                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, +1);
                nowdate = dateFormat.format(calendar.getTime());
                //tvEmpty.setText("Inga anteckningar än för " + nowdate);

                loadData();
                //_selectedDate.nextDate();
                /*if (_selectedDate.isToday()) {
                    mNextDate.setVisibility(View.GONE);
                } else {
                    mNextDate.setVisibility(View.VISIBLE);
                }*/
                loadData();
            }
        });

        //super.onCreateOptionsMenu(menu,inflater);
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            String year_now, month_now, day_now;
            year_now = String.valueOf(year);
            if((monthOfYear+1)<10){
                month_now = "0"+String.valueOf(monthOfYear + 1);
            } else {
                month_now = String.valueOf(monthOfYear + 1);
            }

            if(dayOfMonth<10){
                day_now = "0"+String.valueOf(dayOfMonth);
            } else {
                day_now = String.valueOf(dayOfMonth);
            }
            nowdate = year_now + "-" + month_now + "-" + day_now;
            //tvEmpty.setText("Inga anteckningar än för " + nowdate);
            loadData();
        }
    };

    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

        /*((TextView) findViewById(R.id.showDate))
                .setText(dateFormat.format(calendar.getTime()));*/
        nowdate = dateFormat.format(calendar.getTime());
        //loadData(nowdate);
        //Log.d("nowday", nowdate);
    }

    private void loadData() {
        mSelectedDateView.setText(Global.dateString(nowdate));

        Call<Attendance> call = Global.getAPIService.readAttendance("Token " + Global.token, nowdate);
        call.enqueue(new Callback<Attendance>() {

            @Override
            public void onResponse(Call<Attendance> call, Response<Attendance> response) {
                if (response.isSuccessful()) {
                    if (response != null && response.body().getAttendedClients() != null) {
                        Log.e(Global.TAG, response.body().toJSON());
                        AttendanceAdapter adapter = new AttendanceAdapter(AttendanceFragment.this.getContext(), response.body().getAttendedClients());
                        adapter.setSubmitted(response.body().isSubmitted());
                        if (response.body().isSubmitted()) {
                            mSave.setVisibility(View.GONE);
                            mSubmit.setVisibility(View.GONE);
                            mSubmitStatusView.setTextColor(getResources().getColor(R.color.colorGreen));
                            mSubmitStatusView.setText(" - inskickad");
                        } else {
                            mSubmitStatusView.setTextColor(getResources().getColor(R.color.colorOrange));
                            mSubmitStatusView.setText(" - ej inskickad");
                            mSave.setVisibility(View.VISIBLE);
                            mSubmit.setVisibility(View.VISIBLE);
                        }
                        mRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<Attendance> call, Throwable t) {
                //Log.e(Global.TAG, t.toString());
            }
        });
    }

    /*@Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        _selectedDate = new MyDate(year, month, dayOfMonth);
        loadData();
    }*/
}
