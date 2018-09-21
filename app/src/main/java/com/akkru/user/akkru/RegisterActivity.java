package com.akkru.user.akkru;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.akkru.user.akkru.api.model.Auth;
import com.akkru.user.akkru.api.model.Login2;
import com.akkru.user.akkru.api.model.User;
import com.akkru.user.akkru.api.model.UserData;
import com.akkru.user.akkru.api.service.ApiClient;
import com.akkru.user.akkru.api.service.UserClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private EditText fullname , email , password, password_conf;
    ProgressDialog progressDialog;
    private Button btn_signup;
    private ImageButton back_btn;
    Toolbar toolbar;
    UserData userdata;

    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Retrofit
        final UserClient createUser = ApiClient.getClient().create(UserClient.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        toolbar = findViewById(R.id.toolbar);
        fullname = (EditText) findViewById(R.id.signup_input_fullname);
        password = (EditText) findViewById(R.id.signup_input_password);
        password_conf = (EditText) findViewById(R.id.password_conf) ;
        email = (EditText) findViewById(R.id.signup_input_email);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signUpUser(fullname.getText().toString(), email.getText().toString(),password.getText().toString() , password_conf.getText().toString());

                String name = fullname.getText().toString();
                String emailx = email.getText().toString();
                String password1 = password.getText().toString();
                String password2 = password_conf.getText().toString();
                progressDialog.setMessage("sign up your data");
                showDialog();


                userdata = new UserData(name,emailx,password1,password2);
                Call<UserData> call = createUser.createUser(userdata);
                call.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Sign Up Success",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                            hideDialog();
                        } else {
                            Toast.makeText(getApplicationContext(),response.message(), Toast.LENGTH_LONG).show();
                            hideDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error Connection", Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                });
            }
        });



    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
