package com.uberdoktor.android.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.uberdoktor.android.response.ProfileResponse;
import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.model.UserProfile;
import com.uberdoktor.android.response.UpdateImageResponse;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientProfileDetailsActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor1;

    UploadingDialog uploadingDialog;
    LoadingDialog loadingDialog;
    Dialog searchAbleDialog;

    int lastClicked = 0;
    int lastClicked1 = 0;

    Button saveChangesBtn;
    UserProfile userProfile;
    DatePickerDialog.OnDateSetListener setListener;

    private EditText editTextPhoneNo;
    private EditText editTextUserEmail;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextUserCNIC;
    private EditText editTextUserProvince;
    private TextView citiesSpinner;
    private TextView textViewUserDOB;
    public ImageView imageView;
    ////
    String mediaPath;

    Button selectImageBtn;
    Button uploadImageBtn;
    ////

    private Spinner genderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_details);

        Objects.requireNonNull(getSupportActionBar()).setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextPhoneNo = findViewById(R.id.editTextPhone);
        editTextUserEmail = findViewById(R.id.editTextUserEmail);
        editTextFirstName = findViewById(R.id.editText_first_name);
        editTextLastName = findViewById(R.id.editText_last_name);
        editTextUserProvince = findViewById(R.id.editTextProvince);
        editTextUserCNIC = findViewById(R.id.edittext_cnic);
        citiesSpinner = findViewById(R.id.p_cities_spinner);
        genderSpinner = findViewById(R.id.g_spinner);
        saveChangesBtn = findViewById(R.id.save_changes_button);
        uploadImageBtn = findViewById(R.id.upload_image_btn);
        imageView = findViewById(R.id.profile_image);

        selectImageBtn = findViewById(R.id.select_img_btn);
        textViewUserDOB = findViewById(R.id.tvDOB);

        userProfile = new UserProfile();
        sharedPrefManager = new SharedPrefManager(this);

        //loading / progress dialog
        uploadingDialog = new UploadingDialog(PatientProfileDetailsActivity.this);
        loadingDialog = new LoadingDialog(PatientProfileDetailsActivity.this);
        loadingDialog.startLoadingDialog();
        loadingDialog.progressDialog.show();

        viewProfile();

        /////////////
        final List<String> genderList = new ArrayList<>();
        genderList.add("Select your gender");
        genderList.add("Male");
        genderList.add("Female");

        sharedPreferences = getSharedPreferences("genderSharedPref", MODE_PRIVATE);
        sharedPreferences1 = getSharedPreferences("citySharedPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor1 = sharedPreferences1.edit();

        lastClicked = sharedPreferences.getInt("lastClicked", 0);
        lastClicked1 = sharedPreferences1.getInt("lastClicked1", 0);
//
        ArrayAdapter<String> genderArrayAdapter = new ArrayAdapter<>(PatientProfileDetailsActivity.this, R.layout.support_simple_spinner_dropdown_item, genderList);
        genderSpinner.setAdapter(genderArrayAdapter);


        String[] cities = getResources().getStringArray(R.array.cities_spinner);


        citiesSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAbleDialog = new Dialog(PatientProfileDetailsActivity.this);
                searchAbleDialog.setContentView(R.layout.dialog_searchable_spinner);

                searchAbleDialog.getWindow().setLayout(650, 900);
                searchAbleDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                searchAbleDialog.show();

                EditText editText = searchAbleDialog.findViewById(R.id.editTextSearch);
                ListView listView = searchAbleDialog.findViewById(R.id.list_view);

                ArrayAdapter<String> citiesArrayAdapter = new ArrayAdapter<>(PatientProfileDetailsActivity.this, R.layout.support_simple_spinner_dropdown_item, cities);
                listView.setAdapter(citiesArrayAdapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        citiesArrayAdapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        citiesSpinner.setText(citiesArrayAdapter.getItem(position));
                        String selectedString = citiesArrayAdapter.getItem(position);
                        editor1.putString("spinnerSelected", selectedString);
                        editor1.apply();
                        searchAbleDialog.dismiss();
                    }
                });
            }
        });

//
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("lastClicked", position);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        genderSpinner.setSelection(lastClicked);


        ////////////
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (shouldAskPermissions()) {
                    askPermissions();
                }
                selectImage();
                uploadImageBtn.setVisibility(View.VISIBLE);
            }
        });

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                uploadingDialog.startUploadingDialog();
                uploadingDialog.uploadingProgressDialog.show();
                uploadImage();
            }
        });


        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.MONTH);

        textViewUserDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PatientProfileDetailsActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                String date = i2 + "/" + i1 + "/" + i;
                textViewUserDOB.setText(date);
            }
        };
    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    private void selectImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // When an Image is picked
        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

            // Get the Image from data
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            Toast.makeText(this, mediaPath, Toast.LENGTH_SHORT).show();
            // Set the Image in ImageView for Previewing the Media
            imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            cursor.close();

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }

    }

    private void uploadImage() {
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        Call<UpdateImageResponse> call = RetrofitClient.getInstance().getMyInterface().uploadPatientProfileImage("Bearer " + sharedPrefManager.getAccessToken(), fileToUpload);
        call.enqueue(new Callback<UpdateImageResponse>() {
            @Override
            public void onResponse(@NotNull Call<UpdateImageResponse> call, @NotNull Response<UpdateImageResponse> response) {
                UpdateImageResponse updateImageResponse = response.body();
                if (response.isSuccessful()) {
                    uploadingDialog.uploadingProgressDialog.dismiss();
                    Toast.makeText(PatientProfileDetailsActivity.this, updateImageResponse.getUser(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UpdateImageResponse> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String userEmail = editTextUserEmail.getText().toString().trim();
        String cnic = editTextUserCNIC.getText().toString().trim();
        String userCountry = editTextUserProvince.getText().toString().trim();
        String userDOB = textViewUserDOB.getText().toString().trim();

        if (firstName.isEmpty()) {
            editTextFirstName.requestFocus();
            editTextFirstName.setError("First name is required");
            return;
        }
        if (lastName.isEmpty()) {
            editTextLastName.requestFocus();
            editTextLastName.setError("Last name is required");
            return;
        }
        if (userEmail.isEmpty()) {
            editTextUserEmail.requestFocus();
            editTextUserEmail.setError("Email is required");
            return;
        }

        if (userCountry.isEmpty()) {
            editTextUserProvince.requestFocus();
            editTextUserProvince.setError("Country is required");
            return;
        }
        if (userDOB.isEmpty()) {
            textViewUserDOB.requestFocus();
            textViewUserDOB.setError("DOB is required");
            return;
        }
        if (cnic.isEmpty()) {
            editTextUserCNIC.requestFocus();
            editTextUserCNIC.setError("CNIC is required");
            return;
        }

        Call<ProfileResponse> call = RetrofitClient.getInstance().getMyInterface().updateUserProfile("Bearer " + sharedPrefManager.getAccessToken(), firstName, lastName, userEmail, userDOB, cnic, genderSpinner.getSelectedItem().toString(), citiesSpinner.getText().toString(), userCountry);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProfileResponse> call, @NotNull Response<ProfileResponse> response) {

                assert response.body() != null;
                userProfile = response.body().getUserProfile();
                if (response.isSuccessful()) {
                    Toast.makeText(PatientProfileDetailsActivity.this, "User updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PatientProfileDetailsActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ProfileResponse> call, @NotNull Throwable t) {
                Toast.makeText(PatientProfileDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewProfile() {
        Call<ProfileResponse> call = RetrofitClient.getInstance().getMyInterface().userProfile("Bearer " + sharedPrefManager.getAccessToken());
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProfileResponse> call, @NotNull Response<ProfileResponse> response) {

                assert response.body() != null;
                userProfile = response.body().getUserProfile();
                if (response.isSuccessful()) {
                    loadingDialog.progressDialog.dismiss();
                    ImageView imageView = findViewById(R.id.profile_image);
                    Glide.with(PatientProfileDetailsActivity.this).load("http://uberdoktor.com" + response.body().getUserProfile().getImageUrl()).into(imageView);
                    editTextPhoneNo.setText(userProfile.getPhoneNo());
                    editTextUserEmail.setText(userProfile.getEmail());
                    editTextFirstName.setText(userProfile.getFirstName());
                    editTextLastName.setText(userProfile.getLastName());
                    editTextUserProvince.setText(userProfile.getCountry());
                    textViewUserDOB.setText(userProfile.getDob());
                    editTextUserCNIC.setText(userProfile.getDocumentNo());
                    citiesSpinner.setText(userProfile.getCity());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ProfileResponse> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


