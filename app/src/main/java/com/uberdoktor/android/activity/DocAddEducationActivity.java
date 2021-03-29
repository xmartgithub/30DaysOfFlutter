package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.response.AddDoctorScheduleResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocAddEducationActivity extends AppCompatActivity {

    LinearLayout docDegreeLayoutList;
    private TextView tvDegreeName;
    private TextView tvDegreeYear;
    private TextView tvInstitutionName;

    private Button saveEducationBtn;

    private EditText editTextInstitutionName;
    private EditText editTextDegreeName;
    private EditText editTextDegreeYear;


    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_add_eduction);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Education");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        docDegreeLayoutList = findViewById(R.id.doctor_degree_layout_list);
        tvDegreeName = findViewById(R.id.textViewDegreeName);
        tvDegreeYear = findViewById(R.id.textViewDegreeYear);

        editTextInstitutionName = findViewById(R.id.editText_institution_name);
        editTextDegreeName = findViewById(R.id.editTextDegreeName);
        editTextDegreeYear = findViewById(R.id.editTextDegree_Year);

        saveEducationBtn = findViewById(R.id.update_degree_btn);

        sharedPrefManager = new SharedPrefManager(this);

        saveEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEducation();
            }
        });


    }

    private void addEducation() {
        String institutionName = editTextInstitutionName.getText().toString().trim();
        String degreeName = editTextDegreeName.getText().toString().trim();
        String degreeYear = editTextDegreeYear.getText().toString().trim();

        if (institutionName.isEmpty()) {
            editTextInstitutionName.requestFocus();
            editTextInstitutionName.setError("Institution name is required");
            return;
        }
        if (degreeName.isEmpty()) {
            editTextDegreeName.requestFocus();
            editTextDegreeName.setError("Degree name is required");
            return;
        }
        if (degreeYear.isEmpty()) {
            editTextDegreeYear.requestFocus();
            editTextDegreeYear.setError("Degree year is required");
            return;
        }

        Call<AddDoctorScheduleResponse> call = RetrofitClient.getInstance().getMyInterface().addEducation("Bearer " + sharedPrefManager.getAccessToken(), institutionName, degreeName, degreeYear);
        call.enqueue(new Callback<AddDoctorScheduleResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddDoctorScheduleResponse> call, @NotNull Response<AddDoctorScheduleResponse> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Toast.makeText(DocAddEducationActivity.this, response.body().getResults(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddDoctorScheduleResponse> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}