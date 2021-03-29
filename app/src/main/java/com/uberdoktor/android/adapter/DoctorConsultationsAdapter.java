package com.uberdoktor.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uberdoktor.android.R;
import com.uberdoktor.android.model.CheckupDatum;
import com.uberdoktor.android.model.DoctorCheckupDatum;

import java.util.List;

public class DoctorConsultationsAdapter extends RecyclerView.Adapter<DoctorConsultationsAdapter.ViewHolder> {

    Context context;
    List<DoctorCheckupDatum> checkupDatumList;

    public DoctorConsultationsAdapter(Context context, List<DoctorCheckupDatum> checkupDatumList) {
        this.context = context;
        this.checkupDatumList = checkupDatumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.doctor_consultation_card_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String doctorName = checkupDatumList.get(position).getDocName();
        String symptoms = checkupDatumList.get(position).getSymptoms();
        String diagnosis = checkupDatumList.get(position).getDiagnosis();
        String treatment = checkupDatumList.get(position).getTreatment();
        String ratings = checkupDatumList.get(position).getRating();

        holder.drName.setText(doctorName);
        holder.symptomValue.setText(symptoms);
        holder.diagnosisValue.setText(diagnosis);
        holder.treatmentValue.setText(treatment);
        holder.ratingTv.setText(ratings);


    }

    @Override
    public int getItemCount() {
        return checkupDatumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView drName;
        TextView symptomValue, diagnosisValue, treatmentValue;
        TextView ratingTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            drName = itemView.findViewById(R.id.consultation_doc_name);
            symptomValue = itemView.findViewById(R.id.symptom_value);
            diagnosisValue = itemView.findViewById(R.id.diagnosis_value);
            treatmentValue = itemView.findViewById(R.id.treatment_value);
            ratingTv = itemView.findViewById(R.id.average_rating);
        }
    }
}
