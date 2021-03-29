package com.uberdoktor.android.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uberdoktor.android.R;
import com.uberdoktor.android.adapter.QueueDetailsAdapter;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.response.AddDoctorScheduleResponse;
import com.uberdoktor.android.response.AddReviewResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentFeedback extends AppCompatActivity {

    private EditText comments;
    String patientFeedback;
    String id;

    SharedPrefManager sharedPrefManager;

    //    rating layout
    private LinearLayout rateNowContainer;
    private static int x;
    private TextView ratedStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_feedback);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Appointment Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button addReviewBtn = findViewById(R.id.add_review_btn);
        comments = findViewById(R.id.et_comments);
        ratedStars = findViewById(R.id.average_rating);
        id = getIntent().getStringExtra("id");
        sharedPrefManager = new SharedPrefManager(this);

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAppointmentReview();
            }
        });

        rateNowContainer = findViewById(R.id.rate_now_container);

        for (x = 0; x < rateNowContainer.getChildCount(); x++) {
            final int starPos = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    setRating(starPos);
                }
            });
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setRating(int starPos) {
        for (x = 0; x < rateNowContainer.getChildCount(); x++) {
            ImageView starButton = (ImageView) rateNowContainer.getChildAt(x);
            starButton.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= starPos) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    ratedStars.setText(String.valueOf(x + 1));
                    starButton.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
                }
            }
        }
    }

    private void submitAppointmentReview() {
        patientFeedback = comments.getText().toString().trim();
        Toast.makeText(this, AppointmentFeedback.x, Toast.LENGTH_SHORT).show();
        Call<AddReviewResponse> call = RetrofitClient.getInstance().getMyInterface().submitReview("Bearer " + sharedPrefManager.getAccessToken(), VideoCall.checkupId, String.valueOf(AppointmentFeedback.x), patientFeedback);
        call.enqueue(new Callback<AddReviewResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddReviewResponse> call, @NotNull Response<AddReviewResponse> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Toast.makeText(AppointmentFeedback.this, "successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AppointmentFeedback.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddReviewResponse> call, @NotNull Throwable t) {
                Toast.makeText(AppointmentFeedback.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}