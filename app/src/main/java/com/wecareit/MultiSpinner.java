package com.wecareit;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.wecareit.adapter.SpinnerAdapter;
import com.wecareit.model.Spinners;

import java.util.ArrayList;
import java.util.List;

public class MultiSpinner extends Spinner implements
        DialogInterface.OnMultiChoiceClickListener {

    private List<String> items;
    private boolean[] selected = null;
    private String defaultText;
    private MultiSpinnerListener listener;
    private boolean mOpenInitiated = false;

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
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked)
            selected[which] = true;
        else
            selected[which] = false;
    }

    public void performCloseEvent() {
        // refresh text on spinner
        mOpenInitiated = false;
        StringBuffer spinnerBuffer = new StringBuffer();
        boolean someUnselected = false;
        for (int i = 0; i < items.size(); i++) {
            if (selected[i] == true) {
                spinnerBuffer.append(items.get(i));
                spinnerBuffer.append(", ");
            } else {
                someUnselected = true;
            }
        }
        String spinnerText;
        if (someUnselected) {
            spinnerText = spinnerBuffer.toString();
            if (spinnerText.length() > 2)
                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
        } else {
            spinnerText = defaultText;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[] { spinnerText });
        setAdapter(adapter);
        listener.onItemsSelected(selected);
    }

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
           performCloseEvent();
        }
    }

    public void setItems(List<String> items, String allText, MultiSpinnerListener listener) {
        this.items = items;
//        this.defaultText = allText;
        this.listener = listener;

        // all selected by default
        selected = new boolean[items.size()];
        for (int i = 0; i < selected.length; i++)
            selected[i] = true;

        ArrayList<Spinners> mItems = new ArrayList<Spinners>();
        Spinners stateVO = new Spinners();
        stateVO.setTitle(allText);
        stateVO.setSelected(true);
        mItems.add(stateVO);
        for (int i = 0; i < items.size(); i++) {
            stateVO = new Spinners();
            stateVO.setTitle(items.get(i).toString());
            stateVO.setSelected(true);
            mItems.add(stateVO);
        }
        SpinnerAdapter myAdapter = new SpinnerAdapter(getContext(), 0, mItems);
        setAdapter(myAdapter);
    }

    public List<String> getItems() {
        return items;
    }

    public boolean[] getSelected() {
        return selected;
    }

    public interface MultiSpinnerListener {
        public void onItemsSelected(boolean[] selected);
    }
}
