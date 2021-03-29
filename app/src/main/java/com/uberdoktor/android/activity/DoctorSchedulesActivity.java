package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.uberdoktor.android.R;
import com.uberdoktor.android.adapter.DoctorProfilesAdapter;
import com.uberdoktor.android.adapter.DoctorSchedulesAdapter;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.response.ShowScheduleResponse;
import com.uberdoktor.android.response.SpecialistResponse;

import org.jetbrains.annotations.NotNull;

import java.security.interfaces.DSAKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorSchedulesActivity extends AppCompatActivity {

    RecyclerView doctorScheduleRV;

    SharedPrefManager sharedPrefManager;

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_schedules);
        getSupportActionBar().setTitle("My Schedules");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPrefManager = new SharedPrefManager(this);

        doctorScheduleRV = findViewById(R.id.show_schedules_rv);
        doctorScheduleRV.setLayoutManager(new LinearLayoutManager(this));

        loadingDialog = new LoadingDialog(DoctorSchedulesActivity.this);
        loadingDialog.startLoadingDialog();
        loadingDialog.progressDialog.show();

        getData();

    }

    private void getData() {

        Call<ShowScheduleResponse> call = RetrofitClient.getInstance().getMyInterface().getDocSchedules("Bearer " + sharedPrefManager.getAccessToken());

        call.enqueue(new Callback<ShowScheduleResponse>() {
            @Override
            public void onResponse(@NotNull Call<ShowScheduleResponse> call, @NotNull Response<ShowScheduleResponse> response) {
                ShowScheduleResponse showScheduleResponse = response.body();
                if (response.isSuccessful()) {
                    loadingDialog.progressDialog.dismiss();
                    doctorScheduleRV.setAdapter(new DoctorSchedulesAdapter(DoctorSchedulesActivity.this, showScheduleResponse.getSlots()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ShowScheduleResponse> call, @NotNull Throwable t) {
                Toast.makeText(DoctorSchedulesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}