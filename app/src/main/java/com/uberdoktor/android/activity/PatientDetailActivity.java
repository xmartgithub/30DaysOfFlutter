package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uberdoktor.android.R;

public class PatientDetailActivity extends AppCompatActivity {

    private Button confirmBookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_detail_layout);

        getSupportActionBar().setTitle("Patient details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        confirmBookingButton = findViewById(R.id.confirm_booking_btn);

        confirmBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), PaymentMethodActivity.class));
            }
        });
    }
}