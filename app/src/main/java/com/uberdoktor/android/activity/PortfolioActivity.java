package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.uberdoktor.android.response.PortfolioResponse;
import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortfolioActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;

    private TextView docName;
    private TextView docDegrees;
    private TextView textViewAppointmentWaitTime;
    private TextView textViewSpecialization;
    private TextView educationDoctorDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Portfolio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPrefManager = new SharedPrefManager(this);

        docName = findViewById(R.id.doctor_name);
        docDegrees = findViewById(R.id.doctor_degree);
        textViewAppointmentWaitTime = findViewById(R.id.tv_wait_time);
        textViewSpecialization = findViewById(R.id.tv_specialization);
        educationDoctorDegree = findViewById(R.id.education_doctor_degree);

        viewDoctorPortfolio();
    }

    private void viewDoctorPortfolio() {
        Call<PortfolioResponse> call = RetrofitClient.getInstance().getMyInterface().doctorPortfolio("Bearer " + sharedPrefManager.getAccessToken());

        call.enqueue(new Callback<PortfolioResponse>() {
            @Override
            public void onResponse(@NotNull Call<PortfolioResponse> call, @NotNull Response<PortfolioResponse> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String name = response.body().getDocProfile().get(0).get(0).getFirstName() + " " + response.body().getDocProfile().get(0).get(0).getLastName();
                    docName.setText(name);
                    docDegrees.setText(response.body().getDocProfile().listIterator().next().listIterator().next().getSpecialities().listIterator().next());
                    textViewSpecialization.setText(response.body().getDocProfile().listIterator().next().listIterator().next().getSpecialities().listIterator().next());
                    Toast.makeText(PortfolioActivity.this, "Doctor first name: " + name, Toast.LENGTH_SHORT).show();
                    educationDoctorDegree.setText(response.body().getDocProfile().listIterator().next().iterator().next().getEducation().listIterator().next().toString());//response.body().getDocProfile().listIterator().next().iterator().next().getEducation().listIterator().next().toString()

//                    about doctor and services not done
//                    textViewAboutDoctor.setText("");
//                    textViewDoctorServices.setText("");

                }
            }

            @Override
            public void onFailure(@NotNull Call<PortfolioResponse> call, @NotNull Throwable t) {
                Toast.makeText(PortfolioActivity.this, "Portfolio not successful", Toast.LENGTH_SHORT).show();
                Toast.makeText(PortfolioActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}