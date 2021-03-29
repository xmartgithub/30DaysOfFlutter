package com.uberdoktor.android.adapter;

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
import com.uberdoktor.android.activity.VideoCall;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.model.QueDetail;
import com.uberdoktor.android.response.SessionRequestResponse;
import com.uberdoktor.android.response.VideoCallRequestResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueueDetailsAdapter extends RecyclerView.Adapter<QueueDetailsAdapter.ViewHolder> {

    private Context context;
    private List<List<QueDetail>> queDetailsList;
    public static String session, token;

    SharedPrefManager sharedPrefManager;

    public static String appointmentId;
    public static int pId;
    public static int dId;

    public QueueDetailsAdapter(Context context, List<List<QueDetail>> queDetailsList) {
        this.context = context;
        this.queDetailsList = queDetailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.que_patient_appointment_layout, parent, false);
        sharedPrefManager = new SharedPrefManager(parent.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<QueDetail> queDetails = queDetailsList.get(position);
        String patientFullName = queDetails.get(0).getPatientFname().trim() + " " + queDetails.get(0).getPatientLname().trim();
        holder.patientName.setText(patientFullName);
        holder.appointmentDate.setText(queDetails.get(0).getAppointmentDate());
        holder.appointmentTime.setText(queDetails.get(0).getSlot());


        holder.startSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling api video call
                Call<SessionRequestResponse> call = RetrofitClient.getInstance().getMyInterface().requestSession("Bearer " + sharedPrefManager.getAccessToken(), queDetails.get(0).getAppointmentId().toString());
                call.enqueue(new Callback<SessionRequestResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<SessionRequestResponse> call, @NotNull Response<SessionRequestResponse> response) {
                        SessionRequestResponse sessionRequestResponse = response.body();
                        if (response.isSuccessful()) {
//                            v.getContext().startActivity(new Intent(context, VideoCall.class));
                            holder.startSessionBtn.setVisibility(View.GONE);
                            holder.startVideoCallBtn.setVisibility(View.VISIBLE);
                            assert sessionRequestResponse != null;
                            assert response.body() != null;
                            pId = response.body().getAppointmentInfo().getPId();
                            dId = sessionRequestResponse.getAppointmentInfo().getDId();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<SessionRequestResponse> call, @NotNull Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.startVideoCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling api video call
                Call<VideoCallRequestResponse> call = RetrofitClient.getInstance().getMyInterface().requestVideoCall("Bearer " + sharedPrefManager.getAccessToken(), queDetails.get(0).getAppointmentId().toString());
                call.enqueue(new Callback<VideoCallRequestResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<VideoCallRequestResponse> call, @NotNull Response<VideoCallRequestResponse> response) {
                        VideoCallRequestResponse videoCallRequestResponse = response.body();
                        if (response.isSuccessful()) {
                            assert videoCallRequestResponse != null;

                            appointmentId = videoCallRequestResponse.getAppointmentInfoList().listIterator().next().getId().toString();
//                          token = videoCallRequestResponse.getAppointmentInfo().getToken();
                            session = videoCallRequestResponse.getAppointmentInfoList().listIterator().next().getSession();
                            token = videoCallRequestResponse.getAppointmentInfoList().listIterator().next().getToken();
//                            Toast.makeText(context, "session " + session, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(context, "token " + token, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, VideoCall.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("session", session);
                            intent.putExtra("token", token);
                            v.getContext().startActivity(new Intent(context, VideoCall.class));
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
        return queDetailsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView patientName, appointmentDate, appointmentTime;
        Button startVideoCallBtn;
        Button startSessionBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            patientName = itemView.findViewById(R.id.doc_home_patient_name);
            appointmentDate = itemView.findViewById(R.id.doc_home_appointment_date);
            appointmentTime = itemView.findViewById(R.id.doc_home_appointment_time);

            startVideoCallBtn = itemView.findViewById(R.id.start_video_call_btn);
            startSessionBtn = itemView.findViewById(R.id.doc_home_start_session_btn);
        }

    }
}
