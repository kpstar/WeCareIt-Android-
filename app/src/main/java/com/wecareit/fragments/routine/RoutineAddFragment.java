package com.wecareit.fragments.routine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecareit.MultiSpinner;
import com.wecareit.R;
import com.wecareit.common.Global;
import com.wecareit.fragments.TemplateFragment;


public class RoutineAddFragment extends TemplateFragment implements MultiSpinner.MultiSpinnerListener {

    private LinearLayout lnWeekdays, lnUsers, lnAreas;
    private MultiSpinner spWeekdays, spUsers, spAreas;

    public static RoutineAddFragment createInstance() {
        return new RoutineAddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // this is how we load a specific layout
        return inflater.inflate(R.layout.fragment_routine_add, container, false);
    }

    public void onItemsSelected(boolean[] selected){
    };

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Global.toolbar.setNavigationIcon(R.drawable.ic_side_menu);
        Global.toolbar.setTitle("SKAPA RUTIN");
        Global.floatingButton.setVisibility(View.GONE);

        lnWeekdays = view.findViewById(R.id.lnWeekday_routeaddfragment);
        lnUsers = view.findViewById(R.id.lnUsers_routeaddfragment);
        lnAreas = view.findViewById(R.id.lnAreas_routeaddfragment);

        spUsers = view.findViewById(R.id.usersspinner_routeaddfragment);
        spUsers.setItems(Global.clientslist, getString(R.string.all), this);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.notescategories_array);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spWeekdays = view.findViewById(R.id.weekdayspinner_routeaddfragment);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.weekDays);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spWeekdays.setAdapter(adapter2);
        spAreas = view.findViewById(R.id.areasspinner_routeaddfragment);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.areaslist);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spAreas.setAdapter(adapter);
        /*ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spSpecifickey.setAdapter(adapter3);*/

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2,Color.LTGRAY);
        gd.setCornerRadius(5.0f);
        lnUsers.setBackground(gd);
        lnWeekdays.setBackground(gd);
        lnAreas.setBackground(gd);
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

        /*Button mSave = (Button) view.findViewById(R.id.menu_save);
        //if (_selectedDate.isToday()) mNextDate.setVisibility(View.GONE);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

}
