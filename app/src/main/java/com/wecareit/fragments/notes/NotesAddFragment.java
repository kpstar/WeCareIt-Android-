package com.wecareit.fragments.notes;

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


public class NotesAddFragment extends TemplateFragment implements MultiSpinner.MultiSpinnerListener {

    private LinearLayout lnAccomo, lnGenerakkey, lnSpecifickey, lnCategory, lnArea;
    private Spinner spGeneralkey, spArea, spSpecifickey, spCategory;
    private MultiSpinner spAccomo;

    public static NotesAddFragment createInstance() {
        return new NotesAddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // this is how we load a specific layout
        return inflater.inflate(R.layout.fragment_notes_add, container, false);
    }

    public void onItemsSelected(boolean[] selected){
    };

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.areaslist);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spArea.setAdapter(adapter);
        spCategory = view.findViewById(R.id.categoryspinner_notesaddfragment);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.notescategories_array);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spCategory.setAdapter(adapter1);
        spGeneralkey = view.findViewById(R.id.generalkeyspinner_notesaddfragment);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.major_keywordslist);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spGeneralkey.setAdapter(adapter2);
        spSpecifickey = view.findViewById(R.id.specifickeyspinner_notesaddfragment);
        /*ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Global.);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spSpecifickey.setAdapter(adapter3);*/
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

        /*Button mSave = (Button) view.findViewById(R.id.menu_save);
        //if (_selectedDate.isToday()) mNextDate.setVisibility(View.GONE);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

}
