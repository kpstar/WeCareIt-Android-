package com.wecareit.fragments.tasks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.wecareit.InformationEditActivity;
import com.wecareit.LoginActivity;
import com.wecareit.NavigationActivity;
import com.wecareit.R;
import com.wecareit.adapter.TasksAdapter;
import com.wecareit.common.Global;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.model.Tasks;
import com.wecareit.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TasksFragment extends TemplateFragment {

    private int flag_expand = 0;
    public static int flag_mine = 0;
    private LinearLayout lnExpand;
    private RecyclerView mRecyclerView;
    private Spinner mSP_status, mSP_timeinterval;
    private Button mBtn_clear, mBtn_used;
    private User user;
    private static String filter_status="", filter_date="";

    private ArrayList<Tasks> tasks;

    public static TasksFragment createInstance() {
        return new TasksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // this is how we load a specific layout
        return inflater.inflate(R.layout.fragment_tasks, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Global.toolbar.setNavigationIcon(R.drawable.ic_side_menu);
        Global.toolbar.setTitle("UPPGIFTER");
        Global.floatingButton.setVisibility(View.VISIBLE);
        Global.floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(getContext(), TasksAddActivity.class);
                getContext().startActivity(mIntent);
            }
        });

        lnExpand = view.findViewById(R.id.expandLinear_tasksfragment);
        mRecyclerView = view.findViewById(R.id.framgment_tasks_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mSP_status = view.findViewById(R.id.statusspinner_tasksfragment);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Global.status_array);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSP_status.setAdapter(adapter1);

        mSP_timeinterval = view.findViewById(R.id.timeintervalspinner_tasksfragment);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Global.timeintervals_array);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSP_timeinterval.setAdapter(adapter2);

        mBtn_clear = view.findViewById(R.id.btnClear_tasksfragment);
        mBtn_used = view.findViewById(R.id.btnUsed_tasksfragment);

        mBtn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSP_status.setSelection(0);
                mSP_timeinterval.setSelection(0);
                Log.d("Status", filter_status);
                Log.d("Date", filter_date);

            }
        });

        mBtn_used.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //postTasks();
                lnExpand.setVisibility(View.GONE);
                filter_status = mSP_status.getSelectedItem().toString();
                filter_date = mSP_timeinterval.getSelectedItem().toString();
                Log.d("Status", filter_status);
                Log.d("Date", filter_date);
                //getFilterDate(mSP_timeinterval.getSelectedItem().toString());
                loadData();
            }
        });

        loadData();

    }
/*

    public Date getFilterDate(String date){

        Date nowdate = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String nowDateString = df.format(nowdate);
        try {
            nowdate = df.parse(nowDateString);
        } catch (Exception ex){

        }

        if(date.equals("Alla datum")){
            return null;
        } else if (date.equals("Idag")){
            return nowdate;
        } else if (date.equals("Senaste 7 dagarna")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowdate);
            calendar.add(Calendar.DATE, -7);
            nowdate = calendar.getTime();
            return nowdate;
        } else if (date.equals("")){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowdate);
            calendar.add(Calendar.MONTH, -1);
            nowdate = calendar.getTime();
            return nowdate;
        }
        return null;
    }
*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tasks_filter, menu);
        this.setHasOptionsMenu(true);
        if (menu == null) {
            System.out.println("menu is null");
            return;
        }
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.getItem(0);
        View view = item.getActionView();
        Switch mSwitch = (Switch)view.findViewById(R.id.menu_item_tasks_filter_switch);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    flag_mine = 1;
                    loadData();
                } else{
                    flag_mine = 0;
                    loadData();
                }
                //loadData();
            }
        });

        ImageView mExpande = (ImageView) view.findViewById(R.id.menu_item_tasks_filter_expand);
        //if (_selectedDate.isToday()) mNextDate.setVisibility(View.GONE);
        mExpande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("******","Clicked");
                if(flag_expand == 0){
                    lnExpand.setVisibility(View.VISIBLE);
                    flag_expand = 1;
                } else {
                    lnExpand.setVisibility(View.GONE);
                    flag_expand = 0;
                }
            }
        });
    }

    public void loadData() {
        this.user = Global.user;

        Date nowdate = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String nowDateString = df.format(nowdate);
        Log.d("nowdate", nowDateString);

        Call<ArrayList<Tasks>> call;
        if(flag_mine == 0) {
            call = Global.getAPIService.readTasks("Token " + Global.token);
        } else {
            call = Global.getAPIService.readUserTasks("Token " + Global.token, Global.user.getId());
        }

        call.enqueue(new Callback<ArrayList<Tasks>>() {
            @Override
            public void onResponse(Call<ArrayList<Tasks>> call, Response<ArrayList<Tasks>> response) {

                if (response.isSuccessful()) {
                    if(response.code() == 401){
                        Intent intent = new Intent(TasksFragment.this.getActivity(), LoginActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                    tasks = response.body();

                    for (Tasks newsres : tasks) {

                        switch (filter_status){
                            case "Alla":
                                if(filter_date.equals("Alla datum")){
                                    Log.d("ALL", filter_date);
                                    TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                    mRecyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } else if(filter_date.equals("Idag")){
                                    if(newsres.getDeadline_date().equals(nowDateString)){
                                        TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                        mRecyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                } else if(filter_date.equals("Senaste 7 dagarna")){
                                    if(newsres.getDeadline_date().equals(nowDateString)){
                                        TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                        mRecyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                } else if(filter_date.equals("Denna månad")){
                                    if(newsres.getDeadline_date().substring(0,7).equals(nowDateString.substring(0,7))){
                                        TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                        mRecyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                } else if(filter_date.equals("Detta år")){

                                    if(newsres.getDeadline_date().substring(0,4).equals(nowDateString.substring(0,4))){
                                        TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                        mRecyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                                //break;
                            case "Ej klar":
                                //Log.d("###########", "Ej klar");
                                if(newsres.getStatus().equals("ACTIVE")){
                                    if(filter_date.equals("Alla datum")){
                                        TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                        mRecyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    } else if(filter_date.equals("Idag")){
                                        if(newsres.getDeadline_date().equals(nowDateString)){
                                            TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                            mRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    } else if(filter_date.equals("Senaste 7 dagarna")){
                                        if(newsres.getDeadline_date().equals(nowDateString)){
                                            TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                            mRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    } else if(filter_date.equals("Denna månad")){
                                        if(newsres.getDeadline_date().substring(0,7).equals(nowDateString.substring(0,7))){
                                            TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                            mRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    } else if(filter_date.equals("Detta år")){
                                        if(newsres.getDeadline_date().substring(0,4).equals(nowDateString.substring(0,4))){
                                            TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                            mRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                                //break;
                            case "Klar":
                                if(newsres.getStatus().equals("CANCL")){
                                    if(filter_date.equals("Alla datum")){
                                        Log.d(nowDateString, newsres.getDeadline_date());
                                        TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                        mRecyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    } else if(filter_date.equals("Idag")){
                                        if(newsres.getDeadline_date().equals(nowDateString)){
                                            TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                            mRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    } else if(filter_date.equals("Senaste 7 dagarna")){
                                        if(newsres.getDeadline_date().equals(nowDateString)){
                                            TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                            mRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    } else if(filter_date.equals("Denna månad")){
                                        if(newsres.getDeadline_date().substring(0,7).equals(nowDateString.substring(0,7))){
                                            TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                            mRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    } else if(filter_date.equals("Detta år")){
                                        if(newsres.getDeadline_date().substring(0,4).equals(nowDateString.substring(0,4))){
                                            TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                            mRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                                //break;
                            case "Inställd":
                                if(newsres.getStatus().equals("COMPL")){
                                    if(filter_date.equals("Alla datum")){
                                        TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                        mRecyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    } else if(filter_date.equals("Idag")){
                                        if(newsres.getDeadline_date().equals(nowDateString)){
                                            TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                            mRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    } else if(filter_date.equals("Senaste 7 dagarna")){
                                        if(newsres.getDeadline_date().equals(nowDateString)){
                                            TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                            mRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    } else if(filter_date.equals("Denna månad")){
                                        if(newsres.getDeadline_date().substring(0,7).equals(nowDateString.substring(0,7))){
                                            TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                            mRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    } else if(filter_date.equals("Detta år")){
                                        if(newsres.getDeadline_date().substring(0,4).equals(nowDateString.substring(0,4))){
                                            TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                            mRecyclerView.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                                //break;
                            default:
                                break;
                        }
                                TasksAdapter adapter = new TasksAdapter(TasksFragment.this.getContext(), tasks);
                                mRecyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Tasks>> call, Throwable t) {
            }
        });
    }

    /*public void postTasks(){

    }*/
}
