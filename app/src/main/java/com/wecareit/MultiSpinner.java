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

//    public void performCloseEvent() {
//        // refresh text on spinner
//        mOpenInitiated = false;
//        StringBuffer spinnerBuffer = new StringBuffer();
//        boolean someUnselected = false;
//        for (int i = 0; i < items.size(); i++) {
//            if (selected[i] == true) {
//                spinnerBuffer.append(items.get(i));
//                spinnerBuffer.append(", ");
//            } else {
//                someUnselected = true;
//            }
//        }
//        String spinnerText;
//        if (someUnselected) {
//            spinnerText = spinnerBuffer.toString();
//            if (spinnerText.length() > 2)
//                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
//        } else {
//            spinnerText = defaultText;
//        }
////        Toast.makeText(getContext(), spinnerText, Toast.LENGTH_SHORT).show();
////        listener.onItemsSelected(selected);
//    }

    @Override
    public boolean performClick() {
        mOpenInitiated = true;
        return super.performClick();
    }

//    public void setItems(List<String> items, String allText,
//                         MultiSpinnerListener listener) {
//        this.items = items;
//        this.defaultText = allText;
//        this.listener = listener;
//
//        // all unselected by default
//        selected = new boolean[items.size()];
//        for (int i = 0; i < selected.length; i++)
//            selected[i] = true;
//
//        // all text on the spinner
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, new String[] { allText });
//        setAdapter(adapter);
//    }


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
