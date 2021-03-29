package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.uberdoktor.android.adapter.DoctorConsultationsAdapter;
import com.uberdoktor.android.response.DoctorConsultationResponse;
import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorConsultationActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    RecyclerView doctorConsultationsRV;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_consultation);

        sharedPrefManager = new SharedPrefManager(this);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Consultations");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        doctorConsultationsRV = findViewById(R.id.doctor_consultations_rv);
        doctorConsultationsRV.setLayoutManager(new LinearLayoutManager(this));

        loadingDialog = new LoadingDialog(DoctorConsultationActivity.this);
        loadingDialog.startLoadingDialog();
        loadingDialog.progressDialog.show();

        viewConsultations();
    }

    private void viewConsultations() {
        Call<DoctorConsultationResponse> call = RetrofitClient.getInstance().getMyInterface().doctorConsultations("Bearer " + sharedPrefManager.getAccessToken());
        call.enqueue(new Callback<DoctorConsultationResponse>() {
            @Override
            public void onResponse(@NotNull Call<DoctorConsultationResponse> call, @NotNull Response<DoctorConsultationResponse> response) {

                assert response.body() != null;

                if (response.isSuccessful()) {
                    loadingDialog.progressDialog.dismiss();
                    doctorConsultationsRV.setAdapter(new DoctorConsultationsAdapter(DoctorConsultationActivity.this, response.body().getDoctorCheckupData()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<DoctorConsultationResponse> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), "No data found", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), call.request().url().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}