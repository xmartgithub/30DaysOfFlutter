package com.uberdoktor.android.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uberdoktor.android.R;
import com.uberdoktor.android.activity.AppointmentFeedback;
import com.uberdoktor.android.activity.MainActivity;
import com.uberdoktor.android.activity.VideoCall;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.response.LoginResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PatientSignInFragment extends Fragment {

    private TextView dontHaveAnAccountButton;
    private TextView tvAreYouADoctor;

    private Button loginButton;

    private EditText phoneNo;
    private EditText signInPassword;
    private ProgressBar progressBar;
    public static String patientType;

    private FrameLayout frameLayout;

    SharedPrefManager sharedPrefManager;

    public PatientSignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_sign_in, container, false);

        dontHaveAnAccountButton = view.findViewById(R.id.signup_text_btn);
        tvAreYouADoctor = view.findViewById(R.id.tv_are_you_a_doctor_patient_login);

        frameLayout = requireActivity().findViewById(R.id.register_framelayout);

        phoneNo = view.findViewById(R.id.sign_in_phone_no);
        signInPassword = view.findViewById(R.id.user_sign_in_pwd);
        progressBar = view.findViewById(R.id.signIn_progressBar);

        loginButton = view.findViewById(R.id.login_btn);

        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.saveAccessToken(new LoginResponse().getAccessToken());
        sharedPrefManager.getAccessToken();
        System.out.println(sharedPrefManager.getAccessToken());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAnAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new PatientSignUpFragment());
            }
        });

        tvAreYouADoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new DoctorSignInFragment());
            }
        });

        loginButton.setOnClickListener(view12 -> userLogin());

    }

    private void userLogin() {
        String userPhoneNO = phoneNo.getText().toString().trim();
        String userPassword = signInPassword.getText().toString().trim();
        if (userPhoneNO.isEmpty()) {
            phoneNo.requestFocus();
            phoneNo.setError("please enter your phone no");
            return;
        }
        if (userPassword.isEmpty()) {
            signInPassword.requestFocus();
            signInPassword.setError("please enter your password");
            return;
        }
        if (userPassword.length() < 8) {
            signInPassword.requestFocus();
            signInPassword.setError("Password must be at least 8 characters");
            return;
        }

        if (userPassword.length() >= 8) {
            progressBar.setVisibility(View.VISIBLE);
            loginButton.setEnabled(false);
        }
        Call<LoginResponse> call = RetrofitClient.getInstance().getMyInterface().login(userPhoneNO, userPassword);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                assert response.body() != null;
                if (response.body().getAccessToken() != null) {
                    if (response.isSuccessful()) {
                        assert loginResponse != null;
                        sharedPrefManager.saveUser(loginResponse.getUser());
                        sharedPrefManager.saveAccessToken(response.body().getAccessToken());
                        patientType = loginResponse.getUser().getType();
                        System.out.println(response.body().getAccessToken());
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Toast.makeText(getContext(), "login successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                } else {

                    Toast.makeText(getContext(), "Invalid phone number or password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                progressBar.setEnabled(false);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        onStart();
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if (sharedPrefManager.isLoggedIn()){
//            Intent intent = new Intent(getContext(), MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }
}
