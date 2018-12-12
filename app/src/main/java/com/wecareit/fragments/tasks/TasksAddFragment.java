package com.wecareit.fragments.tasks;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wecareit.LoginActivity;
import com.wecareit.MultiSpinner;
import com.wecareit.R;
import com.wecareit.common.Global;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.model.AuthorRes;
import com.wecareit.model.PostTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksAddFragment extends TemplateFragment implements MultiSpinner.MultiSpinnerListener {

    private LinearLayout lnMain, lnActivity, lnResponsible, lnDeadline;
    private EditText etActivity, etDeadline;
    private MultiSpinner spResponsible;
    private PostTask postTask;
    private String stDeadline, stActivity;
    private ArrayList<Integer> user_id;

    private Calendar myCalendar = Calendar.getInstance();

    public static TasksAddFragment createInstance() {
        return new TasksAddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // this is how we load a specific layout
        return inflater.inflate(R.layout.fragment_tasks_add, container, false);

    }

    public void onItemsSelected(boolean[] selected){
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Global.toolbar.setNavigationIcon(R.drawable.ic_side_menu);
        Global.toolbar.setTitle("UPPGIFTER");
        Global.floatingButton.setVisibility(View.GONE);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        lnMain = (LinearLayout) view.findViewById(R.id.lnMain_taskadd);
        lnActivity = (LinearLayout) view.findViewById(R.id.lnActivity_taskadd);
        lnResponsible = (LinearLayout) view.findViewById(R.id.lnResponsible_taskadd);
        lnDeadline = (LinearLayout) view.findViewById(R.id.lnDeadline_taskadd);
        etActivity = (EditText) view.findViewById(R.id.etActivity_taskadd);
        etDeadline = (EditText) view.findViewById(R.id.etDeadline_taskadd);
        spResponsible = (MultiSpinner) view.findViewById(R.id.spResponsible_taskadd);
        spResponsible.setItems(Global.userslist, getString(R.string.all), this);

        etDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(TasksAddFragment.this.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        GradientDrawable gd_tab = new GradientDrawable();
        gd_tab.setShape(GradientDrawable.RECTANGLE);
        gd_tab.setStroke(2,Color.LTGRAY);
        gd_tab.setCornerRadius(3.0f);
        lnActivity.setBackground(gd_tab);
        lnResponsible.setBackground(gd_tab);
        lnDeadline.setBackground(gd_tab);

        GradientDrawable gd_main = new GradientDrawable();
        gd_main.setShape(GradientDrawable.RECTANGLE);
        gd_main.setStroke(2,Color.LTGRAY);
        gd_main.setCornerRadius(5.0f);
        lnMain.setBackground(gd_main);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            public void onClick(View v) {
                user_id = new ArrayList<Integer>();
                stActivity = etActivity.getText().toString();
                stDeadline = etDeadline.getText().toString();
                String users = spResponsible.getSelectedItem().toString();
                String[] splited_user = users.split(", ");
                if(users.equals("Alla")){
                    for (AuthorRes authoRres : Global.users) {
                        user_id.add(authoRres.getId());
                    }
                } else {
                    for (AuthorRes authoRres : Global.users) {
                        for (int i = 0; i < splited_user.length; i++) {
                            //Log.d(splited_user[i], "" + authoRres.getName());
                            if (splited_user[i].equals(authoRres.getName())) {
                                user_id.add(authoRres.getId());
                            }
                        }
                    }
                }

                postData();
            }
        });
    }

    public void postData() {

        postTask =new PostTask(stActivity, user_id, stDeadline);

        Call<ResponseBody> call = Global.getAPIService.postTask("Token " + Global.token, postTask);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 401){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                //Log.d("Success",""+response.code());
                if (response.isSuccessful()) {
                    Global.tasksFragment = TasksFragment.createInstance();
                    Global.fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, Global.tasksFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        etDeadline.setText(sdf.format(myCalendar.getTime()));
    }
}
