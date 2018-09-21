package com.akkru.user.akkru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.akkru.user.akkru.api.model.SetIncomeResponse;
import com.akkru.user.akkru.api.service.ApiClient;
import com.akkru.user.akkru.api.service.UserClient;
import com.akkru.user.akkru.utils.PrefManager;
import com.cunoraz.tagview.TagView;
import com.ns.developer.tagview.widget.TagCloudLinkView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TagView tagGroup;
    private EditText editText;
    private ImageView ivBackButton, ivConfirmIncome;
    private EditText etNamaPemasukan, etPemasukan, etTags;
    private String token, name, total, category;
    PrefManager prefManager;
    TagCloudLinkView tagCloudLinkView;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        prefManager = new PrefManager(this);
        token = prefManager.getString(PrefManager.PREF_TOKEN);

        etTags = findViewById(R.id.et_tags2);
        etNamaPemasukan = findViewById(R.id.et_namapemasukan);
        etPemasukan = findViewById(R.id.et_pemasukan);
        ivBackButton = findViewById(R.id.iv_back_button);
        ivConfirmIncome = findViewById(R.id.iv_confirm_income);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        ivBackButton.setOnClickListener(this);
        ivConfirmIncome.setOnClickListener(this);
        tagCloudLinkView = findViewById(R.id.tagv);
        tagCloudLinkView.add(new com.ns.developer.tagview.entity.Tag(1, "Salary"));
        tagCloudLinkView.add(new com.ns.developer.tagview.entity.Tag(2, "Loans"));
        tagCloudLinkView.add(new com.ns.developer.tagview.entity.Tag(3, "Grants"));
        tagCloudLinkView.add(new com.ns.developer.tagview.entity.Tag(4, "Reimbursement"));
        tagCloudLinkView.add(new com.ns.developer.tagview.entity.Tag(5, "Other"));
        tagCloudLinkView.drawTags();
        tagCloudLinkView.setOnTagSelectListener(new TagCloudLinkView.OnTagSelectListener() {
            @Override
            public void onTagSelected(com.ns.developer.tagview.entity.Tag tag, int i) {
                etTags.setText(tag.getText());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_button:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;

            case R.id.iv_confirm_income:
                name = etNamaPemasukan.getText().toString();
                total = etPemasukan.getText().toString();
                category = etTags.getText().toString();

                UserClient userClient = ApiClient.getClient().create(UserClient.class);
                Call<SetIncomeResponse> call = userClient.setIncome(
                        token, name, total, category
                );
                showDialog();
                call.enqueue(new Callback<SetIncomeResponse>() {
                    @Override
                    public void onResponse(Call<SetIncomeResponse> call, Response<SetIncomeResponse> response) {
                        if (response.isSuccessful()) {
                            hideDialog();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(IncomeActivity.this, String.valueOf(response.message()), Toast.LENGTH_LONG).show();
                            hideDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<SetIncomeResponse> call, Throwable t) {
                        Toast.makeText(IncomeActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                });
                break;
        }
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
