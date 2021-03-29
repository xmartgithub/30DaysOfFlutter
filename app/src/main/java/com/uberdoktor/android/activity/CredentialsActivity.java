package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uberdoktor.android.R;
import com.uberdoktor.android.adapter.DocEducationAdapter;
import com.uberdoktor.android.adapter.DocSpecialityAdapter;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.response.ShowCredentialsResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CredentialsActivity extends AppCompatActivity {

    //education recyclerview
    RecyclerView educationRecyclerView;
    TextView educationRVTitle;

    //loadin dialog
    LoadingDialog loadingDialog;

    //speciality recyclerview
    RecyclerView specialityRecyclerView;
    TextView specialityRVTitle;

    SharedPrefManager sharedPrefManager;

    //fab
    FloatingActionButton fmain, fone, ftwo;
    Float translationYaxix = 100f;
    Boolean menuOpen = false;
    OvershootInterpolator interpolator = new OvershootInterpolator();
    //fab end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Credentials");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPrefManager = new SharedPrefManager(this);

        //education
        educationRecyclerView = findViewById(R.id.credential_education_rv);
        educationRVTitle = findViewById(R.id.education_rv_title_tv);

        //speciality
        specialityRecyclerView = findViewById(R.id.credential_speciality_rv);
        specialityRVTitle = findViewById(R.id.speciality_rv_title_tv);

        //set layout manager to rvs
        educationRecyclerView.setLayoutManager(new LinearLayoutManager(CredentialsActivity.this));
        specialityRecyclerView.setLayoutManager(new LinearLayoutManager(CredentialsActivity.this));


        //fab
        fmain = findViewById(R.id.fab_main);
        fone = findViewById(R.id.fab_one);
        ftwo = findViewById(R.id.fab_two);


        getEducations();
        getSpecialities();

        loadingDialog = new LoadingDialog(CredentialsActivity.this);
        loadingDialog.startLoadingDialog();
        loadingDialog.progressDialog.show();

        //show menu of fab
        ShowMenu();
    }

    private void getEducations() {
        Call<ShowCredentialsResponse> call = RetrofitClient.getInstance().getMyInterface().showCredentials("Bearer " + sharedPrefManager.getAccessToken());

        call.enqueue(new Callback<ShowCredentialsResponse>() {
            @Override
            public void onResponse(@NotNull Call<ShowCredentialsResponse> call, @NotNull Response<ShowCredentialsResponse> response) {
                ShowCredentialsResponse showCredentialsResponse = response.body();
                if (response.isSuccessful()) {

                    loadingDialog.progressDialog.dismiss();
//                    Toast.makeText(CredentialsActivity.this, response.body().getDoctorEducation().listIterator().next().getSpeciality(), Toast.LENGTH_SHORT).show();
                    assert showCredentialsResponse != null;
                    educationRecyclerView.setAdapter(new DocEducationAdapter(CredentialsActivity.this, showCredentialsResponse.getDoctorEducation()));

                }
            }

            @Override
            public void onFailure(@NotNull Call<ShowCredentialsResponse> call, @NotNull Throwable t) {
                Toast.makeText(getApplicationContext(), call.request().url().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSpecialities() {
        Call<ShowCredentialsResponse> call = RetrofitClient.getInstance().getMyInterface().showCredentials("Bearer " + sharedPrefManager.getAccessToken());

        call.enqueue(new Callback<ShowCredentialsResponse>() {
            @Override
            public void onResponse(@NotNull Call<ShowCredentialsResponse> call, @NotNull Response<ShowCredentialsResponse> response) {
                ShowCredentialsResponse showCredentialsResponse = response.body();
                if (response.isSuccessful()) {

//                    Toast.makeText(CredentialsActivity.this, response.body().getDoctorEducation().listIterator().next().getSpeciality(), Toast.LENGTH_SHORT).show();
                    assert showCredentialsResponse != null;
                    specialityRecyclerView.setAdapter(new DocSpecialityAdapter(CredentialsActivity.this, showCredentialsResponse.getDoctorSpecialities()));
//                    loadingTextView.setVisibility(View.GONE);
//                    bookedAppointmentsRVTitle.setVisibility(View.VISIBLE);
//                    bookedAppointmentsRecyclerView.setVisibility(View.VISIBLE);
//                    bookedAppointmentsRecyclerView.setAdapter(new BookedAppointmentsAdapter(getActivity(), response.body().getBooked()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ShowCredentialsResponse> call, @NotNull Throwable t) {
                Toast.makeText(getApplicationContext(), call.request().url().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void ShowMenu() {
        fone.setAlpha(0f);
        ftwo.setAlpha(0f);


        fone.setTranslationY(translationYaxix);
        ftwo.setTranslationY(translationYaxix);

        fmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuOpen) {
                    CloseMenu();
                } else {
                    OpenMenu();
                }
            }
        });

        fone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CredentialsActivity.this, DocAddEducationActivity.class));
                CloseMenu();
            }
        });
        ftwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CredentialsActivity.this, DocAddSpecialityActivity.class));
                CloseMenu();

            }
        });

    }

    private void OpenMenu() {
        menuOpen = !menuOpen;
        fmain.setImageResource(R.drawable.ic_add);
        fone.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        ftwo.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
    }

    private void CloseMenu() {
        menuOpen = !menuOpen;
        fmain.setImageResource(R.drawable.ic_add);
        fone.animate().translationY(translationYaxix).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        ftwo.animate().translationY(translationYaxix).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
    }
}