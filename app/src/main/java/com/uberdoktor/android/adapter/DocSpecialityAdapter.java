package com.uberdoktor.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uberdoktor.android.R;
import com.uberdoktor.android.model.Doc;
import com.uberdoktor.android.model.DoctorEducation;
import com.uberdoktor.android.model.DoctorSpeciality;

import java.util.List;

public class DocSpecialityAdapter extends RecyclerView.Adapter<DocSpecialityAdapter.ViewHolder> {

    Context context;
    List<DoctorSpeciality> doctorSpecialityList;

    public DocSpecialityAdapter(Context context, List<DoctorSpeciality> doctorSpecialityList) {
        this.context = context;
        this.doctorSpecialityList = doctorSpecialityList;
    }

    @NonNull
    @Override
    public DocSpecialityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.doctor_speciality_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocSpecialityAdapter.ViewHolder holder, int position) {
        DoctorSpeciality speciality = doctorSpecialityList.get(position);
        holder.docSpeciality.setText(speciality.getName());
    }

    @Override
    public int getItemCount() {
        return doctorSpecialityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView docSpeciality;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            docSpeciality = itemView.findViewById(R.id.edu_doctor_speciality);
        }
    }
}
