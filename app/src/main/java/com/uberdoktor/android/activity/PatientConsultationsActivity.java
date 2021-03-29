package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.uberdoktor.android.adapter.PatientConsultationsAdapter;
import com.uberdoktor.android.response.PatientConsultationsResponse;
import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientConsultationsActivity extends AppCompatActivity {

    //    private ViewPager consultationDetailsViewPager;
    SharedPrefManager sharedPrefManager;
    private TextView consultantName;
    RecyclerView consultationsRecyclerView;
    LoadingDialog loadingDialog;
//    public static int checkupId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultations);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Consultations");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPrefManager = new SharedPrefManager(this);
        consultationsRecyclerView = findViewById(R.id.patient_consultation_rv);
        consultationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadingDialog = new LoadingDialog(PatientConsultationsActivity.this);
        loadingDialog.startLoadingDialog();

        getConsultations();

    }


    private void getConsultations() {

        Call<PatientConsultationsResponse> call = RetrofitClient.getInstance().getMyInterface().patientConsultations("Bearer " + sharedPrefManager.getAccessToken());
        call.enqueue(new Callback<PatientConsultationsResponse>() {
            @Override
            public void onResponse(@NotNull Call<PatientConsultationsResponse> call, @NotNull Response<PatientConsultationsResponse> response) {

                assert response.body() != null;

                if (response.isSuccessful()) {
//                    consultantName.setText(response.body().getCheckupData().iterator().next().getDocName());
                    consultationsRecyclerView.setAdapter(new PatientConsultationsAdapter(PatientConsultationsActivity.this, response.body().getCheckupData()));
                    loadingDialog.progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull Call<PatientConsultationsResponse> call, @NotNull Throwable t) {
                Toast.makeText(PatientConsultationsActivity.this, "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }

}