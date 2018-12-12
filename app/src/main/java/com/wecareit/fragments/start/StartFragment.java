package com.wecareit.fragments.start;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.wecareit.LoginActivity;
import com.wecareit.R;
import com.wecareit.adapter.StartEventsAdapter;
import com.wecareit.adapter.StartNewsAdapter;
import com.wecareit.adapter.StartTasksAdapter;
import com.wecareit.adapter.StartUpdatesAdapter;
import com.wecareit.common.Global;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.fragments.news.NewsFragment;
import com.wecareit.model.EventsRes;
import com.wecareit.model.MainModel;
import com.wecareit.model.News;
import com.wecareit.model.NewsResponse;
import com.wecareit.model.Tasks;
import com.wecareit.model.Updates;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartFragment extends TemplateFragment {

    private MainModel starslist;
    private ArrayList<NewsResponse> newslist;
    private ArrayList<EventsRes> eventslist;
    private ArrayList<Tasks> taskslist;
    private ArrayList<Updates> updateslist;

    private RecyclerView newsRecyclerView, eventsRecyclerView, tasksRecyclerView, updatesRecyclerView;
    private LinearLayout newsSection, eventsSection, tasksSection, updateSection;
    private TextView tvHeader_news,tvHeader_updates, tvHeader_tasks, tvHeader_events ;
    private int flag_relevant = 0;

    public static StartFragment createInstance() {
        return new StartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // this is how we load a specific layout
        return inflater.inflate(R.layout.fragment_start, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Global.toolbar.setNavigationIcon(R.drawable.ic_side_menu);
        Global.toolbar.setTitle("WECAREIT DEMO");

        newsRecyclerView = view.findViewById(R.id.fragmentstart_news_list);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        eventsRecyclerView = view.findViewById(R.id.fragmentstart_events_list);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        tasksRecyclerView = view.findViewById(R.id.fragmentstart_tasks_list);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        updatesRecyclerView = view.findViewById(R.id.fragmentstart_updates_list);
        updatesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        newsSection = view.findViewById(R.id.fragmentstart_news_section);
        //newsSection.setVisibility(View.GONE);
        eventsSection = view.findViewById(R.id.fragmentstart_events_section);
        //eventsSection.setVisibility(View.GONE);
        tasksSection = view.findViewById(R.id.fragmentstart_tasks_section);
        //tasksSection.setVisibility(View.GONE);
        updateSection = view.findViewById(R.id.fragmentstart_updates_section);
        //updateSection.setVisibility(View.GONE);

        tvHeader_news = view.findViewById(R.id.tvHeader_news_startfragment);
        tvHeader_events = view.findViewById(R.id.tvHeader_events_startfragment);
        tvHeader_tasks = view.findViewById(R.id.tvHeader_tasks_startfragment);
        tvHeader_updates = view.findViewById(R.id.tvHeader_updates_startfragment);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(4,Color.LTGRAY);
        gd.setCornerRadius(5.0f);

        tvHeader_news.setBackground(gd);
        tvHeader_updates.setBackground(gd);
        tvHeader_events.setBackground(gd);
        tvHeader_tasks.setBackground(gd);

        loadData();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_filter, menu);
        this.setHasOptionsMenu(true);
        if (menu == null) {
            System.out.println("menu is null");
            return;
        }
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.getItem(0);
        View view = item.getActionView();
        Switch mSwitch = (Switch)view.findViewById(R.id.menu_item_news_filter_switch);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    flag_relevant =1;
                } else{
                    flag_relevant =0;
                }
                loadData();
            }
        });
        //super.onCreateOptionsMenu(menu,inflater);
    }

    public void loadData() {

        Call<MainModel> call = Global.getAPIService.readStart("Token "+ Global.token );
        //Log.d("Startfrag",call.toString());
        call.enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {

                if(response.code() == 401){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

                if (response.isSuccessful()) {
                    starslist = response.body();
                    //for(MainModel mainModel: starslist)

                    Log.d("Startfrag",starslist.toJSON());
                    if(starslist.getNews().isEmpty()){
                        newsSection.setVisibility(View.GONE);
                    } else {
                        newsSection.setVisibility(View.VISIBLE);
                        newslist = starslist.getNews();

                        for (NewsResponse newsResponse : newslist){
                            Log.d("UpdatesTitle",newsResponse.toJSON());
                            StartNewsAdapter adapter = new StartNewsAdapter(StartFragment.this.getContext(), newslist);
                            newsRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    if(starslist.getEvents().isEmpty()) {
                        eventsSection.setVisibility(View.GONE);
                    } else {
                        eventsSection.setVisibility(View.VISIBLE);
                        eventslist = starslist.getEvents();

                        for (EventsRes eventsRes : eventslist){
                            Log.d("EventsTitle",eventsRes.toJSON());
                            StartEventsAdapter adapter = new StartEventsAdapter(StartFragment.this.getContext(), eventslist);
                            eventsRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    if (starslist.getTasks().isEmpty()){
                        tasksSection.setVisibility(View.GONE);
                    } else {
                        tasksSection.setVisibility(View.VISIBLE);
                        taskslist = starslist.getTasks();

                        for (Tasks tasks : taskslist){
                            Log.d("EventsTitle",tasks.toJSON());
                            StartTasksAdapter adapter = new StartTasksAdapter(StartFragment.this.getContext(), taskslist);
                            tasksRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    if (starslist.getUpdates().isEmpty()){
                        updateSection.setVisibility(View.GONE);
                    } else {
                        updateSection.setVisibility(View.VISIBLE);
                        updateslist = starslist.getUpdates();

                        for (Updates updates : updateslist){
                            Log.d("UpdatesTitle",updates.toJSON());
                            StartUpdatesAdapter adapter = new StartUpdatesAdapter(StartFragment.this.getContext(), updateslist);
                            updatesRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {
                //Log.d("Startfrag",t.toString());
            }
        });
    }
}
