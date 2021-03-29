package com.uberdoktor.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uberdoktor.android.R;
import com.uberdoktor.android.activity.AppointmentTimeActivity;
import com.uberdoktor.android.activity.PaymentMethodActivity;
import com.uberdoktor.android.activity.SpecialityActivity;
import com.uberdoktor.android.activity.VideoCall;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.response.DoctorScheduleResponse;
import com.uberdoktor.android.response.SelectSlotResponse;
import com.uberdoktor.android.response.VideoCallRequestResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.SlotsViewHolder> {

    List<String> slots;
    Context context;
    SharedPrefManager sharedPrefManager;

    public static int pendingPaymentId;
    public static String doctorName;
    public String appointmentFee;
    public static String appointmentDate;
    public String slotTime;
    public String walletBalance;

    public CustomAdapter(Context context, List<String> slots) {
        this.slots = slots;
        this.context = context;
    }

    @NonNull
    @Override
    public SlotsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_slots_list, parent, false);
        sharedPrefManager = new SharedPrefManager(parent.getContext());
        return new CustomAdapter.SlotsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotsViewHolder holder, int position) {
        holder.tvSlots.setText(slots.get(position));
        String slot = holder.tvSlots.getText().toString();
        holder.tvSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked");
                String id = String.valueOf(SpecialityActivity.doctorID);
                Call<SelectSlotResponse> call = RetrofitClient.getInstance().getMyInterface().selectSlot("Bearer " + sharedPrefManager.getAccessToken(), id, GroupAdapter.appointmentDate, slot);
                call.enqueue(new Callback<SelectSlotResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<SelectSlotResponse> call, @NotNull Response<SelectSlotResponse> response) {

                        assert response.body() != null;

                        if (response.isSuccessful()) {
                            System.out.println("success");
                            pendingPaymentId = response.body().getPendingPaymentId();
                            doctorName = response.body().getAppointmentDetails().listIterator().next().getDoctorName();
                            appointmentFee = response.body().getAppointmentDetails().listIterator().next().getFee();
                            appointmentDate = response.body().getAppointmentDetails().listIterator().next().getDate();
                            slotTime = response.body().getAppointmentDetails().listIterator().next().getSlot();
                            walletBalance = response.body().getAppointmentDetails().listIterator().next().getWalletBalance();
                            Intent intent = new Intent(v.getContext(), PaymentMethodActivity.class);
                            intent.putExtra("doctorName", doctorName);
                            intent.putExtra("appointmentDate", appointmentDate);
                            intent.putExtra("appointmentTime", slotTime);
                            intent.putExtra("walletBalance", walletBalance);
                            intent.putExtra("appointmentFee", appointmentFee);
                            v.getContext().startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<SelectSlotResponse> call, @NotNull Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void selectSlot() {

    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    public class SlotsViewHolder extends RecyclerView.ViewHolder {
        TextView tvSlots;


        public SlotsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSlots = itemView.findViewById(R.id.tv_slots);


        }
    }
}
