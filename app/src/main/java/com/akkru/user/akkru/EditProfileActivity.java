package com.akkru.user.akkru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akkru.user.akkru.api.model.EditUser;
import com.akkru.user.akkru.api.model.EditUserResponse;
import com.akkru.user.akkru.api.model.ResultResponse;
import com.akkru.user.akkru.api.model.User;
import com.akkru.user.akkru.api.model.UserData;
import com.akkru.user.akkru.api.model.UserResponse;
import com.akkru.user.akkru.api.service.ApiClient;
import com.akkru.user.akkru.api.service.UserClient;
import com.akkru.user.akkru.utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";
    CircleImageView avatarCI;
    EditText emailET;
    EditText passwordET;
    EditText passwordconfET;
    Button editedBtn;
    JSONObject data;
    PrefManager prefManager;
    private String token, emailpref, email, password, passwordconf;
    private String getEmail, getPass, getpasswordconf;
    ProgressDialog progressDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        prefManager = new PrefManager(getApplication());
        token = prefManager.getString(PrefManager.PREF_TOKEN);
        emailpref = prefManager.getString(PrefManager.PREF_USERNAME);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        avatarCI = (CircleImageView) findViewById(R.id.avatar);
        emailET = (EditText) findViewById(R.id.email);
        passwordET = (EditText) findViewById(R.id.password);
        passwordconfET = (EditText) findViewById(R.id.password_conf);
        editedBtn = (Button) findViewById(R.id.edit_profileBtn);

        getEmail = emailET.getText().toString();
        getPass = passwordET.getText().toString();
        getpasswordconf = passwordconfET.getText().toString();

        editedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getEmail == null) {
                    getEmail = emailpref;
                }
                progressDialog.setMessage("Login . . .");
                showDialog();
                UserClient userClient = ApiClient.getClient().create(UserClient.class);
                Call<EditUserResponse> call = userClient.editUser(
                        token, "Dian111", getEmail, getPass, getPass
                );
                call.enqueue(new Callback<EditUserResponse>() {
                    @Override
                    public void onResponse(Call<EditUserResponse> call, Response<EditUserResponse> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            hideDialog();
                            finish();
                        } else {
                            hideDialog();
                            Toast.makeText(getApplication(), response.message(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<EditUserResponse> call, Throwable t) {
                        hideDialog();
                        Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
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
