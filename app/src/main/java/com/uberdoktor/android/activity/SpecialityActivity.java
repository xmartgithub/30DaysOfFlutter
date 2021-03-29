package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.response.SpecialistResponse;
import com.uberdoktor.android.adapter.DoctorProfilesAdapter;
import com.uberdoktor.android.apis.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialityActivity extends AppCompatActivity {

    public static int doctorID;
    String title;

    private RecyclerView doctorsProfileRecyclerView;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_speciality);

        title = getIntent().getStringExtra("specialityName");
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPrefManager = new SharedPrefManager(this);

        doctorsProfileRecyclerView = findViewById(R.id.doctors_profile_recycler_view);
        doctorsProfileRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();

    }

    private void getData() {

        Call<SpecialistResponse> call = RetrofitClient.getInstance().getMyInterface().getSpecialist("Bearer " + sharedPrefManager.getAccessToken(), "Lahore", title);

        call.enqueue(new Callback<SpecialistResponse>() {
            @Override
            public void onResponse(@NotNull Call<SpecialistResponse> call, @NotNull Response<SpecialistResponse> response) {
                assert response.body() != null;
                if (response.isSuccessful()) {
                    doctorID = Integer.parseInt(response.body().getDocs().listIterator().next().listIterator().next().getDoctorID());
                    doctorsProfileRecyclerView.setAdapter(new DoctorProfilesAdapter(SpecialityActivity.this, response.body().getDocs()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<SpecialistResponse> call, @NotNull Throwable t) {
                Toast.makeText(SpecialityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}