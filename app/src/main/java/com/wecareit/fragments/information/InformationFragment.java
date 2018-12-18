package com.wecareit.fragments.information;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;

import com.wecareit.LoginActivity;
import com.wecareit.R;
import com.wecareit.adapter.InformationAdapter;
import com.wecareit.common.Global;
import com.wecareit.model.InfoRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationFragment extends Fragment {

    private ArrayList<InfoRes> info;
    private LinearLayout lnTab1,lnTab2,lnTab1line, lnTab2line, lnTab;
    private RecyclerView mRecyclerView;
    private ScrollView mScrollView;
    private LinearLayout mLinearLayout;
    private Button mSavebtn, mCancelbtn;
    private EditText mTitleEdit, mDescEdit;
    private static int flag_relevant = 1;
    public static int flagEdit_informationfragment = 0, id_information;
    public static String stTitle = "", stDesc = "";

    private boolean isBold, isItalic;

    public static InformationFragment createInstance() {
        return new InformationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // this is how we load a specific layout
        return inflater.inflate(R.layout.fragment_information, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Global.toolbar.setNavigationIcon(R.drawable.ic_side_menu);
        Global.toolbar.setTitle("INFOMATION");

        lnTab = view.findViewById(R.id.lnTab_informationfragment);
        lnTab1 = view.findViewById(R.id.lnTab1_information);
        lnTab1line = view.findViewById(R.id.lnTab1line_information);
        lnTab2 = view.findViewById(R.id.lnTab2_information);
        lnTab2line = view.findViewById(R.id.lnTab2line_information);
        mRecyclerView = view.findViewById(R.id.framgment_information_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mScrollView = view.findViewById(R.id.framgment_information_scrollview);
        mLinearLayout = view.findViewById(R.id.framgment_information_editview);
        mSavebtn = view.findViewById(R.id.btnSave_informationfragment);
        mCancelbtn = view.findViewById(R.id.btnCancel_informationfragment);
        mTitleEdit = view.findViewById(R.id.etTitle_informationfragment);
        mDescEdit = view.findViewById(R.id.etDesc_informationfragment);

        lnTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_relevant =1;
                lnTab1.setBackgroundColor( getResources().getColor(R.color.colorCardView));
                lnTab1line.setVisibility(View.VISIBLE);
                lnTab2.setBackgroundColor( getResources().getColor(R.color.colorWhite));
                lnTab2line.setVisibility(View.GONE);
                loadData();
            }
        });

        lnTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_relevant =2;
                lnTab1.setBackgroundColor( getResources().getColor(R.color.colorWhite));
                lnTab1line.setVisibility(View.GONE);
                lnTab2.setBackgroundColor( getResources().getColor(R.color.colorCardView));
                lnTab2line.setVisibility(View.VISIBLE);
                loadData();
            }
        });

        mSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //postData();
                flagEdit_informationfragment = 0;
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

        mCancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagEdit_informationfragment = 0;
                loadData();
            }
        });

        loadData();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_information_filter, menu);
        this.setHasOptionsMenu(true);
        if (menu == null) {
            System.out.println("menu is null");
            return;
        }
        /*super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.getItem(0);
        View view = item.getActionView();

        Switch mSwitch = (Switch)view.findViewById(R.id.menu_item_information_filter_switch);

        if (flag_relevant == 1){
            mSwitch.setChecked(false);
        } else {
            mSwitch.setChecked(true);
        }

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    flag_relevant =2;
                } else{
                    flag_relevant =1;
                }
                loadData();
            }
        });*/
        //super.onCreateOptionsMenu(menu,inflater);
    }

    public void loadData() {

        if(flagEdit_informationfragment == 0){
            setHasOptionsMenu(true);
            lnTab.setVisibility(View.VISIBLE);
            mScrollView.setVisibility(View.VISIBLE);
            mLinearLayout.setVisibility(View.GONE);
            //Log.d("Flag",""+flag_relevant);
        } else {
            setHasOptionsMenu(false);
            flagEdit_informationfragment = 0;
            lnTab.setVisibility(View.GONE);
            mScrollView.setVisibility(View.GONE);
            mLinearLayout.setVisibility(View.VISIBLE);
            mTitleEdit.setText(stTitle);
            mDescEdit.setText(stDesc);
            //Log.d("Flag",""+flag_relevant);
        }

        Call<ArrayList<InfoRes>> call = Global.getAPIService.readInfo("Token " + Global.token, flag_relevant);

        call.enqueue(new Callback<ArrayList<InfoRes>>() {
            @Override
            public void onResponse(Call<ArrayList<InfoRes>> call, Response<ArrayList<InfoRes>> response) {
                if(response.code() == 401){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                if (response.isSuccessful()) {
                    info = response.body();
                    for (InfoRes infores : info) {
                        InformationAdapter adapter = new InformationAdapter(InformationFragment.this.getContext(), info);
                        mRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<InfoRes>> call, Throwable t) {
            }
        });
    }

    public void postData(){

        Call<InfoRes> call = Global.getAPIService.postInfo("Token " + Global.token, flag_relevant, id_information, mDescEdit.getText().toString());
        Log.d("&&&&&",mDescEdit.getText().toString()+":"+flag_relevant);
        call.enqueue(new Callback<InfoRes>() {
            @Override
            public void onResponse(Call<InfoRes> call, Response<InfoRes> response) {
                if (response.isSuccessful()) {
                    InfoRes infoRes = response.body();
                    Log.d("&&&&&",infoRes.getText());
                }
            }
            @Override
            public void onFailure(Call<InfoRes> call, Throwable t) {
                Log.d("&&&&&","Failed");
            }
        });

    }
}
