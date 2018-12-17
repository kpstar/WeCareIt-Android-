package com.wecareit.fragments.document;

import android.os.Bundle;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.wecareit.R;
import com.wecareit.adapter.DocumentAdapter;
import com.wecareit.common.Global;
import com.wecareit.common.Message;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.model.DocCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentFragment extends TemplateFragment {
    private ArrayList<DocCategory> docCategories;

    private CardView mGeneralSection;
    private RecyclerView mGeneralList;
    private Button mGeneralViewAll;

    private CardView mDayTimeActivitySection;
    private RecyclerView mDayTimeActivityList;
    private Button mDayTimeAcivityViewAll;

    private CardView mAccomodationSection;
    private RecyclerView mAccomodationList;
    private Button mAccomodationViewAll;

    private CardView mPersonalSection;
    private RecyclerView mPersonalList;
    private Button mPersonalViewAll;

    private CardView mQualitySection;
    private RecyclerView mQualityList;
    private Button mQualityViewAll;

    private CardView mAPTSection;
    private RecyclerView mAPTList;
    private Button mAPTViewAll;

    private int id_general, id_daytime, id_accommodation, id_personal, id_quality, id_APTS;

    private String string_order;

    public static int category_id = 0;
    public static String string_category = "";

    public static DocumentFragment createInstance() {
        return new DocumentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_document, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Global.toolbar.setTitle("DOKUMENT");
        //Global.floatingButton.setVisibility(View.VISIBLE);
        Global.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.documentAddFragment = DocumentAddFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.documentAddFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mGeneralSection = (CardView) view.findViewById(R.id.fragment_document_general_section);
        mGeneralSection.setVisibility(View.GONE);
        mGeneralList = (RecyclerView) view.findViewById(R.id.fragment_document_general_list);
        mGeneralList.setLayoutManager(new LinearLayoutManager(getContext()));
        mGeneralViewAll = (Button) view.findViewById(R.id.fragment_document_general_view_all);

        mDayTimeActivitySection = (CardView) view.findViewById(R.id.fragment_document_daytime_activity_section);
        mDayTimeActivitySection.setVisibility(View.GONE);
        mDayTimeActivityList = (RecyclerView) view.findViewById(R.id.fragment_document_daytime_activity_list);
        mDayTimeActivityList.setLayoutManager(new LinearLayoutManager(getContext()));
        mDayTimeAcivityViewAll = (Button) view.findViewById(R.id.fragment_document_daytime_activity_view_all);

        mAccomodationSection = (CardView) view.findViewById(R.id.fragment_document_accomodation_section);
        mAccomodationSection.setVisibility(View.GONE);
        mAccomodationList = (RecyclerView) view.findViewById(R.id.fragment_document_accomodation_list);
        mAccomodationList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAccomodationViewAll = (Button) view.findViewById(R.id.fragment_document_accomodation_view_all);

        mPersonalSection = (CardView) view.findViewById(R.id.fragment_document_personal_section);
        mPersonalSection.setVisibility(View.GONE);
        mPersonalList = (RecyclerView) view.findViewById(R.id.fragment_document_personal_list);
        mPersonalList.setLayoutManager(new LinearLayoutManager(getContext()));
        mPersonalViewAll = (Button) view.findViewById(R.id.fragment_document_personal_view_all);

        mQualitySection = (CardView) view.findViewById(R.id.fragment_document_quality_section);
        mQualitySection.setVisibility(View.GONE);
        mQualityList = (RecyclerView) view.findViewById(R.id.fragment_document_quality_list);
        mQualityList.setLayoutManager(new LinearLayoutManager(getContext()));
        mQualityViewAll = (Button) view.findViewById(R.id.fragment_document_quality_view_all);

        mAPTSection = (CardView) view.findViewById(R.id.fragment_document_apt_staff_meeting_section);
        mAPTSection.setVisibility(View.GONE);
        mAPTList = (RecyclerView) view.findViewById(R.id.fragment_document_apt_staff_meeting_list);
        mAPTList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAPTViewAll = (Button) view.findViewById(R.id.fragment_document_apt_staff_meeting_view_all);

        mGeneralViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.documentViewAllFragment = DocumentViewAllFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.documentViewAllFragment)
                        .addToBackStack(null)
                        .commit();
                string_category = "Generella";
                category_id = id_general;
            }
        });

        mDayTimeAcivityViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.documentViewAllFragment = DocumentViewAllFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.documentViewAllFragment)
                        .addToBackStack(null)
                        .commit();
                string_category = "Dagverksamheten";
                category_id = id_daytime;
            }
        });

        mAccomodationViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.documentViewAllFragment = DocumentViewAllFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.documentViewAllFragment)
                        .addToBackStack(null)
                        .commit();
                string_category = "Boende";
                category_id = id_accommodation;
            }
        });

        mPersonalViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.documentViewAllFragment = DocumentViewAllFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.documentViewAllFragment)
                        .addToBackStack(null)
                        .commit();
                string_category = "Personal";
                category_id = id_personal;
            }
        });

        mQualityViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.documentViewAllFragment = DocumentViewAllFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.documentViewAllFragment)
                        .addToBackStack(null)
                        .commit();
                string_category = "Kvalitetssystem";
                category_id = id_quality;
            }
        });

        mAPTViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.documentViewAllFragment = DocumentViewAllFragment.createInstance();
                Global.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Global.documentViewAllFragment)
                        .addToBackStack(null)
                        .commit();
                string_category = "Personalmöte";
                category_id = id_APTS;
            }
        });

        getCategory();
        loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_document_filter, menu);
        this.setHasOptionsMenu(true);
        if (menu == null) {
            System.out.println("menu is null");
            return;
        }
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.getItem(0);
        View view = item.getActionView();
        Switch mSwitch = (Switch)view.findViewById(R.id.menu_item_document_filter_switch);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    string_order = "date";
                } else{
                    string_order = "filename";
                }
                loadData();
            }
        });
    }

    public void loadData() {
        mGeneralSection.setVisibility(View.GONE);
        mDayTimeActivitySection.setVisibility(View.GONE);
        mAccomodationSection.setVisibility(View.GONE);
        mPersonalSection.setVisibility(View.GONE);
        mQualitySection.setVisibility(View.GONE);
        mAPTSection.setVisibility(View.GONE);

        Call<ArrayList<DocCategory>> call = Global.getAPIService.readDocuments("Token " + Global.token, string_order);
        call.enqueue(new Callback<ArrayList<DocCategory>>() {
            @Override
            public void onResponse(Call<ArrayList<DocCategory>> call, Response<ArrayList<DocCategory>> response) {
                //Message.showObject(response.body());
                if (response.isSuccessful()) {
                    Log.d("#############","@@@@@@@@@@@");
                    docCategories = response.body();

                    for (DocCategory category : docCategories) {
                        Global.categorylist.add(category.getName());
                        Message.showObject(category);
                        if (category.getName().equals("Generella")) {
                            id_general = category.getId();
                            mGeneralSection.setVisibility(View.VISIBLE);
                            DocumentAdapter adapter = new DocumentAdapter(DocumentFragment.this.getContext(), category.getDocuments());
                            mGeneralList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else if (category.getName().equals("Dagverksamheten")) {
                            id_daytime = category.getId();
                            mDayTimeActivitySection.setVisibility(View.VISIBLE);
                            DocumentAdapter adapter = new DocumentAdapter(DocumentFragment.this.getContext(), category.getDocuments());
                            mDayTimeActivityList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();;
                        } else if (category.getName().equals("Boende")) {
                            id_accommodation = category.getId();
                            mAccomodationSection.setVisibility(View.VISIBLE);
                            DocumentAdapter adapter = new DocumentAdapter(DocumentFragment.this.getContext(), category.getDocuments());
                            mAccomodationList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else if (category.getName().equals("Personal")) {
                            id_personal = category.getId();
                            mPersonalSection.setVisibility(View.VISIBLE);
                            DocumentAdapter adapter = new DocumentAdapter(DocumentFragment.this.getContext(), category.getDocuments());
                            mPersonalList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else if (category.getName().equals("Kvalitetssystem")) {
                            id_quality = category.getId();
                            mQualitySection.setVisibility(View.VISIBLE);
                            DocumentAdapter adapter = new DocumentAdapter(DocumentFragment.this.getContext(), category.getDocuments());
                            mQualityList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else if (category.getName().equals("APT / Personalmöte")) {
                            id_APTS = category.getId();
                            mAPTSection.setVisibility(View.VISIBLE);
                            DocumentAdapter adapter = new DocumentAdapter(DocumentFragment.this.getContext(), category.getDocuments());
                            mAPTList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DocCategory>> call, Throwable t) {

            }
        });
    }

    public void getCategory() {

        Call<ArrayList<DocCategory>> call = Global.getAPIService.readCategory("Token " + Global.token);
        call.enqueue(new Callback<ArrayList<DocCategory>>() {
            @Override
            public void onResponse(Call<ArrayList<DocCategory>> call, Response<ArrayList<DocCategory>> response) {
                //Message.showObject(response.body());
                if (response.isSuccessful()) {
                    Log.d("getCate","Success");
                    docCategories = response.body();
                    for (DocCategory category : docCategories) {
                        Global.categorylist.add(category.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DocCategory>> call, Throwable t) {

            }
        });
    }
}
