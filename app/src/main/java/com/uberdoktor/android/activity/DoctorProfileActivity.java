package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uberdoktor.android.adapter.DoctorProfilesAdapter;
import com.uberdoktor.android.response.DoctorProfileResponse;
import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorProfileActivity extends AppCompatActivity {

    private TextView docName;
    private TextView docDegrees;
    TextView educationDoctorDegree;
    TextView textViewAppointmentWaitTime;
    TextView textViewSpecialization;
    TextView textViewAboutDoctor;
    TextView textViewReviewAboutDoctor;
    TextView textViewDoctorServices;
    public static String id;

    Button videoCallAppointmentTimeBookingButton;
    Button bookClinicAppointment;
    Button bookClinicAppointment1;
    Button bookClinicAppointment2;

    SharedPrefManager sharedPrefManager;

    LoadingDialog loadingDialog;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        DoctorProfileActivity.super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        String title = getIntent().getStringExtra("doctorFullName");
        String doctorSpecialities = getIntent().getStringExtra("doctorSpecialities");
        id = getIntent().getStringExtra("doctorId");

        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPrefManager = new SharedPrefManager(this);

        docName = findViewById(R.id.doctor_name);
        docDegrees = findViewById(R.id.doctor_degree);
        educationDoctorDegree = findViewById(R.id.education_doctor_degree);
        textViewAppointmentWaitTime = findViewById(R.id.tv_wait_time);
        textViewSpecialization = findViewById(R.id.tv_specialization);
        textViewAboutDoctor = findViewById(R.id.tv_about_doctor);
        textViewReviewAboutDoctor = findViewById(R.id.tv_review);
        textViewDoctorServices = findViewById(R.id.tv_doctor_services);

        loadingDialog = new LoadingDialog(DoctorProfileActivity.this);
        loadingDialog.startLoadingDialog();
        loadingDialog.progressDialog.show();

        videoCallAppointmentTimeBookingButton = findViewById(R.id.book_video_call_appointment_btn);
//        bookClinicAppointment = findViewById(R.id.book_clinic_appointment_btn);
        bookClinicAppointment1 = findViewById(R.id.book_appointment_btn);
        bookClinicAppointment2 = findViewById(R.id.book_appointment_btn_02);

        docName.setText(title);
        docDegrees.setText(doctorSpecialities);

        this.videoCallAppointmentTimeBookingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AppointmentTimeActivity.class));
            }
        });

//        bookClinicAppointment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getBaseContext(), AppointmentTimeActivity.class));
//            }
//        });

        bookClinicAppointment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AppointmentTimeActivity.class));
            }
        });

        bookClinicAppointment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AppointmentTimeActivity.class));
            }
        });

        viewDoctorProfile();
    }

    private void viewDoctorProfile() {
//        String id = String.valueOf(DoctorProfilesAdapter.doctorId);
        Call<DoctorProfileResponse> call = RetrofitClient.getInstance().getMyInterface().getDoctorProfile("Bearer " + sharedPrefManager.getAccessToken(), id);

        call.enqueue(new Callback<DoctorProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<DoctorProfileResponse> call, @NotNull Response<DoctorProfileResponse> response) {

                if (response.isSuccessful()) {

                    assert response.body() != null;
                    loadingDialog.progressDialog.dismiss();
                    String doctorFullName = response.body().getDocProfile().listIterator().next().listIterator().next().getFirstName() + " " + response.body().getDocProfile().listIterator().next().listIterator().next().getLastName();
                    String doctorDegree = response.body().getDocProfile().listIterator().next().listIterator().next().getSpecialities().listIterator().next();

                    docName.setText(doctorFullName);
                    docDegrees.setText(doctorDegree);

//                  educationDoctorDegree.setText(response.body().getDocProfile().listIterator().next().iterator().next().getEducation().listIterator().next().toString());//response.body().getDocProfile().listIterator().next().iterator().next().getEducation().listIterator().next().toString()
                    textViewReviewAboutDoctor.setText("");//response.body().getDocProfile().listIterator().next().iterator().next().getEducation().listIterator().next().toString()

//                    about doctor and services not done
//                    textViewAboutDoctor.setText("");
//                    textViewDoctorServices.setText("");

                    String time = "Under " + response.body().getDocProfile().listIterator().next().iterator().next().getWaitTime() + " min";
                    textViewAppointmentWaitTime.setText(time);
                    textViewSpecialization.setText(response.body().getDocProfile().listIterator().next().listIterator().next().getSpecialities().listIterator().next());

                    Intent intent = new Intent(DoctorProfileActivity.this, AppointmentTimeActivity.class);
                    intent.putExtra("doctorFullName", docName.getText().toString());

                }
            }

            @Override
            public void onFailure(@NotNull Call<DoctorProfileResponse> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}