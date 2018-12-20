package com.wecareit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.wecareit.adapter.SpinnerAdapter;
import com.wecareit.model.Spinners;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SuppressLint("AppCompatCustomView")
public class MultiSpinner extends Spinner implements SpinnerAdapter.SpinnerAdapterListener {

    private List<String> items;
    private boolean[] selected = null;
    private String defaultText;
    private boolean mOpenInitiated = false;
    private SpinnerAdapter adapter;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public boolean performClick() {
        mOpenInitiated = true;
        return super.performClick();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (mOpenInitiated && hasWindowFocus) {
//           performCloseEvent();
        }
    }

    public void setItems(List<String> items, String allText) {
        this.items = items;
        this.defaultText = allText;

        // all selected by default
        selected = new boolean[items.size()];
        for (int i = 0; i < selected.length; i++)
            selected[i] = true;
        ArrayList<Spinners> mItems = new ArrayList<Spinners>();
        Spinners spinner = new Spinners(defaultText, true);
        mItems.add(spinner);
        for (int i = 0; i < items.size(); i++) {
            spinner = new Spinners(items.get(i).toString(), true);
            mItems.add(spinner);
        }
        adapter = new SpinnerAdapter(getContext(), 0, mItems, this);
        setAdapter(adapter);
    }

    public void setItemsWithOptions(List<String> items, List<String> selection) {
        this.items = items;

        // all selected by default
        selected = new boolean[items.size()];
        for (int i = 0; i < selected.length; i++)
            selected[i] = false;

        for (int i=0;i<items.size(); i++) {
            for (String sel: selection) {
                if (items.get(i).equals(sel)) {
                    selected[i] = true;
                    break;
                }
            }
        }
        ArrayList<Spinners> mItems = new ArrayList<Spinners>();
        Spinners spinner = new Spinners(defaultText, true);
        mItems.add(spinner);
        for (int i = 0; i < items.size(); i++) {
            spinner = new Spinners(items.get(i).toString(), selected[i]);
            mItems.add(spinner);
        }
        adapter = new SpinnerAdapter(getContext(), 0, mItems, this);
        setAdapter(adapter);
    }

    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<String>();
        for (int i = 0; i < items.size(); ++i) {
            if (selected[i]) {
                selection.add(items.get(i));
            }
        }
        return selection;
    }

    public List<String> getItems() {
        return items;
    }

    public void setSelected(boolean[] selected) {
        this.selected = selected;
    }

    public void setSelectedItem(int position, boolean isChecked) {
        this.selected[position] = isChecked;
    }

    public boolean[] getSelected() {
        return selected;
    }

    @Override
    public void onSelected(int pos, boolean isChecked) {
        this.selected[pos - 1] = isChecked;
    }
}
