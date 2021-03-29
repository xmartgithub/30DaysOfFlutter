package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.response.AddDoctorScheduleResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocAddSpecialityActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    private EditText editTextSpecialityName;
    private Button saveDegreeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_speciality);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Speciality");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPrefManager = new SharedPrefManager(this);

        editTextSpecialityName = findViewById(R.id.editText_speciality);
        saveDegreeBtn = findViewById(R.id.update_speciality_btn);

        saveDegreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSpeciality();
            }
        });
    }

    private void addSpeciality() {
        String specialityName = editTextSpecialityName.getText().toString().trim();


        if (specialityName.isEmpty()) {
            editTextSpecialityName.requestFocus();
            editTextSpecialityName.setError("Speciality is required");
            return;
        }


        Call<AddDoctorScheduleResponse> call = RetrofitClient.getInstance().getMyInterface().addSpecialty("Bearer " + sharedPrefManager.getAccessToken(), specialityName);
        call.enqueue(new Callback<AddDoctorScheduleResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddDoctorScheduleResponse> call, @NotNull Response<AddDoctorScheduleResponse> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Toast.makeText(DocAddSpecialityActivity.this, "Speciality added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddDoctorScheduleResponse> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}