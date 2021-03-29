package com.uberdoktor.android.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.model.Slot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private Activity activity;
    List<Slot> arrayListGroup;
    SharedPrefManager sharedPrefManager;
    public static String appointmentDate;

    public GroupAdapter(Activity activity, List<Slot> arrayListGroup) {
        this.activity = activity;
        this.arrayListGroup = arrayListGroup;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_slot_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Slot slot = arrayListGroup.get(position);
        String str = slot.getScheduleDate();
        try {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appointmentDate = slot.getScheduleDate();
                    CustomAdapter adapter = new CustomAdapter(activity, slot.getAvailSlots());
                    holder.rvMember.setAdapter(adapter);
                }
            });
            @SuppressLint("SimpleDateFormat") SimpleDateFormat fmInput = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmInput.parse(str);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat fmtOutput = new SimpleDateFormat("EEE, dd MMM");
            assert date != null;
            str = fmtOutput.format(date);

            holder.dummyTV.setText(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {
        return arrayListGroup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dummyTV;
        RecyclerView rvMember;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dummyTV = itemView.findViewById(R.id.tv_appointment_date_app_time_activity);
            rvMember = itemView.findViewById(R.id.rv_member);

        }
    }
}
