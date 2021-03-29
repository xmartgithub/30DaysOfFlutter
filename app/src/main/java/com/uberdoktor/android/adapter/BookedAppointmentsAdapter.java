package com.uberdoktor.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uberdoktor.android.R;
import com.uberdoktor.android.activity.AppointmentTimeActivity;
import com.uberdoktor.android.activity.DoctorProfileActivity;
import com.uberdoktor.android.activity.PortfolioActivity;
import com.uberdoktor.android.activity.SpecialityActivity;
import com.uberdoktor.android.activity.VideoCall;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.model.Booked;
import com.uberdoktor.android.model.PendingRequest;
import com.uberdoktor.android.response.DoctorProfileResponse;
import com.uberdoktor.android.response.PortfolioResponse;
import com.uberdoktor.android.response.SpecialistResponse;
import com.uberdoktor.android.response.StartVideoCallResponse;
import com.uberdoktor.android.response.VideoCallRequestResponse;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookedAppointmentsAdapter extends RecyclerView.Adapter<BookedAppointmentsAdapter.ViewHolder> {

    Context context;
    List<List<Booked>> bookedSlotsList;
    public static String bookedAppointmentId;
    Activity activity;
    SharedPrefManager sharedPrefManager;
    public static String patientSession;
    public static String patientToken;

    public BookedAppointmentsAdapter(Context context, List<List<Booked>> bookedSlotsList) {
        this.context = context;
        this.bookedSlotsList = bookedSlotsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.booked_appointment_item_history_layout, parent, false);
        sharedPrefManager = new SharedPrefManager(parent.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Booked> booked = bookedSlotsList.get(position);
        String str = booked.get(0).getAppointmentDate();

        try {
            SimpleDateFormat fmInput = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmInput.parse(str);
            SimpleDateFormat fmtOutput = new SimpleDateFormat("dd MMM");
            str = fmtOutput.format(date);
            holder.appointmentDate.setText(str);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.appointmentTime.setText(booked.get(0).getSlot());
        holder.appointmentPatientName.setText(booked.get(0).getPatientName());
        holder.appointmentDrName.setText(booked.get(0).getDoctorName());
        holder.appointmentFee.setText(booked.get(0).getAppointmentFee());

        holder.statVideoCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //calling api video call
                Call<VideoCallRequestResponse> call = RetrofitClient.getInstance().getMyInterface().callRequest("Bearer " + sharedPrefManager.getAccessToken(), booked.get(0).getAppointmentId().toString());
                call.enqueue(new Callback<VideoCallRequestResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<VideoCallRequestResponse> call, @NotNull Response<VideoCallRequestResponse> response) {
                        VideoCallRequestResponse videoCallRequestResponse = response.body();
                        assert videoCallRequestResponse != null;
                        if (response.isSuccessful()) {
                            if (videoCallRequestResponse.getAppointmentInfoList().listIterator().next().getSession().equals("null") || videoCallRequestResponse.getAppointmentInfoList().listIterator().next().getToken().equals("null")) {
                                Toast.makeText(context, "Doctor is not available right now", Toast.LENGTH_SHORT).show();
                            } else {
                                patientSession = videoCallRequestResponse.getAppointmentInfoList().listIterator().next().getSession();
                                patientToken = videoCallRequestResponse.getAppointmentInfoList().listIterator().next().getToken();
                                bookedAppointmentId = videoCallRequestResponse.getAppointmentInfoList().listIterator().next().getId().toString();

                                Intent intent = new Intent(context, VideoCall.class);
                                v.getContext().startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<VideoCallRequestResponse> call, @NotNull Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookedSlotsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView appointmentDate;
        TextView appointmentTime;
        TextView appointmentDrName;
        TextView appointmentPatientName;
        TextView appointmentFee;
        Button statVideoCallBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            appointmentDate = itemView.findViewById(R.id.appointment_h_appointment_date);
            appointmentTime = itemView.findViewById(R.id.appointment_h_appointment_time);
            appointmentDrName = itemView.findViewById(R.id.appointment_dr_name);
            appointmentPatientName = itemView.findViewById(R.id.appointment_h_patient_name);
            appointmentFee = itemView.findViewById(R.id.appointment_history_app_fee);

            statVideoCallBtn = itemView.findViewById(R.id.appointment_h_video_call_btn);

        }

    }
}
