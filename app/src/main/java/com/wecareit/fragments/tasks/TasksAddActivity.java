package com.wecareit.fragments.tasks;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wecareit.LoginActivity;
import com.wecareit.MultiSpinner;
import com.wecareit.R;
import com.wecareit.common.Global;
import com.wecareit.model.AuthorRes;
import com.wecareit.model.InfoRes;
import com.wecareit.model.PostTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksAddActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    private LinearLayout lnMain, lnActivity, lnResponsible, lnDeadline;
    private EditText etActivity, etDeadline;
    private MultiSpinner spResponsible;
    private PostTask postTask;
    private String stDeadline, stActivity;
    private ArrayList<Integer> user_id;

    private Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Skapa uppgift");


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

        lnMain = (LinearLayout) findViewById(R.id.lnMain_taskadd);
        lnActivity = (LinearLayout) findViewById(R.id.lnActivity_taskadd);
        lnResponsible = (LinearLayout) findViewById(R.id.lnResponsible_taskadd);
        lnDeadline = (LinearLayout) findViewById(R.id.lnDeadline_taskadd);
        etActivity = (EditText) findViewById(R.id.etActivity_taskadd);
        etDeadline = (EditText) findViewById(R.id.etDeadline_taskadd);
        spResponsible = (MultiSpinner) findViewById(R.id.spResponsible_taskadd);
        spResponsible.setItems(Global.userslist, getString(R.string.all));

        etDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(TasksAddActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        GradientDrawable gd_tab = new GradientDrawable();
        gd_tab.setShape(GradientDrawable.RECTANGLE);
        gd_tab.setStroke(2, Color.LTGRAY);
        gd_tab.setCornerRadius(3.0f);
        lnActivity.setBackground(gd_tab);
        lnResponsible.setBackground(gd_tab);
        lnDeadline.setBackground(gd_tab);

        GradientDrawable gd_main = new GradientDrawable();
        gd_main.setShape(GradientDrawable.RECTANGLE);
        gd_main.setStroke(2,Color.LTGRAY);
        gd_main.setCornerRadius(5.0f);
        lnMain.setBackground(gd_main);


        sharedPreferences = this.getSharedPreferences("Fragment", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Current", "Tasks");
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_task_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                TasksAddActivity.this.finish();
                break;
            case R.id.menu_tasks_add:
                postData();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void postData() {

        user_id = new ArrayList<Integer>();
        stActivity = etActivity.getText().toString();
        stDeadline = etDeadline.getText().toString();
        ArrayList<String> users = new ArrayList<String>();
        for (int i=0; i<spResponsible.getItems().size(); i++) {
            if (spResponsible.getSelected()[i] == true) {
                user_id.add(i + 1);
            }
        }

        postTask =new PostTask(stActivity, user_id, stDeadline);

        Call<ResponseBody> call = Global.getAPIService.postTask("Token " + Global.token, postTask);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 401){
                    Intent intent = new Intent(TasksAddActivity.this, LoginActivity.class);
                    startActivity(intent);
                    TasksAddActivity.this.finish();
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
