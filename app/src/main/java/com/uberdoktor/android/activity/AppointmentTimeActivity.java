package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.uberdoktor.android.adapter.DoctorProfilesAdapter;
import com.uberdoktor.android.response.DoctorScheduleResponse;
import com.uberdoktor.android.adapter.GroupAdapter;
import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.model.Slot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentTimeActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;

    Button appointmentTimeButton;
    TextView doctorFullName, textViewAppointmentFee;
    ConstraintLayout constraintLayout;

    public static List<List<String>> availableSlots;

    RecyclerView rvGroup;
    public List<Slot> arrayListGroup;
    LinearLayoutManager layoutManagerGroup;
    GroupAdapter groupAdapter;

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_appointment_time);

        getSupportActionBar().setTitle("Appointment time");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPrefManager = new SharedPrefManager(this);

        constraintLayout = findViewById(R.id.constraintLayout);
        appointmentTimeButton = findViewById(R.id.book_video_call_appointment_btn);

        doctorFullName = findViewById(R.id.doctor_full_name);
        textViewAppointmentFee = findViewById(R.id.appointment_fee);
        rvGroup = findViewById(R.id.rv_group);

        loadingDialog = new LoadingDialog(AppointmentTimeActivity.this);
        loadingDialog.startLoadingDialog();
        loadingDialog.progressDialog.show();

        arrayListGroup = new ArrayList<>();

        fetchAndShowAppointmentsTime();

    }

    private void fetchAndShowAppointmentsTime() {
        String id = String.valueOf(SpecialityActivity.doctorID);

        Call<DoctorScheduleResponse> call = RetrofitClient.getInstance().getMyInterface().getAppointmentTime("Bearer " + sharedPrefManager.getAccessToken(), id);
        call.enqueue(new Callback<DoctorScheduleResponse>() {
            @Override
            public void onResponse(@NotNull Call<DoctorScheduleResponse> call, @NotNull Response<DoctorScheduleResponse> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    arrayListGroup = response.body().getSlot();
                    layoutManagerGroup = new LinearLayoutManager(getApplicationContext());
                    doctorFullName.setText(response.body().getDocName());
                    textViewAppointmentFee.setText(response.body().getDocFee());
                    loadingDialog.progressDialog.dismiss();
                    groupAdapter = new GroupAdapter(AppointmentTimeActivity.this, arrayListGroup);
                    rvGroup.setLayoutManager(layoutManagerGroup);
                    rvGroup.setAdapter(groupAdapter);
                }

            }

            @Override
            public void onFailure(@NotNull Call<DoctorScheduleResponse> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}