package com.wecareit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wecareit.common.Global;
import com.wecareit.model.LoginResponse;
import com.wecareit.model.Login;
import com.wecareit.retrofit.APIClient;
import com.wecareit.retrofit.GetAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText mSubdomainView;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private Button mSigninButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Global.getAPIService = APIClient.getRetrofitInstance().create(GetAPIService.class);

        mSubdomainView = (EditText) findViewById(R.id.activity_login_subdomain);
        mUsernameView = (EditText) findViewById(R.id.activity_login_username);
        mPasswordView = (EditText) findViewById(R.id.activity_login_password);

        mSigninButton = (Button) findViewById(R.id.activity_login_signin);
        mSigninButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call<LoginResponse> apiCall = Global.getAPIService.doLogin(new Login(mUsernameView.getText().toString(), mSubdomainView.getText().toString(), mPasswordView.getText().toString(), ""));
//                Call<LoginResponse> apiCall = Global.getAPIService.doLogin(new Login("vikarie", "", "mobiledev1", ""));
                Call<LoginResponse> apiCall = Global.getAPIService.doLogin(new Login("tester", "", "mobiledev", ""));
//                Call<LoginResponse> apiCall = Global.getAPIService.doLogin(new Login("nurse", "", "mobiledev2", ""));

                apiCall.enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Global.user = response.body().getUser();
                            Global.token = response.body().getKey();
                            Global.user_ID = response.body().getUser().getId();
                            Log.e("mToken =", response.body().getKey());
                            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                            LoginActivity.this.startActivity(intent);
                            LoginActivity.this.finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Ange ett korrekt användarnamn och lösenord. Observera att båda fälten är skiftlägeskänsliga.", Toast.LENGTH_LONG).show();
                        mUsernameView.setText("");
                        mPasswordView.setText("");
                    }
                });
            }
        });
    }



    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

    }

}

