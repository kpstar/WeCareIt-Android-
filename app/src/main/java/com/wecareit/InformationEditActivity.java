package com.wecareit;

import android.content.Context;
import android.content.Intent;
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

import com.chinalwb.are.AREditText;
import com.chinalwb.are.AREditor;
import com.chinalwb.are.styles.toolbar.IARE_Toolbar;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Bold;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Italic;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_ListBullet;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Strikethrough;
import com.chinalwb.are.styles.toolitems.ARE_ToolItem_Underline;
import com.chinalwb.are.styles.toolitems.IARE_ToolItem;
import com.wecareit.common.Global;
import com.wecareit.fragments.tasks.TasksAddActivity;
import com.wecareit.model.InfoRes;

import jp.wasabeef.richeditor.RichEditor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.noties.markwon.Markwon;

public class InformationEditActivity extends AppCompatActivity implements RichEditText.RichEditTextListener {

    private EditText mTitle;
    private IARE_Toolbar mToolbar;
    private AREditText arEditText;
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

        mNumbers = (TextView)findViewById(R.id.letterNumberTxt);
        mTitle.setText(getIntent().getStringExtra("Title"));
        arEditText = (AREditText)findViewById(R.id.arEditText);
//        arEditText.setText(getIntent().getStringExtra("Desc"));
        String desc = getIntent().getStringExtra("Desc");
//        desc = desc.replace("\"", "");
//        Markwon.setMarkdown(arEditText, desc);
        arEditText.fromHtml(desc);
        initToolbar();


        setNumbers();
        SharedPreferences sharedPreferences = this.getSharedPreferences("Fragment", Context.MODE_PRIVATE);
        flag_relevant = sharedPreferences.getInt("tabID", 1);
        id_information = getIntent().getIntExtra("InfoId", 0);


        sharedPreferences = this.getSharedPreferences("Fragment", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Current", "Information");
        editor.apply();
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.areToolbar);
        IARE_ToolItem bold = new ARE_ToolItem_Bold();
        IARE_ToolItem italic = new ARE_ToolItem_Italic();
        IARE_ToolItem listBullet = new ARE_ToolItem_ListBullet();
        mToolbar.addToolbarItem(bold);
        mToolbar.addToolbarItem(italic);
        mToolbar.addToolbarItem(listBullet);

        arEditText.setToolbar(mToolbar);

//        setHtml();
    }


    public void setNumbers() {
        int number = arEditText.getText().toString().length();
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

        Call<InfoRes> call = Global.getAPIService.postInfo("Token " + Global.token, flag_relevant, id_information, arEditText.getHtml().replace("\n", "").replace("\"", ""));
        call.enqueue(new Callback<InfoRes>() {
            @Override
            public void onResponse(Call<InfoRes> call, Response<InfoRes> response) {
                if(response.code() == 401){
                    Intent intent = new Intent(InformationEditActivity.this, LoginActivity.class);
                    startActivity(intent);
                    InformationEditActivity.this.finish();
                }
                if (response.isSuccessful()) {
                    InfoRes infoRes = response.body();
                    InformationEditActivity.this.finish();
                    SharedPreferences.Editor editor = InformationEditActivity.this.getSharedPreferences("Message", Context.MODE_PRIVATE).edit();
                    editor.putString("infoFragment", "Informationen uppdaterad!");
                    editor.apply();
                    Log.d("&&&&&",infoRes.getText());
                }
            }
            @Override
            public void onFailure(Call<InfoRes> call, Throwable t) {
                Log.d("&&&&&","Failed");
            }
        });

    }

    @Override
    public void onChanged(CharSequence text, int i1, int i2, int i3) {
        setNumbers();
    }
}
