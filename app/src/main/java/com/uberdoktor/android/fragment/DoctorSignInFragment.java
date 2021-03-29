package com.uberdoktor.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.uberdoktor.android.R;
import com.uberdoktor.android.activity.DoctorMainActivity;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.response.LoginResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorSignInFragment extends Fragment {

    private TextView dontHaveAnAccountButton;
    private TextView tvAreYouAPatient;

    private Button loginButton;

    private EditText phoneNo;
    private EditText signInPassword;
    private ProgressBar progressBar;
    public static String doctorType;

    private FrameLayout frameLayout;

    SharedPrefManager sharedPrefManager;

    public DoctorSignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_sign_in, container, false);

        dontHaveAnAccountButton = view.findViewById(R.id.signup_text_btn);
        tvAreYouAPatient = view.findViewById(R.id.tv_are_you_a_patient_doctor_login);

        frameLayout = getActivity().findViewById(R.id.register_framelayout);

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
                setFragment(new DoctorSignUpFragment());
            }
        });

        tvAreYouAPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new PatientSignInFragment());
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
        }
        Call<LoginResponse> call = RetrofitClient.getInstance().getMyInterface().doctorLogin(userPhoneNO, userPassword);
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
                        System.out.println(response.body().getAccessToken());
                        Intent intent = new Intent(getContext(), DoctorMainActivity.class);
                        doctorType = loginResponse.getUser().getType();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Toast.makeText(getContext(), "login successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Invalid phone number or password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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
