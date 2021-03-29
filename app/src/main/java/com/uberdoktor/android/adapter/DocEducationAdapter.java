package com.uberdoktor.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.model.DoctorEducation;

import java.util.ArrayList;
import java.util.List;

public class DocEducationAdapter extends RecyclerView.Adapter<DocEducationAdapter.ViewHolder> {

    Context context;
    List<DoctorEducation> doctorEducationList;

    public DocEducationAdapter(Context context, List<DoctorEducation> doctorEducationList) {
        this.context = context;
        this.doctorEducationList = doctorEducationList;
    }

    @NonNull
    @Override
    public DocEducationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.doctor_education_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocEducationAdapter.ViewHolder holder, int position) {
        DoctorEducation education = doctorEducationList.get(position);
        holder.tvDocDegree.setText(education.getSpeciality());
        holder.tvInstitutionName.setText(education.getName());
        holder.tvDegreeYear.setText(education.getYear());

    }

    @Override
    public int getItemCount() {
        return doctorEducationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDocDegree, tvInstitutionName, tvDegreeYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDocDegree = itemView.findViewById(R.id.edu_doctor_degree);
            tvInstitutionName = itemView.findViewById(R.id.edu_institution_name);
            tvDegreeYear = itemView.findViewById(R.id.edu_degree_year);
        }
    }
}
