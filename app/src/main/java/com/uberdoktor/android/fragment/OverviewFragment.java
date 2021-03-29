package com.uberdoktor.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.uberdoktor.android.R;
import com.uberdoktor.android.activity.PaymentMethodActivity;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.activity.SpecialityBottomSheet;
import com.uberdoktor.android.response.DirectGeneralPhysicianResponse;
import com.uberdoktor.android.response.OverviewResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OverviewFragment extends Fragment {

    SharedPrefManager sharedPrefManager;

    CardView specialistPhysicianCardView, generalPhysicianCardView;
    LinearLayout linearLayout;

    public static String patientAppointmentId;

    //////// direct gp
    public static String doctorName;
    public static String fee;
    public static String walletBalance;
    public static String appointmentDate;
    public static String appointmentTime;
    //////

    public OverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        specialistPhysicianCardView = view.findViewById(R.id.speacialist_physcian);
        generalPhysicianCardView = view.findViewById(R.id.cardView_g_p_responsibilities);

        linearLayout = view.findViewById(R.id.bottom_sheet);

        sharedPrefManager = new SharedPrefManager(Objects.requireNonNull(getContext()));

        specialistPhysicianCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpecialityBottomSheet bottomSheet = new SpecialityBottomSheet();
                bottomSheet.show(getChildFragmentManager(), "test");
            }
        });

        generalPhysicianCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirectGeneralPhysician();
            }
        });

//        viewPatientOverview();

        return view;
    }

    private void getDirectGeneralPhysician() {
        Call<DirectGeneralPhysicianResponse> call = RetrofitClient.getInstance().getMyInterface().getDirectGeneralPhysician("Bearer " + sharedPrefManager.getAccessToken());
        call.enqueue(new Callback<DirectGeneralPhysicianResponse>() {
            @Override
            public void onResponse(@NotNull Call<DirectGeneralPhysicianResponse> call, @NotNull Response<DirectGeneralPhysicianResponse> response) {

                DirectGeneralPhysicianResponse directGeneralPhysicianResponse = response.body();

                assert directGeneralPhysicianResponse != null;
                if (response.isSuccessful()) {
                    patientAppointmentId = directGeneralPhysicianResponse.getPendingPaymentId().toString();
                    doctorName = directGeneralPhysicianResponse.getAppointmentDetails().listIterator().next().getDoctorName();
                    fee = directGeneralPhysicianResponse.getAppointmentDetails().listIterator().next().getFee();
                    walletBalance = directGeneralPhysicianResponse.getAppointmentDetails().listIterator().next().getWalletBalance();
                    appointmentDate = directGeneralPhysicianResponse.getAppointmentDetails().listIterator().next().getDate();
                    appointmentTime = directGeneralPhysicianResponse.getAppointmentDetails().listIterator().next().getSlot();

//                    Toast.makeText(getActivity(), patientAppointmentId, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), PaymentMethodActivity.class);
                    intent.putExtra("doctorName", doctorName);
                    intent.putExtra("appointmentDate", appointmentDate);
                    intent.putExtra("appointmentTime", appointmentTime);
                    intent.putExtra("walletBalance", walletBalance);
                    intent.putExtra("fee", fee);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NotNull Call<DirectGeneralPhysicianResponse> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void viewPatientOverview() {
        Call<OverviewResponse> call = RetrofitClient.getInstance().getMyInterface().patientOverview("Bearer " + sharedPrefManager.getAccessToken());
        call.enqueue(new Callback<OverviewResponse>() {
            @Override
            public void onResponse(@NotNull Call<OverviewResponse> call, @NotNull Response<OverviewResponse> response) {


                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "I am Working", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<OverviewResponse> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Consultations not successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}