package com.akkru.user.akkru;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import com.akkru.user.akkru.api.model.Auth;
import com.akkru.user.akkru.api.model.Login2;
import com.akkru.user.akkru.api.model.User;
import com.akkru.user.akkru.api.service.ApiClient;
import com.akkru.user.akkru.api.service.UserClient;
import com.akkru.user.akkru.utils.PrefManager;

import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    Toolbar toolbar;
    ProgressDialog progressDialog;
    private EditText loginusername , loginpassword;
    private Button btnlogin;
    private Button btnlinkSignUp;
    public String token;
    PrefManager prefManager;

//    private ImageButton btn_back;

    //Retrofit
    final UserClient userClient = ApiClient.getClient().create(UserClient.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        toolbar = findViewById(R.id.toolbar);
        loginusername = (EditText) findViewById(R.id.login_input_username);
        loginpassword = (EditText) findViewById(R.id.login_input_password);
        btnlogin = (Button) findViewById(R.id.btn_login);
        btnlinkSignUp = (Button) findViewById(R.id.btnLinkSignUp);

        prefManager = new PrefManager(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(loginusername.getText().toString(),loginpassword.getText().toString());
            }
        });

        btnlinkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    Auth auth;
    private void loginUser(final String username , final String password){
        String cancel_reg_tag = "login";
        progressDialog.setMessage("Login . . .");
        showDialog();
        auth = new Auth(username, password);

        Login2 login = new Login2(auth);
        Call<User> call = userClient.login(login);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, response.body().getToken(),Toast.LENGTH_LONG).show();
                    prefManager.setString(PrefManager.PREF_USERNAME, response.body().getEmail());
                    prefManager.setString(PrefManager.PREF_TOKEN, response.body().getToken());
                    prefManager.setBoolean(PrefManager.PREF_LOGIN, true);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    hideDialog();
                }
                else {
                    String errorMsg = "wrong username or password";
                    Toast.makeText(getApplicationContext(),
                            response.message(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Connection", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


}
