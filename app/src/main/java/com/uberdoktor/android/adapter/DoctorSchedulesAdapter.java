package com.uberdoktor.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uberdoktor.android.R;
import com.uberdoktor.android.model.Slot;

import java.util.List;

public class DoctorSchedulesAdapter extends RecyclerView.Adapter<DoctorSchedulesAdapter.ViewHolder> {

    Context context;
    List<List<Slot>> slotsList;

    public DoctorSchedulesAdapter(Context context, List<List<Slot>> slotsList) {
        this.context = context;
        this.slotsList = slotsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.doctor_schedule_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Slot> slots = slotsList.get(position);
        String date = slots.listIterator().next().getScheduleDate();
        String start = slots.listIterator().next().getScheduleStart();
        String end = slots.listIterator().next().getScheduleEnd();
        holder.tvScheduleDate.setText(date);
        holder.tvScheduleStartTime.setText(start);
        holder.tvScheduleEndTime.setText(end);

    }

    @Override
    public int getItemCount() {
        return slotsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvScheduleDate, tvScheduleStartTime, tvScheduleEndTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvScheduleDate = itemView.findViewById(R.id.tv_schedule_date);
            tvScheduleStartTime = itemView.findViewById(R.id.tv_schedule_start_time);
            tvScheduleEndTime = itemView.findViewById(R.id.tv_schedule_end_time);
        }
    }
}
