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
import com.uberdoktor.android.activity.AppointmentTimeActivity;
import com.uberdoktor.android.activity.DoctorProfileActivity;
import com.uberdoktor.android.activity.PatientAddressActivity;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.model.CheckupDatum;
import com.uberdoktor.android.response.DoctorProfileResponse;
import com.uberdoktor.android.response.MedicineRequestResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientConsultationsAdapter extends RecyclerView.Adapter<PatientConsultationsAdapter.ViewHolder> {

    Context context;
    List<CheckupDatum> checkupDatumList;
    SharedPrefManager sharedPrefManager;
    public static String checkupId;

    public PatientConsultationsAdapter(Context context, List<CheckupDatum> checkupDatumList) {
        this.context = context;
        this.checkupDatumList = checkupDatumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.consultation_card_layout, parent, false);
        sharedPrefManager = new SharedPrefManager(parent.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckupDatum checkupDatum = checkupDatumList.get(position);
        String doctorName = checkupDatum.getDocName();
        String symptoms = checkupDatum.getSymptoms();
        String diagnosis = checkupDatum.getDiagnosis();
        String treatment = checkupDatum.getTreatment();
        String ratings = checkupDatum.getRating();

        holder.drName.setText(doctorName);
        holder.symptomValue.setText(symptoms);
        holder.diagnosisValue.setText(diagnosis);
        holder.treatmentValue.setText(treatment);
        holder.ratingTv.setText(ratings);

        holder.orderMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkupId = checkupDatum.getId().toString();
                v.getContext().startActivity(new Intent(context, PatientAddressActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return checkupDatumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView drName;
        TextView symptomValue, diagnosisValue, treatmentValue;
        TextView ratingTv;
        Button orderMedicineBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            drName = itemView.findViewById(R.id.consultation_doc_name);
            symptomValue = itemView.findViewById(R.id.symptom_value);
            diagnosisValue = itemView.findViewById(R.id.diagnosis_value);
            treatmentValue = itemView.findViewById(R.id.treatment_value);
            ratingTv = itemView.findViewById(R.id.average_rating);
            orderMedicineBtn = itemView.findViewById(R.id.patient_consultation_order_med_btn);
        }
    }
}
