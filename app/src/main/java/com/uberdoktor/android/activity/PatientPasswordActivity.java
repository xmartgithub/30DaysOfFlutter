package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.uberdoktor.android.R;
import com.uberdoktor.android.response.RegisterResponse;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientPasswordActivity extends AppCompatActivity {

    private TextInputLayout signUpPassword;
    private TextInputLayout confirmSignUpPassword;

    private ProgressBar progressBar;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_password);

        signUpPassword = findViewById(R.id.sign_up_password);
        confirmSignUpPassword = findViewById(R.id.signup_confirm_password);
        Button signUpButton = findViewById(R.id.signup_btn);
        progressBar = findViewById(R.id.setPasswordProgressbar);

        sharedPrefManager = new SharedPrefManager(this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = signUpPassword.getEditText().getText().toString().trim();
                String confirmPassword = confirmSignUpPassword.getEditText().getText().toString().trim();

                if (password.isEmpty()) {
                    signUpPassword.requestFocus();
                    signUpPassword.setError("please enter your password");

                }
                if (confirmPassword.isEmpty()) {
                    confirmSignUpPassword.requestFocus();
                    confirmSignUpPassword.setError("please enter your password");

                }
                if (password.length() < 8) {
                    signUpPassword.requestFocus();
                    signUpPassword.setError("Password must be at least 8 characters");
                }


                if (confirmPassword.length() < 8) {
                    confirmSignUpPassword.requestFocus();
                    confirmSignUpPassword.setError("Password must be at least 8 characters");
                }

                if (!confirmPassword.equals(password)) {
                    Toast.makeText(PatientPasswordActivity.this, "Password and confirm password should be same", Toast.LENGTH_SHORT).show();
                }


                Call<RegisterResponse> call = RetrofitClient.getInstance().getMyInterface().registration("Bearer " + sharedPrefManager.getAccessToken(), password, confirmPassword);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<RegisterResponse> call, @NotNull Response<RegisterResponse> response) {

                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.VISIBLE);
                            startActivity(new Intent(PatientPasswordActivity.this, PatientProfileDetailsActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<RegisterResponse> call, @NotNull Throwable t) {
                        Toast.makeText(PatientPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}