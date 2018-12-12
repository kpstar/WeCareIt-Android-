package com.wecareit.viewholder;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wecareit.R;
import com.wecareit.common.Global;
import com.wecareit.model.Document;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentViewHolder extends RecyclerView.ViewHolder {
    private Document document;

    private TextView mFileName;
    private TextView mDate;
    private ImageView mTrash;
    private String string_dialog, string_url;
    private AlertDialog alertDialog;

    public DocumentViewHolder(@NonNull View itemView) {
        super(itemView);

        Context context = itemView.getContext();

        mFileName = (TextView) itemView.findViewById(R.id.recyclerview_row_document_filename);
        mDate = (TextView) itemView.findViewById(R.id.recyclerview_row_document_date);
        mTrash = (ImageView) itemView.findViewById(R.id.recyclerview_row_document_trash);
        mTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage(string_dialog);
                alertDialogBuilder.setPositiveButton("Ta bort", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete_Doc();
                        alertDialog.dismiss();
                    }
                });
                alertDialogBuilder.setNegativeButton("AVBRYT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void setContent(Document document) {
        this.document = document;
        mFileName.setText(document.getFilename());
        mDate.setText(document.getDate());
        string_url = document.getUrl();
        string_dialog = "Är du säker på att du vill ta bort '" + document.getFilename() + "'?";
    }

    public void delete_Doc(){
        Call<ArrayList<Document>> call = Global.getAPIService.deleteDocuments("Token " + Global.token, string_url);
        call.enqueue(new Callback<ArrayList<Document>>() {
            @Override
            public void onResponse(Call<ArrayList<Document>> call, Response<ArrayList<Document>> response) {
                if (response.isSuccessful()) {
                    Log.d("deleted",response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Document>> call, Throwable t) {

            }
        });
    }
}
