package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uberdoktor.android.R;
import com.uberdoktor.android.response.RegisterResponse;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientOTPVerificationActivity extends AppCompatActivity {


    private EditText editTextOtp;
    private Button otpVerificationBtn;
    private EditText editTextSignUpPhone;
    private String userPhone;

    SharedPrefManager sharedPrefManager;

    private TextView verificationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);

        editTextOtp = findViewById(R.id.editTextOTP);
        otpVerificationBtn = findViewById(R.id.otp_verify_btn);
        editTextSignUpPhone = findViewById(R.id.sign_up_phone_no);
        verificationTextView = findViewById(R.id.textViewPatientBalance);

        userPhone = getIntent().getStringExtra("phone_no");
        verificationTextView.setText("Verication code has been send to +" + userPhone);

//        verificationTextView.setText("Verification Code has been sent to +"+editTextSignUpPhone);

        otpVerificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                String userPhoneNO = extras.getString("phone_no");
                String shortcode = editTextOtp.getText().toString().trim();
                if (shortcode.isEmpty()) {
                    editTextOtp.requestFocus();
                    editTextOtp.setError("please enter verification code first");
                    return;
                }
                Call<RegisterResponse> call = RetrofitClient.getInstance().getMyInterface().verifyPhoneNumber(userPhoneNO, shortcode);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<RegisterResponse> call, @NotNull Response<RegisterResponse> response) {
                        assert response.body() != null;

                        if (response.isSuccessful()) {
                            startActivity(new Intent(PatientOTPVerificationActivity.this, PatientPasswordActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<RegisterResponse> call, @NotNull Throwable t) {
                        Toast.makeText(PatientOTPVerificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }


}