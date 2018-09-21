package com.akkru.user.akkru;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.akkru.user.akkru.api.model.ExpenseResponse;
import com.akkru.user.akkru.api.model.SetExpense.SetExpenseRespons;
import com.akkru.user.akkru.api.service.ApiClient;
import com.akkru.user.akkru.api.service.UserClient;
import com.akkru.user.akkru.utils.FileUtils;
import com.akkru.user.akkru.utils.PrefManager;
import com.ns.developer.tagview.entity.Tag;
import com.ns.developer.tagview.widget.TagCloudLinkView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpanseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final int RESULT_LOAD_IMAGE = 1;
    private Spinner spinner;
    private EditText etPengeluaran, etMerchant, etTags, etBill;
    private Button btnUploadBills;
    private ImageView ivBill, ivBackButton, ivConfirmExpanse;
    private Toolbar toolbar;
    private Uri selectedImages;
    private String token, merchant, name;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    List<String> tags1;
    TagCloudLinkView tagCloudLinkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanse);
        tags1 = new ArrayList<>();
        prefManager = new PrefManager(this);
        token = prefManager.getString(PrefManager.PREF_TOKEN);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        spinner = findViewById(R.id.spinner_kategori);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.expanse_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btnUploadBills = findViewById(R.id.btn_upload_bill);
        ivBill = findViewById(R.id.iv_bill);
        etBill = findViewById(R.id.et_bill);

        etTags = findViewById(R.id.et_tags);
        btnUploadBills.setOnClickListener(this);
        ivBackButton = findViewById(R.id.iv_back_button);
        ivConfirmExpanse = findViewById(R.id.iv_confirm_expanse);

        ivBackButton.setOnClickListener(this);
        ivConfirmExpanse.setOnClickListener(this);

        tagCloudLinkView = findViewById(R.id.tagv);
        tagCloudLinkView.add(new Tag(1, "Gas"));
        tagCloudLinkView.add(new Tag(1, "Tires"));
        tagCloudLinkView.add(new Tag(1, "Car Payment"));
        tagCloudLinkView.add(new Tag(1, "Oil Changes"));
        tagCloudLinkView.drawTags();
        tagCloudLinkView.setOnTagSelectListener(new TagCloudLinkView.OnTagSelectListener() {
            @Override
            public void onTagSelected(Tag tag, int i) {
                etTags.setText(tag.getText());
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    File file;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImages = data.getData();
            ivBill.setImageURI(selectedImages);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload_bill:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.
                        Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;

            case R.id.iv_back_button:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;

            case R.id.iv_confirm_expanse:
                uploadFile(selectedImages);

                break;
        }
    }

    private void uploadFile(Uri uri) {
        progressDialog.setMessage("Uploading . . .");
        showDialog();
        final EditText name = findViewById(R.id.et_pengeluaran);
        final EditText merchant = findViewById(R.id.et_merchant);
        String namaPengeluaran = name.getText().toString();
        String namaMerchant = merchant.getText().toString();
        int category = spinner.getSelectedItemPosition() + 1;
        //RequestBody token1 = RequestBody.create(MultipartBody.FORM, token);
        //RequestBody namaPengeluaran = RequestBody.create(MultipartBody.FORM, "Beli Mie");
        //RequestBody namaMerchant = RequestBody.create(MultipartBody.FORM, merchant.getText().toString());

        File originalFile = FileUtils.getFile(this, uri);
        RequestBody bill = RequestBody.create(
                MediaType.parse(this.getContentResolver().getType(uri)),
                originalFile
        );

        MultipartBody.Part file1 = MultipartBody.Part.createFormData("avatar", originalFile.getName(), bill);
        UserClient userClient = ApiClient.getClient().create(UserClient.class);
        Call<SetExpenseRespons> call = userClient.setExpense(token, namaPengeluaran, file1, namaMerchant, category);
        call.enqueue(new Callback<SetExpenseRespons>() {
            @Override
            public void onResponse(Call<SetExpenseRespons> call, Response<SetExpenseRespons> response) {
                if (response.isSuccessful()) {
                    hideDialog();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } else {
                    Toast.makeText(getApplication(), String.valueOf(response.message()), Toast.LENGTH_SHORT).show();
                    hideDialog();
                }
            }

            @Override
            public void onFailure(Call<SetExpenseRespons> call, Throwable t) {
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_SHORT).show();
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.expanse_toolbar,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.confirm_expanse:
//                Toast.makeText(this,"Confirm",Toast.LENGTH_SHORT).show();
//                return true;
//                default:
//                    return super.onOptionsItemSelected(item);
//        }
//
//    }

