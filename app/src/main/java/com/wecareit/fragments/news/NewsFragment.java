package com.wecareit.fragments.news;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.wecareit.LoginActivity;
import com.wecareit.R;
//import com.wecareit.adapter.CommentAdapter;
import com.wecareit.adapter.NewsAdapter;
import com.wecareit.common.Global;
import com.wecareit.common.Message;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.model.Comment;
import com.wecareit.model.News;
import com.wecareit.model.NewsPost;
import com.wecareit.model.NewsPostResponse;
import com.wecareit.model.NewsResponse;
import com.wecareit.model.User;
import com.wecareit.model.Userlist;
import com.wecareit.retrofit.GetAPIService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends TemplateFragment {

    private static ArrayList<NewsResponse> news_me, news_other;
    public static int flag_comment = 0;
    private RecyclerView mRecyclerView;
    private EditText mEdit;
    private ImageView mImage;
    private String me_mentioned;
    private NewsPost postNews_body;
    private RelativeLayout rlEdit;

    public static int flag_relevant=0;

    public static NewsFragment createInstance() {
        return new NewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // this is how we load a specific layout
        return inflater.inflate(R.layout.fragment_news, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Global.toolbar.setNavigationIcon(R.drawable.ic_side_menu);
        Global.toolbar.setTitle("FLÖDE");

        mRecyclerView = view.findViewById(R.id.framgment_news_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mEdit = view.findViewById(R.id.edit_newsfragment);
        mImage = view.findViewById(R.id.btnEdit_newsfragment);
        rlEdit = view.findViewById(R.id.editLinear_newsfragment);

        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(3,Color.LTGRAY);
        gd.setCornerRadius(5.0f);

        rlEdit.setBackground(gd);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
                final Handler h = new Handler();
                h.postDelayed(new Runnable()
                {
                    private long time = 0;

                    @Override
                    public void run()
                    {
                        loadData();
                    }
                }, 1000);
            }
        });
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
                    me_mentioned = "true";
                } else{
                    flag_relevant =0;
                    me_mentioned = "false";
                }
                loadData();
            }
        });
    }

    public void loadData() {

        news_me = new ArrayList<NewsResponse>();
        Call<ArrayList<NewsResponse>> call_me = Global.getAPIService.readNews("Token " + Global.token, "true");
        Call<ArrayList<NewsResponse>> call_all = Global.getAPIService.readNews("Token " + Global.token, "all");
       // Call<LoginResponse> apiCall = Global.getAPIService.doLogin(new Login("tester", "", "mobiledev", ""));

        if (flag_relevant == 1) {
            call_me.enqueue(new Callback<ArrayList<NewsResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<NewsResponse>> call, Response<ArrayList<NewsResponse>> response) {
                    if(response.code() == 401){
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    if (response.isSuccessful()) {
                        news_me = response.body();
                        for (NewsResponse newsres : news_me) {
                            NewsAdapter adapter = new NewsAdapter(NewsFragment.this.getContext(), news_me);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<NewsResponse>> call, Throwable t) {
                }
            });
        } else {
            call_all.enqueue(new Callback<ArrayList<NewsResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<NewsResponse>> call, Response<ArrayList<NewsResponse>> response) {
                    if(response.code() == 401){
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    if (response.isSuccessful()) {
                        news_me  = response.body();
                        for (NewsResponse newsres : news_me) {
                            NewsAdapter adapter = new NewsAdapter(NewsFragment.this.getContext(), news_me);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<NewsResponse>> call, Throwable t) {
                }
            });
            /*call_other.enqueue(new Callback<ArrayList<NewsResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<NewsResponse>> call, Response<ArrayList<NewsResponse>> response) {
                    if (response.isSuccessful()) {
                        news_other = response.body();
                        for (NewsResponse newsres : news_other) {
                            if(!newsres.toJSON().isEmpty()) {
                                news_me.add(newsres);
                            }

                        }
                        //Log.d("$$$$$$$$$$$$$$$",""+news_me.toArray().toString());
                        for (NewsResponse newsres : news_me) {
                            Log.d("$$$$$$$$$$$$$$$",newsres.toJSON());
                            NewsAdapter adapter = new NewsAdapter(NewsFragment.this.getContext(), news_other);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<NewsResponse>> call, Throwable t) {
                }*/
           // });
        }
    }


    public void postData(){

        ArrayList<Userlist> mentioned_users = new ArrayList<Userlist>();
        Userlist user = new Userlist(Global.user.getId(),Global.user.getFirstname(),Global.user.getLastname(),Global.user.getTitle());
        mentioned_users.add(user);

        postNews_body = new NewsPost(mEdit.getText().toString(),mentioned_users);

        Call<NewsPostResponse> apiCall = Global.getAPIService.postNews("Token " + Global.token, postNews_body);
        apiCall.enqueue(new Callback<NewsPostResponse>() {
            @Override
            public void onResponse(Call<NewsPostResponse> call, Response<NewsPostResponse> response) {
                if(response.code() == 401){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<NewsPostResponse> call, Throwable t) {
                Log.d("$$$$$$#####","Failed");

            }
        });
    }
}
