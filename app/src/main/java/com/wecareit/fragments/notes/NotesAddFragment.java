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
import android.widget.TextView;
import android.widget.Toast;

import com.chinalwb.are.AREditText;
import com.chinalwb.are.styles.toolbar.IARE_Toolbar;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Bold;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Italic;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_ListBullet;
import com.chinalwb.are.styles.toolitems.IARE_ToolItem;
import com.wecareit.LoginActivity;
import com.wecareit.MultiSpinner;
import com.wecareit.R;
import com.wecareit.common.Global;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.model.Minor_Keywords;
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


public class NotesAddFragment extends TemplateFragment implements Spinner.OnItemSelectedListener {

    private LinearLayout lnAccomo, lnGenerakkey, lnSpecifickey, lnCategory, lnArea;
    private Spinner spGeneralkey, spArea, spSpecifickey, spCategory;
    private MultiSpinner spAccomo;
    private Context mContext;
    private String mSummary, mDetail;
    private int area_id, general_id, specific_id, category_id;
    private List<String> client_ids;
    private boolean isBackDated;
    private IARE_Toolbar mToolbar;
    private TextView txtSummary, txtNote;
    private static int AREA_SPINNER_ID = 1001;
    private static int GENERAL_SPINNER_ID = 1002;
    private static int SPECIFIC_SPINNER_ID = 1003;
    private CheckBox backDated;
    private AREditText edDetail;
    private EditText edSummary;
    private static int CATEGORY_SPINNER_ID = 1004;
    private ArrayList<Minor_Keywords> minor_keywords;
    private NotesRes notesRes;

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
        Global.monthlyButton.setVisibility(View.GONE);

        edSummary = (EditText)view.findViewById(R.id.etSummary_notesaddfragment);

        edDetail = view.findViewById(R.id.arEditText);
        mToolbar = view.findViewById(R.id.areToolbar);
        IARE_ToolItem bold = new ARE_ToolItem_Bold();
        IARE_ToolItem italic = new ARE_ToolItem_Italic();
        IARE_ToolItem listBullet = new ARE_ToolItem_ListBullet();
        mToolbar.addToolbarItem(bold);
        mToolbar.addToolbarItem(italic);
        mToolbar.addToolbarItem(listBullet);

        edDetail.setToolbar(mToolbar);
        txtSummary = (TextView)view.findViewById(R.id.summaryText);
        txtNote = (TextView)view.findViewById(R.id.notesText);
        backDated = (CheckBox)view.findViewById(R.id.chxBackdated_noteaddfragment);

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
        spAccomo.setItems(Global.clientslist, getString(R.string.all));

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2,Color.LTGRAY);
        gd.setCornerRadius(5.0f);
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

    @SuppressLint("ResourceAsColor")
    private void saveData() {
        Map<String, String> params = new HashMap<>();
        client_ids = new ArrayList<String>();
        for (int i=0; i<spAccomo.getItems().size(); i++) {
            if (spAccomo.getSelected()[i] == true) {
                client_ids.add(String.valueOf(i + 1));
            }
        }


        mSummary = edSummary.getText().toString();
        mDetail = edDetail.getText().toString();
        boolean success = true;
        if (mSummary.isEmpty()) {
            txtSummary.setTextColor(getResources().getColor(R.color.colorCleaarBtn));
            edSummary.setHint(R.string.errorHint);
            edSummary.setHintTextColor(getResources().getColor(R.color.colorCleaarBtn));
            success = false;
        }

        if (mDetail.isEmpty()) {
            txtNote.setTextColor(getResources().getColor(R.color.colorCleaarBtn));
            edDetail.setHint(R.string.errorHint);
            edDetail.setHintTextColor(getResources().getColor(R.color.colorCleaarBtn));
            success = false;
        }

        if (!success) return;

        isBackDated = backDated.isChecked();

        NotePost post = new NotePost(area_id, client_ids, category_id, general_id, specific_id, mSummary, mDetail, isBackDated);


        Call<NotesRes> apiCall = Global.getAPIService.writeNotes("Token " + Global.token, post);
        apiCall.enqueue(new Callback<NotesRes>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<NotesRes> call, Response<NotesRes> response) {
                if(response.code() == 401){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                notesRes = response.body();

                SharedPreferences.Editor editor = getContext().getSharedPreferences("Message", Context.MODE_PRIVATE).edit();
                editor.putString("noteFragment", "Ny anteckning sparad.");
                editor.apply();

                FragmentTransaction tx = getFragmentManager().beginTransaction();
                tx.replace(R.id.fragment_container, Global.notesFragment).addToBackStack(null).commit();
                Global.floatingButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<NotesRes> call, Throwable t) {
                Log.d("$$$$$$#####","Failed");
            }
        });
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
            specific_id = 2 * general_id + i + 1;
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
                    Global.minor_keywordslist = new ArrayList<String>();
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
