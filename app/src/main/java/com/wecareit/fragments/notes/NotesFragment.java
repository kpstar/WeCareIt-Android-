package com.wecareit.fragments.notes;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wecareit.LoginActivity;
import com.wecareit.MultiSpinner;
import com.wecareit.R;
import com.wecareit.adapter.NotesAdapter;
import com.wecareit.adapter.SpinnerAdapter;
import com.wecareit.common.Global;
import com.wecareit.common.MyDate;
import com.wecareit.fragments.DatePickerFragment;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.model.NotesRes;
import com.wecareit.model.Spinners;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesFragment extends TemplateFragment {

    private ArrayList<NotesRes> notesRes;

    private RecyclerView mRecyclerView;
    Calendar myCalendar = Calendar.getInstance();
    private ScrollView main_scrollview;
    private Spinner spTimeinterval;
    private MultiSpinner spAccommodation, spKeyword, spCategory, spArea;
    private LinearLayout lnEmpty, lnAccom, lnArea, lnTime, lnKeyword, lnCategory;
    private CardView lnExpand;
    private static int flag_expand = 0;
    private String nowdate;
    private TextView tvEmpty;
    private Button btnClear, btnSubmit;
    private NotesAdapter adapter;
    private HashMap<String, String> params;
    private ArrayList<String> clients, area, category, keyword;
    private ConstraintLayout clMessage;
    private TextView txtMsg;
    private Button btnMsgClose;

    public static NotesFragment createInstance() {
        return new NotesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // this is how we load a specific layout
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    public void onItemsSelected(boolean[] selected){
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Global.toolbar.setNavigationIcon(R.drawable.ic_side_menu);
        Global.toolbar.setTitle("ANTECKNINGAR");

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Message", Context.MODE_PRIVATE);
        String messageStr = sharedPreferences.getString("noteFragment", "");

        clMessage = view.findViewById(R.id.clMessage);
        btnMsgClose = (Button) view.findViewById(R.id.closeMessageBtn);
        btnMsgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clMessage.setVisibility(View.GONE);
            }
        });
        txtMsg = (TextView) view.findViewById(R.id.addMessage);
        if (!messageStr.isEmpty()) {
            txtMsg.setText(messageStr);
            clMessage.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("noteFragment", "");
            editor.apply();
        }

        Global.monthlyButton.setVisibility(View.VISIBLE);
        Global.monthlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.monthlySummeryFragment = MonthlySummeryFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.monthlySummeryFragment)
                        .addToBackStack(null)
                        .commit();
                Global.monthlyButton.setVisibility(View.GONE);
            }
        });

        Global.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.notesAddFragment = NotesAddFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.notesAddFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        spAccommodation = view.findViewById(R.id.accommodationspinner_notesfragment);
        spAccommodation.setItems(Global.clientslist, getString(R.string.all));

        spArea = view.findViewById(R.id.areaspinner_notesfragment);
        spArea.setItems(Global.areaslist, getString(R.string.all));

        spKeyword = view.findViewById(R.id.keywordspinner_notesfragment);
        spKeyword.setItems(Global.major_keywordslist, getString(R.string.all));

        spCategory = view.findViewById(R.id.categoryspinner_notesfragment);
        spCategory.setItems(Global.main_categorieslist, getString(R.string.all));

        spTimeinterval = view.findViewById(R.id.timeintervalspinner_notesfragment);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.notestimeintervals_array);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spTimeinterval.setAdapter(adapter);

        mRecyclerView = view.findViewById(R.id.framgment_notes_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        lnExpand = view.findViewById(R.id.expandLinear_notesfragment);
        lnEmpty = view.findViewById(R.id.emptylinear_notesfragment);
        lnAccom = view.findViewById(R.id.lnAccommodation_notesfragment);
        lnArea = view.findViewById(R.id.lnArea_notesfragment);
        lnCategory = view.findViewById(R.id.lnCategory_notesfragment);
        lnKeyword = view.findViewById(R.id.lnKeyword_notesfragment);
        lnTime = view.findViewById(R.id.lnTimeinterval_notesfragment);

        tvEmpty = view.findViewById(R.id.tvEmpty_notesfragment);
        main_scrollview = view.findViewById(R.id.scrollview_notesfragment);

        btnClear = view.findViewById(R.id.btnClear_notesfragment);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_expand = 0;
                lnExpand.setVisibility(View.GONE);
            }
        });
        btnSubmit = view.findViewById(R.id.btnSubmit_notesfragment);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_expand = 0;
                lnExpand.setVisibility(View.GONE);
                //postNote();
                filterNote();
            }
        });

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        nowdate = df.format(c);
        tvEmpty.setText("Inga anteckningar än för " + nowdate);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2,Color.LTGRAY);
        gd.setCornerRadius(5.0f);

        tvEmpty.setBackground(gd);

        GradientDrawable gd1 = new GradientDrawable();
        gd1.setShape(GradientDrawable.RECTANGLE);
        gd1.setStroke(2,Color.LTGRAY);
        gd1.setCornerRadius(5.0f);
        lnExpand.setBackground(gd1);

        //showDate(year, month+1, day);

        loadData(nowdate);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_notes_filter, menu);
        this.setHasOptionsMenu(true);
        if (menu == null) {
            System.out.println("menu is null");
            return;
        }
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.getItem(0);
        View view = item.getActionView();
        myCalendar = Calendar.getInstance();
        ImageView mCalendar = (ImageView) view.findViewById(R.id.menu_item_notes_filter_datepicker);
        mCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
                /*DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
                Log.d("DDDDDD",nowdate);*/
            }
        });
        ImageView mPrevDate = (ImageView) view.findViewById(R.id.menu_item_notes_filter_prev);
        //mPrevDate = menu.getItem(R.menu.menu_calendar).getActionView().findViewById(R.id.calendar_menu_item_prev);
        mPrevDate.setOnClickListener(new View.OnClickListener() {
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
                calendar.add(Calendar.DATE, -1);
                nowdate = dateFormat.format(calendar.getTime());
                tvEmpty.setText("Inga anteckningar än för " + nowdate);

                loadData(nowdate);
            }
        });

        ImageView mNextDate = (ImageView) view.findViewById(R.id.menu_item_notes_filter_next);
        //if (_selectedDate.isToday()) mNextDate.setVisibility(View.GONE);
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
                tvEmpty.setText("Inga anteckningar än för " + nowdate);

                loadData(nowdate);
            }
        });

        ImageView mExpande = (ImageView) view.findViewById(R.id.menu_item_notes_filter_expand);
        //if (_selectedDate.isToday()) mNextDate.setVisibility(View.GONE);
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
            tvEmpty.setText("Inga anteckningar än för " + nowdate);
            loadData(nowdate);
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

    public void loadData(String now_date) {

        Log.e("Now Date = ", now_date);
        Call<ArrayList<NotesRes>> call = Global.getAPIService.readNotes("Token " + Global.token, now_date);

        call.enqueue(new Callback<ArrayList<NotesRes>>() {
            @Override
            public void onResponse(Call<ArrayList<NotesRes>> call, Response<ArrayList<NotesRes>> response) {

                if(response.code() == 401){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

                if (response.isSuccessful()) {
                    notesRes = response.body();
                    //Log.d("Success",notesRes.toString());
                    if(notesRes.isEmpty()){
                        Log.d("empty",notesRes.toString());
                        lnEmpty.setVisibility(View.VISIBLE);
                        main_scrollview.setVisibility(View.GONE);
                    } else {
                        lnEmpty.setVisibility(View.GONE);
                        main_scrollview.setVisibility(View.VISIBLE);
                        mRecyclerView.invalidate();
                        for (NotesRes notes : notesRes) {
                            NotesAdapter adapter = null;
                            adapter = new NotesAdapter(NotesFragment.this.getContext(), notesRes);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<NotesRes>> call, Throwable t) {
            }
        });
    }

    public void filterNote() {

        params = new HashMap<String, String>();
        params.put("date", nowdate);

        if (spTimeinterval.getSelectedItemPosition() > 0) {
            params.put("time_range", String.valueOf(spTimeinterval.getSelectedItemPosition()));
        }

        clients = new ArrayList<String>();
        for (int i=0; i<spAccommodation.getItems().size(); i++) {
            if (spAccommodation.getSelected()[i] == true) {
                clients.add(String.valueOf(i + 1));
            }
        }

        category = new ArrayList<String>();
        for (int i=0; i<spCategory.getItems().size(); i++) {
            if (spCategory.getSelected()[i] == true) {
                category.add(String.valueOf(i + 1));
            }
        }

        keyword = new ArrayList<String>();
        for (int i=0; i<spKeyword.getItems().size(); i++) {
            if (spKeyword.getSelected()[i] == true) {
                keyword.add(String.valueOf(i + 1));
            }
        }

        area = new ArrayList<String>();
        for (int i=0; i<spArea.getItems().size(); i++) {
            if (spArea.getSelected()[i] == true) {
                area.add(String.valueOf(i + 1));
            }
        }

        Call<ArrayList<NotesRes>> call = Global.getAPIService.filterNotes("Token " + Global.token, clients, area, category, keyword, params);

        call.enqueue(new Callback<ArrayList<NotesRes>>() {
            @Override
            public void onResponse(Call<ArrayList<NotesRes>> call, Response<ArrayList<NotesRes>> response) {

                if(response.code() == 401){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

                Log.d("Success",""+response.body().toArray());
                if (response.isSuccessful()) {
                    notesRes = response.body();

                    if(notesRes.isEmpty()){
                        lnEmpty.setVisibility(View.VISIBLE);
                        main_scrollview.setVisibility(View.GONE);
                    } else {
                        lnEmpty.setVisibility(View.GONE);
                        main_scrollview.setVisibility(View.VISIBLE);
                        for (NotesRes notes : notesRes) {
                            adapter = null;
                            adapter = new NotesAdapter(NotesFragment.this.getContext(), notesRes);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<NotesRes>> call, Throwable t) {
            }
        });
    }


}
