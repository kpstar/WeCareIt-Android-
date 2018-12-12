package com.wecareit.fragments.document;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.wecareit.R;
import com.wecareit.common.Global;
import com.wecareit.common.Message;
import com.wecareit.fragments.TemplateFragment;

public class DocumentAddFragment extends TemplateFragment {

    private Spinner mCategory;
    private TextView mFile;

    public static DocumentAddFragment createInstance() {
        return new DocumentAddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_document_add, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Global.toolbar.setTitle("LADDA UPP");
        Global.floatingButton.setVisibility(View.GONE);

        mCategory = (Spinner) view.findViewById(R.id.fragment_document_add_category);
        //mCategory.setOnItemSelectedListener(DocumentAddFragment.this.);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Global.categories);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mCategory.setAdapter(adapter);

        mFile = (TextView) view.findViewById(R.id.fragment_document_add_filename);
        mFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("application/pdf");
                startActivityForResult(Intent.createChooser(i, "Pick a file"), Global.REQUEST_CODE_SELECT_FILE_FOR_IMPORT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        //super.onCreateOptionsMenu(menu,inflater);
    }
}
