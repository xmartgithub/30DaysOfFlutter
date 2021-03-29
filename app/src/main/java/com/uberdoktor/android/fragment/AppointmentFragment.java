package com.uberdoktor.android.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.uberdoktor.android.adapter.OngoingAppointmentsAdapter;
import com.uberdoktor.android.adapter.PendingAppointmentsAdapter;
import com.uberdoktor.android.response.AppointmentsResponse;
import com.uberdoktor.android.R;

import com.uberdoktor.android.adapter.BookedAppointmentsAdapter;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.model.Booked;
import com.uberdoktor.android.model.PendingRequest;


import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppointmentFragment extends Fragment {

    SharedPrefManager sharedPrefManager;

    // ongoing rv
    RecyclerView onGoingAppointmentsRecyclerView;
    TextView onGoingAppointmentsRVTitle;
    //booked rv
    RecyclerView bookedAppointmentsRecyclerView;
    TextView bookedAppointmentsRVTitle;
    //    pending rv
    RecyclerView pendingAppointmentsRecyclerView;
    TextView pendingAppointmentsRVTitle;

    //   loading Text View
    TextView loadingTextView;


    public AppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        sharedPrefManager = new SharedPrefManager(Objects.requireNonNull(getActivity()));

        // booked rv
        onGoingAppointmentsRecyclerView = view.findViewById(R.id.ongoing_appointments_rv);
        onGoingAppointmentsRVTitle = view.findViewById(R.id.ongoing_appointments_rv_title);
        // booked rv
        bookedAppointmentsRecyclerView = view.findViewById(R.id.booked_appointments_rv);
        bookedAppointmentsRVTitle = view.findViewById(R.id.booked_appointments_rv_title);
//      pending rv
        pendingAppointmentsRecyclerView = view.findViewById(R.id.pending_appointments_rv);
        pendingAppointmentsRVTitle = view.findViewById(R.id.pending_appointments_rv_title);

        //set layout manager to rvs
        onGoingAppointmentsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        bookedAppointmentsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        pendingAppointmentsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

//        loading TextView
        loadingTextView = view.findViewById(R.id.loading_tv);

        getBookedAppointmentsSlots();
        getPendingAppointmentsSlots();
        getOngoingAppointmentsSlots();

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getBookedSlots();

    }

    private void getOngoingAppointmentsSlots() {
        Call<AppointmentsResponse> call = RetrofitClient.getInstance().getMyInterface().getAppointmentDetails("Bearer " + sharedPrefManager.getAccessToken());

        call.enqueue(new Callback<AppointmentsResponse>() {
            @Override
            public void onResponse(@NotNull Call<AppointmentsResponse> call, @NotNull Response<AppointmentsResponse> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    loadingTextView.setVisibility(View.GONE);
                    onGoingAppointmentsRVTitle.setVisibility(View.VISIBLE);
                    onGoingAppointmentsRecyclerView.setVisibility(View.VISIBLE);
                    onGoingAppointmentsRecyclerView.setAdapter(new OngoingAppointmentsAdapter(getActivity(), response.body().getOngoing()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<AppointmentsResponse> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), call.request().url().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBookedAppointmentsSlots() {

        Call<AppointmentsResponse> call = RetrofitClient.getInstance().getMyInterface().getAppointmentDetails("Bearer " + sharedPrefManager.getAccessToken());

        call.enqueue(new Callback<AppointmentsResponse>() {
            @Override
            public void onResponse(@NotNull Call<AppointmentsResponse> call, @NotNull Response<AppointmentsResponse> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    loadingTextView.setVisibility(View.GONE);
                    bookedAppointmentsRVTitle.setVisibility(View.VISIBLE);
                    bookedAppointmentsRecyclerView.setVisibility(View.VISIBLE);
                    bookedAppointmentsRecyclerView.setAdapter(new BookedAppointmentsAdapter(getActivity(), response.body().getBooked()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<AppointmentsResponse> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), call.request().url().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getPendingAppointmentsSlots() {

        Call<AppointmentsResponse> call = RetrofitClient.getInstance().getMyInterface().getAppointmentDetails("Bearer " + sharedPrefManager.getAccessToken());

        call.enqueue(new Callback<AppointmentsResponse>() {
            @Override
            public void onResponse(@NotNull Call<AppointmentsResponse> call, @NotNull Response<AppointmentsResponse> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    loadingTextView.setVisibility(View.GONE);
                    pendingAppointmentsRVTitle.setVisibility(View.VISIBLE);
                    pendingAppointmentsRecyclerView.setVisibility(View.VISIBLE);
                    pendingAppointmentsRecyclerView.setAdapter(new PendingAppointmentsAdapter(getActivity(), response.body().getPendingRequests()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<AppointmentsResponse> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), call.request().url().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}