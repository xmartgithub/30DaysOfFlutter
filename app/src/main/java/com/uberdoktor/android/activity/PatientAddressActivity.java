package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.uberdoktor.android.R;
import com.uberdoktor.android.adapter.PatientConsultationsAdapter;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.model.CheckupDatum;
import com.uberdoktor.android.response.MedicineRequestResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientAddressActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    List<CheckupDatum> checkupDatumList;
    List<String> citiesList;

    LoadingDialog loadingDialog;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_address);

        Objects.requireNonNull(getSupportActionBar()).setTitle("My Address");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.cities_spinner);

        sharedPrefManager = new SharedPrefManager(this);
        checkupDatumList = new ArrayList<>();
        citiesList = new ArrayList<>();

        loadingDialog = new LoadingDialog(PatientAddressActivity.this);
        loadingDialog.startLoadingDialog();

        getCities();

    }

    private void getCities() {
        Call<MedicineRequestResponse> call = RetrofitClient.getInstance().getMyInterface().requestForMedicine("Bearer " + sharedPrefManager.getAccessToken(), Integer.parseInt(PatientConsultationsAdapter.checkupId));

        call.enqueue(new Callback<MedicineRequestResponse>() {
            @Override
            public void onResponse(@NotNull Call<MedicineRequestResponse> call, @NotNull Response<MedicineRequestResponse> response) {
                MedicineRequestResponse medicineRequestResponse = response.body();
                if (response.isSuccessful()) {
                    loadingDialog.progressDialog.dismiss();
                    for (int i = 0; i < Objects.requireNonNull(medicineRequestResponse).getCities().size(); i++) {
                        citiesList.add(medicineRequestResponse.getCities().get(i).getCity());
                    }
                    ArrayAdapter<String> cityArrayAdapter = new ArrayAdapter<>(PatientAddressActivity.this, R.layout.spinner_item_style, citiesList);
                    spinner.setAdapter(cityArrayAdapter);

                }
            }

            @Override
            public void onFailure(@NotNull Call<MedicineRequestResponse> call, @NotNull Throwable t) {
                Toast.makeText(PatientAddressActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}