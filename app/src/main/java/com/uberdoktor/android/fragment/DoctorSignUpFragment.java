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
import com.uberdoktor.android.activity.DoctorOTPVerificationActivity;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.response.RegisterResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DoctorSignUpFragment extends Fragment {


    private EditText editTextSignUpPhoneNo;
    private TextView loginTextBtn, areYouAPatient;
    private Button continueButton;
    public static String phoneNO;
    SharedPrefManager sharedPrefManager;

    private ProgressBar progressBar;

    private FrameLayout frameLayout;

    public DoctorSignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_sign_up, container, false);

        frameLayout = getActivity().findViewById(R.id.register_framelayout);
        editTextSignUpPhoneNo = view.findViewById(R.id.sign_up_phone_no);
        loginTextBtn = view.findViewById(R.id.login_text_btn);
        areYouAPatient = view.findViewById(R.id.are_you_a_patient_text_btn);
        continueButton = view.findViewById(R.id.signup_btn);

        progressBar = view.findViewById(R.id.progressBar);
        sharedPrefManager = new SharedPrefManager(getActivity());
        sharedPrefManager.saveAccessToken(new RegisterResponse().getToken());

        sharedPrefManager.getAccessToken();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPhoneNO = editTextSignUpPhoneNo.getText().toString().trim();
                if (userPhoneNO.isEmpty()) {
                    editTextSignUpPhoneNo.requestFocus();
                    editTextSignUpPhoneNo.setError("please enter your phone no");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                phoneNO = userPhoneNO;
                Call<RegisterResponse> call = RetrofitClient.getInstance().getMyInterface().doctorRegisterPhoneNumber(userPhoneNO);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<RegisterResponse> call, @NotNull Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(getContext(), DoctorOTPVerificationActivity.class);
                            intent.putExtra("phone_no", phoneNO);
                            sharedPrefManager.saveAccessToken(response.body().getToken());
                            System.out.println(response.body().getToken());
                            startActivity(intent);
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<RegisterResponse> call, @NotNull Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        loginTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFragment(new PatientSignInFragment());
            }
        });
        areYouAPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setFragment(new PatientSignInFragment());
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}