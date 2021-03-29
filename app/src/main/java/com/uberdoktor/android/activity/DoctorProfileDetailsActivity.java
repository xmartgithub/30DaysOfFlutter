package com.uberdoktor.android.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.response.DocProfileResponse;
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

public class DoctorProfileDetailsActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int lastClicked = 0;

    LoadingDialog loadingDialog;
    UploadingDialog uploadingDialog;
    Dialog searchAbleDialog;

    DatePickerDialog.OnDateSetListener setListener;
    private static final int REQ_CODE = 21;

    private EditText editTextPhoneNo;
    private EditText editTextUserEmail;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private TextView citiesSpinner;
    private EditText editTextUserProvince;
    private EditText editTextLicenseNo;
    private TextView textViewUserDOB;
    private ImageView imageView;
    private EditText editTextUserCNIC;
    private Spinner genderSpinner;
    private Button saveChangesBtn;
    ////
    String mediaPath;
    Button selectImageBtn;
    Button uploadImageBtn;

    ////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_details);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Doctor Profiles");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextPhoneNo = findViewById(R.id.editTextPhone);
        editTextUserEmail = findViewById(R.id.editTextUserEmail);
        editTextFirstName = findViewById(R.id.editText_first_name);
        editTextLastName = findViewById(R.id.editText_last_name);
        citiesSpinner = findViewById(R.id.doc_cities_spinner);
        editTextUserProvince = findViewById(R.id.editTextProvince);
        editTextLicenseNo = findViewById(R.id.editTextLicense);
        editTextUserCNIC = findViewById(R.id.edittext_cnic);
        genderSpinner = findViewById(R.id.g_spinner);
        saveChangesBtn = findViewById(R.id.save_changes_button);
        imageView = findViewById(R.id.profile_image);
        selectImageBtn = findViewById(R.id.select_img_btn);
        uploadImageBtn = findViewById(R.id.upload_image_btn);
        textViewUserDOB = findViewById(R.id.tvDOB);

        sharedPrefManager = new SharedPrefManager(this);

        loadingDialog = new LoadingDialog(DoctorProfileDetailsActivity.this);
        loadingDialog.startLoadingDialog();
        loadingDialog.progressDialog.show();

        uploadingDialog = new UploadingDialog(DoctorProfileDetailsActivity.this);

        viewProfile();

        /////////////
        final List<String> genderList = new ArrayList<>();
        genderList.add("Male");
        genderList.add("Female");

        sharedPreferences = getSharedPreferences("genderSharedPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        lastClicked = sharedPreferences.getInt("lastClicked", 0);
//
        ArrayAdapter<String> genderArrayAdapter = new ArrayAdapter<>(DoctorProfileDetailsActivity.this, R.layout.support_simple_spinner_dropdown_item, genderList);
        genderSpinner.setAdapter(genderArrayAdapter);
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
        ///////////

        String[] cities = getResources().getStringArray(R.array.cities_spinner);
        citiesSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAbleDialog = new Dialog(DoctorProfileDetailsActivity.this);
                searchAbleDialog.setContentView(R.layout.dialog_searchable_spinner);

                searchAbleDialog.getWindow().setLayout(650, 900);

                searchAbleDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                searchAbleDialog.show();

                EditText editText = searchAbleDialog.findViewById(R.id.editTextSearch);
                ListView listView = searchAbleDialog.findViewById(R.id.list_view);

                ArrayAdapter<String> citiesArrayAdapter = new ArrayAdapter<>(DoctorProfileDetailsActivity.this, R.layout.support_simple_spinner_dropdown_item, cities);
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
                        searchAbleDialog.dismiss();
                    }
                });
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(DoctorProfileDetailsActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
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

    private void uploadImage() {
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        Call<UpdateImageResponse> call = RetrofitClient.getInstance().getMyInterface().uploadDoctorProfileImage("Bearer " + sharedPrefManager.getAccessToken(), fileToUpload);
        call.enqueue(new Callback<UpdateImageResponse>() {
            @Override
            public void onResponse(@NotNull Call<UpdateImageResponse> call, @NotNull Response<UpdateImageResponse> response) {
                UpdateImageResponse updateImageResponse = response.body();
                if (response.isSuccessful()) {
                    uploadingDialog.uploadingProgressDialog.dismiss();
                    assert updateImageResponse != null;
                    Toast.makeText(DoctorProfileDetailsActivity.this, updateImageResponse.getUser(), Toast.LENGTH_SHORT).show();
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
        String userCity = citiesSpinner.getText().toString().trim();
        String userCountry = editTextUserProvince.getText().toString().trim();
        String userDOB = textViewUserDOB.getText().toString().trim();
        String licenseNo = editTextLicenseNo.getText().toString().trim();
        String cnic = editTextUserCNIC.getText().toString().trim();

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
        if (userCity.isEmpty()) {
            citiesSpinner.requestFocus();
            citiesSpinner.setError("City is required");
            return;
        }
        if (userCountry.isEmpty()) {
            editTextUserProvince.requestFocus();
            editTextUserProvince.setError("Country is required");
            return;
        }
        if (userDOB.isEmpty()) {
            textViewUserDOB.requestFocus();
            textViewUserDOB.setError("D.O.B is required");
            return;
        }
        if (licenseNo.isEmpty()) {
            editTextLicenseNo.requestFocus();
            editTextLicenseNo.setError("License no. is required");
            return;
        }
        if (cnic.isEmpty()) {
            editTextUserCNIC.requestFocus();
            editTextUserCNIC.setError("CNIC is required");
            return;
        }

        Call<DocProfileResponse> call = RetrofitClient.getInstance().getMyInterface().updateDoctorProfile("Bearer " + sharedPrefManager.getAccessToken(), firstName, lastName, userEmail, userDOB, cnic, userCity, userCountry, licenseNo);
        call.enqueue(new Callback<DocProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<DocProfileResponse> call, @NotNull Response<DocProfileResponse> response) {

                assert response.body() != null;
                if (response.isSuccessful()) {
                    Toast.makeText(DoctorProfileDetailsActivity.this, "User updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DoctorMainActivity.class));
                }
            }

            @Override
            public void onFailure(@NotNull Call<DocProfileResponse> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewProfile() {
        Call<DocProfileResponse> call = RetrofitClient.getInstance().getMyInterface().viewDoctorProfile("Bearer " + sharedPrefManager.getAccessToken());
        call.enqueue(new Callback<DocProfileResponse>() {

            @Override
            public void onResponse(@NotNull Call<DocProfileResponse> call, @NotNull Response<DocProfileResponse> response) {

                DocProfileResponse docProfileResponse = response.body();
                if (response.isSuccessful()) {
                    loadingDialog.progressDialog.dismiss();
                    ImageView imageView = findViewById(R.id.profile_image);
                    assert docProfileResponse != null;
                    Glide.with(DoctorProfileDetailsActivity.this).load("http://uberdoktor.com" + docProfileResponse.getOwnData().getImageUrl()).into(imageView);
                    editTextPhoneNo.setText(docProfileResponse.getOwnData().getPhoneNo());
                    editTextUserEmail.setText(String.valueOf(docProfileResponse.getOwnData().getEmail()));
                    editTextFirstName.setText(docProfileResponse.getOwnData().getFirstName());
                    editTextLastName.setText(docProfileResponse.getOwnData().getLastName());
                    citiesSpinner.setText(docProfileResponse.getOwnData().getCity());
                    editTextUserProvince.setText(docProfileResponse.getOwnData().getCountry());
                    textViewUserDOB.setText(String.valueOf(docProfileResponse.getOwnData().getDob()));
                    editTextLicenseNo.setText(String.valueOf(docProfileResponse.getOwnData().getLicenseNo()));
                    editTextUserCNIC.setText(String.valueOf(docProfileResponse.getOwnData().getDocumentNo()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<DocProfileResponse> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}