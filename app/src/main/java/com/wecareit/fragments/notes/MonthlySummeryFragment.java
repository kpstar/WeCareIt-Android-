package com.wecareit.fragments.notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.wecareit.LoginActivity;
import com.wecareit.MultiSpinner;
import com.wecareit.R;
import com.wecareit.common.Global;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.model.Major_Keyword;
import com.wecareit.model.Minor_Keywords;
import com.wecareit.model.MonthlyKeyword;
import com.wecareit.model.NotePost;
import com.wecareit.model.NotesRes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MonthlySummeryFragment extends TemplateFragment implements MultiSpinner.MultiSpinnerListener, Spinner.OnItemSelectedListener {

    private LinearLayout lnYear, lnMonth, lnUsers, lnCategory, lnArea, lnKeyword;
    private int year, month, user_id;
    private Spinner spYear, spMonth, spUsers;
    private MultiSpinner msCategory, msArea, msKeyword;
    private Context mContext;
    private String mSummary;
    private ArrayList<MonthlyKeyword> keywords;
    private static int YEAR_SPINNER_ID = 2001;
    private static int MONTH_SPINNER_ID = 2002;
    private static int USERS_SPINNER_ID = 2003;
    private EditText edSummary;

    public static MonthlySummeryFragment createInstance() {
        return new MonthlySummeryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // this is how we load a specific layout
        return inflater.inflate(R.layout.fragment_monthly_summery, container, false);
    }

    public void onItemsSelected(boolean[] selected){
    };

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Global.toolbar.setNavigationIcon(R.drawable.ic_side_menu);
        Global.toolbar.setTitle("SKRIV ANTECKNING");
        Global.floatingButton.setVisibility(View.GONE);
        Global.monthlyButton.setVisibility(View.GONE);

        edSummary = (EditText)view.findViewById(R.id.etSummary_month);

        lnYear = view.findViewById(R.id.lnYear_month);
        lnMonth = view.findViewById(R.id.lnMonth_month);
        lnUsers = view.findViewById(R.id.lnUsers_month);
        lnCategory = view.findViewById(R.id.lnCategory_month);
        lnArea = view.findViewById(R.id.lnArea_month);
        lnKeyword = view.findViewById(R.id.lnKeyword_month);

        msArea = view.findViewById(R.id.areaSpinner_month);
        msArea.setItems(Global.clientslist, getString(R.string.all), this);

        msCategory = view.findViewById(R.id.categorySpinner_month);
        msCategory.setItems(Global.main_categorieslist, getString(R.string.all), this);

        msKeyword = view.findViewById(R.id.keywordSpinner_month);


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.areaslist);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        msArea.setAdapter(adapter);
//        spArea.setOnItemSelectedListener(this);

        spYear = view.findViewById(R.id.yearSpinnerMonth);
        spYear.setId(YEAR_SPINNER_ID);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.years);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spYear.setAdapter(adapter1);
        spYear.setOnItemSelectedListener(this);

        spMonth = view.findViewById(R.id.monthSpinner_month);
        spMonth.setId(MONTH_SPINNER_ID);
        adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.months);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spMonth.setAdapter(adapter1);
        spMonth.setOnItemSelectedListener(this);

        spUsers = view.findViewById(R.id.userSpinner_month);
        spUsers.setId(USERS_SPINNER_ID);
        String[] musers = new String[Global.clientslist.size()];
        musers = Global.clientslist.toArray(musers);
        adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, musers);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spUsers.setAdapter(adapter1);
        spUsers.setOnItemSelectedListener(this);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2,Color.LTGRAY);
        gd.setCornerRadius(5.0f);
        lnMonth.setBackground(gd);
        lnArea.setBackground(gd);
        lnCategory.setBackground(gd);
        lnKeyword.setBackground(gd);
        lnYear.setBackground(gd);
        lnUsers.setBackground(gd);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_save, menu);
//        this.setHasOptionsMenu(true);
//        if (menu == null) {
//            System.out.println("menu is null");
//            return;
//        }
//        super.onCreateOptionsMenu(menu, inflater);
//        MenuItem item = menu.getItem(0);
//        View view = item.getActionView();
//        TextView tv_save = (TextView) view.findViewById(R.id.btnSave_eventsmenu);
//        tv_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveData();
//            }
//        });
//    }

//    private void saveData() {
//        Map<String, String> params = new HashMap<>();
//        client_ids = new ArrayList<String>();
//        for (int i=0; i<spAccomo.getItems().size(); i++) {
//            if (spAccomo.getSelected()[i] == true) {
//                client_ids.add(String.valueOf(i + 1));
//            }
//        }
//
//
//        mSummary = edSummary.getText().toString();
//        mDetail = edDetail.getText().toString();
//        isBackDated = backDated.isChecked();
//
//        NotePost post = new NotePost(area_id, client_ids, category_id, general_id, specific_id, mSummary, mDetail, isBackDated);
//
//
//        Call<NotesRes> apiCall = Global.getAPIService.writeNotes("Token " + Global.token, post);
//        apiCall.enqueue(new Callback<NotesRes>() {
//            @SuppressLint("RestrictedApi")
//            @Override
//            public void onResponse(Call<NotesRes> call, Response<NotesRes> response) {
//                if(response.code() == 401){
//                    Intent intent = new Intent(getActivity(), LoginActivity.class);
//                    startActivity(intent);
//                    getActivity().finish();
//                }
//                notesRes = response.body();
//
//                SharedPreferences.Editor editor = getContext().getSharedPreferences("Message", Context.MODE_PRIVATE).edit();
//                editor.putString("noteFragment", "Ny anteckning sparad.");
//                editor.apply();
//
//                FragmentTransaction tx = getFragmentManager().beginTransaction();
//                tx.replace(R.id.fragment_container, Global.notesFragment).addToBackStack(null).commit();
//                Global.floatingButton.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call<NotesRes> call, Throwable t) {
//                Log.d("$$$$$$#####","Failed");
//            }
//        });
//    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int sp_id = adapterView.getId();
        if (sp_id == YEAR_SPINNER_ID) {
            year = 2018 - i;
            getKeywords();
        } else if (sp_id == MONTH_SPINNER_ID) {
            month = i + 1;
            getKeywords();
        } else if (sp_id == USERS_SPINNER_ID) {
            user_id = i + 1;
            getKeywords();
        } else {
            return;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getKeywords() {
        Log.d("cl year, month", String.format("%d/%d/%d", user_id, year, month));
        Call<ArrayList<MonthlyKeyword>> call_minorkeyword = Global.getAPIService.readMonthlyKeywords("Token " + Global.token, user_id, year, month);
        call_minorkeyword.enqueue(new Callback<ArrayList<MonthlyKeyword>>() {
            @Override
            public void onResponse(Call<ArrayList<MonthlyKeyword>> call, Response<ArrayList<MonthlyKeyword>> response) {
                if (response.isSuccessful()) {
                    keywords = response.body();
                    Log.d("Keywords = ", String.valueOf(keywords.size()));
                    Global.month_majorKeywordList = new ArrayList<String>();
                    Collections.sort(keywords, new Comparator<MonthlyKeyword>() {
                        @Override
                        public int compare(MonthlyKeyword a, MonthlyKeyword b) {
                            return (a.getId() >  b.getId() ? 1: -1);
                        }
                    });
                    for (MonthlyKeyword keyword : keywords) {
                        Log.e("Keyword =", keyword.getLabel());
                        Global.month_majorKeywordList.add(keyword.getLabel());
                    }
                    msKeyword.setItems(Global.month_majorKeywordList, getString(R.string.all), (MultiSpinner.MultiSpinnerListener)MonthlySummeryFragment.this);
                } else {
                    Log.e(Global.TAG, response.message());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<MonthlyKeyword>> call, Throwable t) {
            }
        });
    }
}
