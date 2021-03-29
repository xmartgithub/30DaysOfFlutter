package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.uberdoktor.android.response.MedicalHistoryResponse;
import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalHistoryActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        sharedPrefManager = new SharedPrefManager(this);

        getSupportActionBar().setTitle("Medical History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getPatientMedicalHistory();

    }

    private void getPatientMedicalHistory() {
        Call<MedicalHistoryResponse> call = RetrofitClient.getInstance().getMyInterface().patientMedicalHistory("Bearer " + sharedPrefManager.getAccessToken());
        call.enqueue(new Callback<MedicalHistoryResponse>() {
            @Override
            public void onResponse(@NotNull Call<MedicalHistoryResponse> call, @NotNull Response<MedicalHistoryResponse> response) {

                assert response.body() != null;

                if (response.isSuccessful()) {

                    Toast.makeText(MedicalHistoryActivity.this, "Medical History successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<MedicalHistoryResponse> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}