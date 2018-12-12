package com.wecareit.fragments.document;

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
import android.widget.Switch;
import android.widget.TextView;

import com.wecareit.R;
import com.wecareit.adapter.DocumentAdapter;
import com.wecareit.common.Global;
import com.wecareit.common.Message;
import com.wecareit.fragments.TemplateFragment;
import com.wecareit.model.DocCategory;
import com.wecareit.model.Document;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentViewAllFragment extends TemplateFragment {
    private TextView mCategory;
    private RecyclerView mFileList;
    private ArrayList<Document> documents;
    private String string_order;

    public static DocumentViewAllFragment createInstance() {
        return new DocumentViewAllFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_document_view_all, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCategory = (TextView) view.findViewById(R.id.fragment_document_view_all_category);
        mFileList = (RecyclerView) view.findViewById(R.id.fragment_document_view_all_list);
        mFileList.setLayoutManager(new LinearLayoutManager(getContext()));
        mCategory.setText(DocumentFragment.string_category);
        loadData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_document_filter, menu);
        this.setHasOptionsMenu(true);
        if (menu == null) {
            System.out.println("menu is null");
            return;
        }
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.getItem(0);
        View view = item.getActionView();
        Switch mSwitch = (Switch)view.findViewById(R.id.menu_item_document_filter_switch);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    string_order = "date";
                } else{
                    string_order = "filename";
                }
                loadData();
            }
        });
    }

    public void loadData() {
        Call<ArrayList<Document>> call = Global.getAPIService.readAllDocuments("Token " + Global.token, DocumentFragment.category_id,string_order);
        call.enqueue(new Callback<ArrayList<Document>>() {
            @Override
            public void onResponse(Call<ArrayList<Document>> call, Response<ArrayList<Document>> response) {
                if (response.isSuccessful()) {
                    Log.d("alldoc",response.body().toString());
                    documents = response.body();

                    for (Document document : documents) {
                        Message.showObject(document);
                        //if (category.getName().equals(DocumentFragment.string_category)) {
                            //mGeneralSection.setVisibility(View.VISIBLE);
                            DocumentAdapter adapter = new DocumentAdapter(DocumentViewAllFragment.this.getContext(), documents);
                            mFileList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        //}
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Document>> call, Throwable t) {

            }
            }
        );
    }
}
