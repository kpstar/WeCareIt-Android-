package com.wecareit.adapter;

import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wecareit.MultiSpinner;
import com.wecareit.R;
import com.wecareit.model.Spinners;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Spinners> {
    private Context mContext;
    private ArrayList<Spinners> listState;
    private SpinnerAdapter spinnerAdapter;
    private String spinnerTitle;
    private boolean isFromView = false;
    public boolean isOpened;

    private SpinnerAdapterListener listener;

    public SpinnerAdapter(Context context, int resource, List<Spinners> objects, SpinnerAdapterListener listener) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<Spinners>) objects;
        this.spinnerAdapter = this;
        this.listener = listener;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.text);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            holder.mLayout = (ConstraintLayout) convertView.findViewById(R.id.constLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        boolean isAll = true;
        spinnerTitle = "";
        if (position == 0) {
            holder.mLayout.setBackgroundColor(mContext.getColor(R.color.white));
        } else {
            holder.mTextView.setText(listState.get(position).getTitle());
        }
        for (int i=1;i<listState.size(); i ++) {
            if (listState.get(i).isSelected() == true) {
                spinnerTitle += listState.get(i).getTitle() + ", ";
            } else {
                isAll = false;
            }
        }


        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {

            if (!isOpened) {
                holder.mTextView.setText(listState.get(0).getTitle());
                holder.mCheckBox.setVisibility(View.GONE);
            } else {
                holder.mCheckBox.setVisibility(View.VISIBLE);
                if (isAll) {
                    holder.mCheckBox.setChecked(true);
                    holder.mTextView.setText("Avmarkera alla");
                } else {
                    holder.mTextView.setText("Markera alla");
                    holder.mCheckBox.setChecked(false);
                }
            }
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();
                if (!isFromView) {
                    if (position == 0) {
                        for (int i=0;i<listState.size(); i++)
                            listState.get(i).setSelected(isChecked);
                    }
                    listState.get(getPosition).setSelected(isChecked);
                    listener.onSelected(getPosition, isChecked);
                    SpinnerAdapter.this.notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private View contentView;
        private TextView mTextView;
        private CheckBox mCheckBox;
        private ConstraintLayout mLayout;
    }

    public interface SpinnerAdapterListener {
        public void onSelected(int pos, boolean isChecked);
    }
}
