package com.uberdoktor.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uberdoktor.android.R;
import com.uberdoktor.android.activity.AppointmentTimeActivity;
import com.uberdoktor.android.activity.DoctorProfileActivity;
import com.uberdoktor.android.model.Doc;

import java.util.List;

public class DoctorProfilesAdapter extends RecyclerView.Adapter<DoctorProfilesAdapter.DoctorProfileViewHolder> {

    private Context context;
    private List<List<Doc>> specialistDocsList;
    //    public static String doctorId;


    public DoctorProfilesAdapter(Context context, List<List<Doc>> specialistDocsList) {
        this.context = context;
        this.specialistDocsList = specialistDocsList;
    }

    @NonNull
    @Override
    public DoctorProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.doctor_profile_item_layout, parent, false);

        return new DoctorProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorProfileViewHolder holder, int position) {

        List<Doc> docs = specialistDocsList.get(position);
        String doctorFullName = docs.get(0).getFirstName().trim() + " " + docs.get(0).getLastName().trim();
        String waitingTime = "Under " + " " + docs.get(0).getWaitTime() + " min";
        String doctorId = docs.get(0).getDoctorID();

        holder.doctorName.setText(doctorFullName);
        holder.doctorDegree.setText(docs.get(0).getSpecialities().get(0));
        holder.waitTime.setText(waitingTime);
        holder.doctorsId.setText(doctorId);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DoctorProfileActivity.class);
                intent.putExtra("doctorFullName", holder.doctorName.getText().toString());
                intent.putExtra("doctorSpecialities", holder.doctorDegree.getText().toString());
                intent.putExtra("doctorId", holder.doctorsId.getText().toString());
                view.getContext().startActivity(intent);
            }
        });

        holder.appointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), AppointmentTimeActivity.class));
            }
        });

        holder.viewProfilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DoctorProfileActivity.class);
                intent.putExtra("doctorFullName", holder.doctorName.getText().toString());
                intent.putExtra("doctorSpecialities", holder.doctorDegree.getText().toString());
                intent.putExtra("doctorId", holder.doctorsId.getText().toString());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return specialistDocsList.size();
    }

    public static class DoctorProfileViewHolder extends RecyclerView.ViewHolder {

        ImageView doctorCardImg;
        TextView doctorName;
        TextView doctorDegree;
        TextView waitTime;
        TextView doctorsId;
        View mView;

        private final Button appointmentButton;
        private final Button viewProfilesButton;

        public DoctorProfileViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorCardImg = itemView.findViewById(R.id.doctor_card_image);
            doctorName = itemView.findViewById(R.id.doctor_name);
            doctorDegree = itemView.findViewById(R.id.doctor_degree);
            waitTime = itemView.findViewById(R.id.wait_time);
            appointmentButton = itemView.findViewById(R.id.appointment_booking_btn);
            viewProfilesButton = itemView.findViewById(R.id.doctor_profile_view_button);
            doctorsId = itemView.findViewById(R.id.doctorID);
            mView = itemView;
        }
    }
}
