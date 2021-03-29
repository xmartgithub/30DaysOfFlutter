package com.uberdoktor.android.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uberdoktor.android.R;
import com.uberdoktor.android.activity.PaymentMethodActivity;
import com.uberdoktor.android.activity.VideoCall;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.model.Booked;
import com.uberdoktor.android.model.PendingRequest;
import com.uberdoktor.android.response.PendingPaymentBookedResponse;
import com.uberdoktor.android.response.VideoCallRequestResponse;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingAppointmentsAdapter extends RecyclerView.Adapter<PendingAppointmentsAdapter.ViewHolder> {

    Context context;
    List<List<PendingRequest>> pendingRequestList;
    Activity activity;
    SharedPrefManager sharedPrefManager;
    public static String appointmentId;
    public static String paymentBookedDoctorName;
    public static String paymentBookedFee;
    public static String paymentBookedDate;
    public static String paymentBookedTimeSlot;
    public static String paymentBookedWalletBalance;
    public static String pendingPaymentId;

    public PendingAppointmentsAdapter(Context context, List<List<PendingRequest>> pendingRequestList) {
        this.context = context;
        this.pendingRequestList = pendingRequestList;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.pending_appointment_item_history_layout, parent, false);
        sharedPrefManager = new SharedPrefManager(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<PendingRequest> pendingRequests = pendingRequestList.get(position);

        if (pendingRequests.listIterator().next().getAppointmentDate() != null) {
            String str = pendingRequests.listIterator().next().getAppointmentDate();
            try {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat fmInput = new SimpleDateFormat("yyyy-MM-dd");
                Date date = fmInput.parse(str);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat fmtOutput = new SimpleDateFormat("dd MMM");
                assert date != null;
                str = fmtOutput.format(date);
                holder.appointmentDate.setText(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            holder.appointmentDate.setText(pendingRequests.listIterator().next().getAppointmentDate());
        }
        holder.appointmentTime.setText(pendingRequests.get(0).getSlot());
        holder.appointmentPatientName.setText(pendingRequests.get(0).getPatientName());
        holder.appointmentDrName.setText(pendingRequests.get(0).getDoctorName());
        holder.appointmentFee.setText(pendingRequests.get(0).getAppointmentFee());

        holder.gotoVerifyPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointmentId = pendingRequests.get(0).getAppointmentId().toString();
                Call<PendingPaymentBookedResponse> call = RetrofitClient.getInstance().getMyInterface().paymentBooked("Bearer " + sharedPrefManager.getAccessToken(), appointmentId);
                call.enqueue(new Callback<PendingPaymentBookedResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<PendingPaymentBookedResponse> call, @NotNull Response<PendingPaymentBookedResponse> response) {
                        PendingPaymentBookedResponse pendingPaymentBookedResponse = response.body();
                        assert pendingPaymentBookedResponse != null;
                        if (response.isSuccessful()) {
                            paymentBookedDoctorName = pendingPaymentBookedResponse.getAppointmentDetails().listIterator().next().getDoctorName();
                            paymentBookedFee = pendingPaymentBookedResponse.getAppointmentDetails().listIterator().next().getFee();
                            paymentBookedDate = pendingPaymentBookedResponse.getAppointmentDetails().listIterator().next().getDate();
                            paymentBookedTimeSlot = pendingPaymentBookedResponse.getAppointmentDetails().listIterator().next().getSlot();
                            paymentBookedWalletBalance = pendingPaymentBookedResponse.getAppointmentDetails().listIterator().next().getWalletBalance();
                            pendingPaymentId = pendingPaymentBookedResponse.getPendingPaymentId();

                            Intent intent = new Intent(context, PaymentMethodActivity.class);
                            intent.putExtra("doctorName", paymentBookedDoctorName);
                            intent.putExtra("appointmentDate", paymentBookedDate);
                            intent.putExtra("appointmentTime", paymentBookedTimeSlot);
                            intent.putExtra("walletBalance", paymentBookedWalletBalance);
                            intent.putExtra("appointmentFee", paymentBookedFee);
                            v.getContext().startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<PendingPaymentBookedResponse> call, @NotNull Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return pendingRequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView appointmentDate;
        TextView appointmentTime;
        TextView appointmentDrName;
        TextView appointmentPatientName;
        TextView appointmentFee;
        Button gotoVerifyPayment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            appointmentDate = itemView.findViewById(R.id.appointment_h_appointment_date);
            appointmentTime = itemView.findViewById(R.id.appointment_h_appointment_time);
            appointmentDrName = itemView.findViewById(R.id.appointment_dr_name);
            appointmentPatientName = itemView.findViewById(R.id.appointment_h_patient_name);
            appointmentFee = itemView.findViewById(R.id.appointment_history_app_fee);

            gotoVerifyPayment = itemView.findViewById(R.id.appointment_h_video_call_btn);
        }

    }
}
