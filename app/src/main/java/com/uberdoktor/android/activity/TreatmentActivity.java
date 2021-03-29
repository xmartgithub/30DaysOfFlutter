package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.tabs.TabLayout;
import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.response.SessionRequestResponse;
import com.uberdoktor.android.response.SubmitTreatmentResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TreatmentActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    private TextView treatmentPatientName;
    private Button submitTreatmentBtn;
    private EditText editTextSymptoms, editTextDiagnosis, editTextTreatment;
    private String symptoms, diagnosis, treatment;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Treatment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPrefManager = new SharedPrefManager(this);

        treatmentPatientName = findViewById(R.id.tv_treatment_patient_name);
        submitTreatmentBtn = findViewById(R.id.submit_treatment_btn);
        editTextSymptoms = findViewById(R.id.editText_symptoms);
        editTextDiagnosis = findViewById(R.id.editText_diagnosis);
        editTextTreatment = findViewById(R.id.editText_treatment);

        treatmentPatientName.setText(VideoCall.patientName);
        id = VideoCall.treatmentId;

        submitTreatmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TreatmentActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                submitTreatment();
            }
        });

    }

    //
    private void submitTreatment() {
        symptoms = editTextSymptoms.getText().toString();
        diagnosis = editTextDiagnosis.getText().toString();
        treatment = editTextTreatment.getText().toString();

        if (symptoms.isEmpty()) {
            editTextSymptoms.requestFocus();
            editTextSymptoms.setError("Please enter the symptoms");
            return;
        }
        if (diagnosis.isEmpty()) {
            editTextDiagnosis.requestFocus();
            editTextDiagnosis.setError("Please enter the diagnosis");
            return;
        }
        if (treatment.isEmpty()) {
            editTextTreatment.requestFocus();
            editTextTreatment.setError("please enter the treatment");
            return;
        }
        String appointmentId = VideoCall.addTreatmentAppointmentId;

        Toast.makeText(this, "called submit treatment", Toast.LENGTH_SHORT).show();
        Call<SubmitTreatmentResponse> call = RetrofitClient.getInstance().getMyInterface().submitTreatment("Bearer " + sharedPrefManager.getAccessToken(), id, symptoms, diagnosis, treatment, appointmentId);
        call.enqueue(new Callback<SubmitTreatmentResponse>() {
            @Override
            public void onResponse(@NotNull Call<SubmitTreatmentResponse> call, @NotNull Response<SubmitTreatmentResponse> response) {
                SubmitTreatmentResponse submitTreatmentResponse = response.body();
                if (response.isSuccessful()) {
                    assert submitTreatmentResponse != null;
                    Toast.makeText(TreatmentActivity.this, submitTreatmentResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TreatmentActivity.this, DoctorMainActivity.class));
                }
            }

            @Override
            public void onFailure(@NotNull Call<SubmitTreatmentResponse> call, @NotNull Throwable t) {
                Toast.makeText(TreatmentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}