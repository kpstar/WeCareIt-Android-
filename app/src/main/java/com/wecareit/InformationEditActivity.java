package com.wecareit;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wecareit.common.Global;
import com.wecareit.model.InfoRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationEditActivity extends AppCompatActivity {

    private EditText mDescEdit, mTitle;
    private TextView mNumbers;
    int flag_relevant, id_information;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Redigera info");

        mTitle = (EditText)findViewById(R.id.etTitle_informationfragment);
        mDescEdit = (EditText)findViewById(R.id.etDesc_informationfragment);
        mDescEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setNumbers();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mNumbers = (TextView)findViewById(R.id.letterNumberTxt);
        mTitle.setText(getIntent().getStringExtra("Title"));
        mDescEdit.setText(getIntent().getStringExtra("Desc"));
        setNumbers();
        flag_relevant = getIntent().getIntExtra("EditFlag", 0);
        id_information = getIntent().getIntExtra("InfoId", 0);


        sharedPreferences = this.getSharedPreferences("Fragment", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Current", "Information");
        editor.apply();
    }

    public void setNumbers() {
        int number = mDescEdit.getText().toString().length();
        mNumbers.setText(String.format("%d/500", number));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_register_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                InformationEditActivity.this.finish();
                break;
            case R.id.menu_save_register:
                saveData();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void saveData(){

        Call<InfoRes> call = Global.getAPIService.postInfo("Token " + Global.token, flag_relevant, id_information, mDescEdit.getText().toString());
        call.enqueue(new Callback<InfoRes>() {
            @Override
            public void onResponse(Call<InfoRes> call, Response<InfoRes> response) {
                if (response.isSuccessful()) {
                    InfoRes infoRes = response.body();
                    InformationEditActivity.this.finish();
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