package com.wecareit.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wecareit.R;

public class CommentListAdapter extends ArrayAdapter<String> {
    private  Context context;
    private final String[] username;
    private final String[] time;
    private final String[] description;
    private final String[] img_url;

    public CommentListAdapter(Context context, String[] username, String[] time, String[] description, String[] img_url) {
        super(context, R.layout.commentslist_row, username);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.username=username;
        this.time=time;
        this.description=description;
        this.img_url = img_url;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View rowView=inflater.inflate(R.layout.commentslist_row, null,true);
        //View view = super.getView(position,convertView,parent);

        /*TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);*/

        TextView tvUsername = (TextView) rowView.findViewById(R.id.tvTitle_commentslist);
        TextView tvTime = (TextView) rowView.findViewById(R.id.tvTime_commentslist);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.ivUserimage_commentslist);
        TextView tvDescription = (TextView) rowView.findViewById(R.id.tvDeac_commentslist);
        LinearLayout lnRow = (LinearLayout) rowView.findViewById(R.id.lnCommentlistrow);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(2,Color.LTGRAY);
        gd.setColor(Color.argb(255,243,243,243 ));
        gd.setCornerRadius(3.0f);
        lnRow.setBackground(gd);

        tvUsername.setText(username[position]);
        new DownloadImage(imageView).execute(img_url[position]);
        //imageView.setImageResource(img_url[position]);
        tvTime.setText(time[position]);
        tvDescription.setText(description[position]);

        //lnRow.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, 180));

        return rowView;

    };
}
