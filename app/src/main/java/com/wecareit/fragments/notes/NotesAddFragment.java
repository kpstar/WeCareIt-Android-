package com.wecareit.fragments.notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wecareit.MultiSpinner;
import com.wecareit.R;
import com.wecareit.common.Global;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.model.Major_Keyword;
import com.wecareit.model.Minor_Keywords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotesAddFragment extends TemplateFragment implements MultiSpinner.MultiSpinnerListener, Spinner.OnItemSelectedListener {

    private LinearLayout lnAccomo, lnGenerakkey, lnSpecifickey, lnCategory, lnArea;
    private Spinner spGeneralkey, spArea, spSpecifickey, spCategory;
    private MultiSpinner spAccomo;
    private Context mContext;
    private String mSummary, mDetail;
    private int area_id, general_id, specific_id, category_id, client_id;
    private boolean isBackDated;
    private static int AREA_SPINNER_ID = 1001;
    private static int GENERAL_SPINNER_ID = 1002;
    private static int SPECIFIC_SPINNER_ID = 1003;
    private static int CATEGORY_SPINNER_ID = 1004;
    private ArrayList<Minor_Keywords> minor_keywords;

    public static NotesAddFragment createInstance() {
        return new NotesAddFragment();
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
        return inflater.inflate(R.layout.fragment_notes_add, container, false);
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

        lnAccomo = view.findViewById(R.id.lnAccommo_notesaddfragment);
        lnArea = view.findViewById(R.id.lnArea_notesaddfragment);
        lnCategory = view.findViewById(R.id.lnCategory_notesaddfragment);
        lnGenerakkey = view.findViewById(R.id.lnGeneralkey_notesaddfragment);
        lnSpecifickey = view.findViewById(R.id.lnSpecificKey_notesaddfragment);

        spArea = view.findViewById(R.id.arearspinner_notesaddfragment);
        spArea.setId(AREA_SPINNER_ID);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.areaslist);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spArea.setAdapter(adapter);
        spArea.setOnItemSelectedListener(this);

        spCategory = view.findViewById(R.id.categoryspinner_notesaddfragment);
        spCategory.setId(CATEGORY_SPINNER_ID);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.main_categorieslist);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spCategory.setAdapter(adapter1);
        spCategory.setOnItemSelectedListener(this);

        spGeneralkey = view.findViewById(R.id.generalkeyspinner_notesaddfragment);
        spGeneralkey.setId(GENERAL_SPINNER_ID);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.major_keywordslist);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spGeneralkey.setAdapter(adapter2);
        spGeneralkey.setOnItemSelectedListener(this);

        spSpecifickey = view.findViewById(R.id.specifickeyspinner_notesaddfragment);
        spSpecifickey.setId(SPECIFIC_SPINNER_ID);
        spSpecifickey.setOnItemSelectedListener(this);

        spAccomo = view.findViewById(R.id.accommospinner_notesaddfragment);
        spAccomo.setItems(Global.clientslist, getString(R.string.all), this);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2,Color.LTGRAY);
        gd.setCornerRadius(5.0f);
        lnAccomo.setBackground(gd);
        lnArea.setBackground(gd);
        lnCategory.setBackground(gd);
        lnGenerakkey.setBackground(gd);
        lnSpecifickey.setBackground(gd);
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
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int sp_id = adapterView.getId();
        if (sp_id == AREA_SPINNER_ID) {
            area_id = i + 1;
        } else if (sp_id == GENERAL_SPINNER_ID) {
            general_id = i + 1;
            getSpecificKey();
        } else if (sp_id == SPECIFIC_SPINNER_ID) {
            specific_id = i + 1;
        } else if (sp_id == CATEGORY_SPINNER_ID) {
            category_id = i + 1;
        } else {
            return;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getSpecificKey() {
        Call<ArrayList<Minor_Keywords>> call_minorkeyword = Global.getAPIService.readMinorKeywords("Token " + Global.token, general_id);
        call_minorkeyword.enqueue(new Callback<ArrayList<Minor_Keywords>>() {
            @Override
            public void onResponse(Call<ArrayList<Minor_Keywords>> call, Response<ArrayList<Minor_Keywords>> response) {
                if (response.isSuccessful()) {
                    minor_keywords = response.body();
                    Collections.sort(minor_keywords, new Comparator<Minor_Keywords>() {
                        @Override
                        public int compare(Minor_Keywords a, Minor_Keywords b) {
                            return (a.getId() >  b.getId() ? 1: -1);
                        }
                    });
                    for (Minor_Keywords minor_keyword : minor_keywords) {
                        Global.minor_keywordslist.add(minor_keyword.getTitle());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, Global.minor_keywordslist);
                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    spSpecifickey.setAdapter(adapter);
                } else {
                    Log.e(Global.TAG, response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Minor_Keywords>> call, Throwable t) {
            }
        });
    }
}
