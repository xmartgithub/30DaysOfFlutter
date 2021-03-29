package com.uberdoktor.android.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uberdoktor.android.activity.LoadingDialog;
import com.uberdoktor.android.activity.SpecialityActivity;
import com.uberdoktor.android.adapter.DoctorProfilesAdapter;
import com.uberdoktor.android.adapter.QueueDetailsAdapter;
import com.uberdoktor.android.response.DoctorOverviewResponse;
import com.uberdoktor.android.response.DoctorStatusResponse;
import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorHomeFragment extends Fragment {

    private SharedPrefManager sharedPrefManager;
    SharedPreferences onlineDocSharedPref;
    LoadingDialog loadingDialog;

    private TextView textViewTotalPatient;
    private TextView textViewTodayPatientChecked;
    private TextView textViewTotalAppointments;

    private RecyclerView queDetailsRecyclerview;
    private static final String ONLINE_SWITCH = "switch1";

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public Switch onlineSwitch;
    private Boolean switchOnOff;

    public DoctorHomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doctor_home_fragment, container, false);

        loadingDialog = new LoadingDialog(getActivity());

        textViewTotalPatient = view.findViewById(R.id.total_patient);
        textViewTodayPatientChecked = view.findViewById(R.id.today_patient_checked);
        textViewTotalAppointments = view.findViewById(R.id.doctor_home_tv_total_appointments);

        loadingDialog.startLoadingDialog();
        loadingDialog.progressDialog.show();

        sharedPrefManager = new SharedPrefManager(Objects.requireNonNull(getContext()));

        onlineSwitch = (Switch) view.findViewById(R.id.switch1);
        queDetailsRecyclerview = view.findViewById(R.id.que_details_rv);

        onlineDocSharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());


        viewDoctorOverview();

        getQueDetails();

        onlineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    Call<DoctorStatusResponse> call = RetrofitClient.getInstance().getMyInterface().doctorOnline("Bearer " + sharedPrefManager.getAccessToken());
                    call.enqueue(new Callback<DoctorStatusResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<DoctorStatusResponse> call, @NotNull Response<DoctorStatusResponse> response) {

                            if (response.body() != null) {
                                if (response.isSuccessful()) {
//                                    onlineSwitch.setTextColor(getResources().getColor(R.color.second_color));
                                    onlineSwitch.setText("Online");

                                    saveData();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<DoctorStatusResponse> call, @NotNull Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Call<DoctorStatusResponse> call = RetrofitClient.getInstance().getMyInterface().doctorOffline("Bearer " + sharedPrefManager.getAccessToken());
                    call.enqueue(new Callback<DoctorStatusResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<DoctorStatusResponse> call, @NotNull Response<DoctorStatusResponse> response) {

                            if (response.isSuccessful()) {
                                onlineSwitch.setText(getText(R.string.switch_doctor_offline_text));
                                onlineSwitch.setTextColor(getResources().getColor(R.color.colorPrimary));

                                saveData();
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<DoctorStatusResponse> call, @NotNull Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        loadData();
        updateView();
        return view;
    }

    public void saveData() {
        onlineDocSharedPref = Objects.requireNonNull(getContext()).getSharedPreferences("online_sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = onlineDocSharedPref.edit();
        editor.putBoolean(ONLINE_SWITCH, onlineSwitch.isChecked());
        editor.apply();
    }

    public void loadData() {
        onlineDocSharedPref = Objects.requireNonNull(getContext()).getSharedPreferences("online_sp", Context.MODE_PRIVATE);
        switchOnOff = onlineDocSharedPref.getBoolean(ONLINE_SWITCH, false);
    }

    public void updateView() {
        onlineSwitch.setChecked(switchOnOff);
    }


    private void getQueDetails() {
        Call<DoctorOverviewResponse> call = RetrofitClient.getInstance().getMyInterface().doctorOverview("Bearer " + sharedPrefManager.getAccessToken());
        call.enqueue(new Callback<DoctorOverviewResponse>() {
            @Override
            public void onResponse(@NotNull Call<DoctorOverviewResponse> call, @NotNull Response<DoctorOverviewResponse> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    queDetailsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                    loadingDialog.progressDialog.dismiss();
                    queDetailsRecyclerview.setAdapter(new
                            QueueDetailsAdapter(getContext(), response.body().getQueDetails()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<DoctorOverviewResponse> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewDoctorOverview() {
        Call<DoctorOverviewResponse> call = RetrofitClient.getInstance().getMyInterface().doctorOverview("Bearer " + sharedPrefManager.getAccessToken());
        call.enqueue(new Callback<DoctorOverviewResponse>() {
            @Override
            public void onResponse(@NotNull Call<DoctorOverviewResponse> call, @NotNull Response<DoctorOverviewResponse> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    textViewTotalPatient.setText(String.valueOf(response.body().getTotalPatients()));
                    textViewTodayPatientChecked.setText(String.valueOf(response.body().getPatientCheckedToday()));
                    textViewTotalAppointments.setText(String.valueOf(response.body().getTodaysQue()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<DoctorOverviewResponse> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

