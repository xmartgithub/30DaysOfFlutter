package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.response.DoctorEarningsResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarningsActivity extends AppCompatActivity {

    TextView totalEarnings, totalPatients;
    SharedPrefManager sharedPrefManager;

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings);

        getSupportActionBar().setTitle("My Earnings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sharedPrefManager = new SharedPrefManager(this);

        totalEarnings = findViewById(R.id.tv_total_earnings);
        totalPatients = findViewById(R.id.tv_total_patients);
        loadingDialog = new LoadingDialog(EarningsActivity.this);
        loadingDialog.startLoadingDialog();
        loadingDialog.progressDialog.show();
        getTotalEarningsAndPatient();
    }

    private void getTotalEarningsAndPatient() {
        Call<DoctorEarningsResponse> call = RetrofitClient.getInstance().getMyInterface().getEarnings("Bearer " + sharedPrefManager.getAccessToken());

        call.enqueue(new Callback<DoctorEarningsResponse>() {
            @Override
            public void onResponse(@NotNull Call<DoctorEarningsResponse> call, @NotNull Response<DoctorEarningsResponse> response) {
                DoctorEarningsResponse doctorEarningsResponse = response.body();
                if (response.isSuccessful()) {
                    loadingDialog.progressDialog.dismiss();
                    String earnings = doctorEarningsResponse.getEarnings().toString();
                    String patients = doctorEarningsResponse.getTotalPatients().toString();
                    totalEarnings.setText(earnings);
                    totalPatients.setText(patients);
                }
            }

            @Override
            public void onFailure(@NotNull Call<DoctorEarningsResponse> call, @NotNull Throwable t) {
                Toast.makeText(EarningsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}