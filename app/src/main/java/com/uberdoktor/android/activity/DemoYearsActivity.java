package com.uberdoktor.android.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.uberdoktor.android.R;

import com.uberdoktor.android.adapter.DemoYearsPagerAdapter;
import com.uberdoktor.android.apis.SharedPrefManager;


import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemoYearsActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyler_tablyout);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Appointments Time");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int startYear = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int endYear = 14;
        int initialYear = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        DateFormat dateFormat1 = new SimpleDateFormat("EE, dd MMM", Locale.getDefault());
        List<String> items = new ArrayList<>();
        for (int i = startYear; i <= endYear; i++) {
            Date date = addDays(new Date(), i - 3);
            String yesterday = dateFormat1.format(date.getTime());
            DateTime time = new DateTime();
            items.add(String.valueOf(yesterday));
        }
        sharedPrefManager = new SharedPrefManager(this);

        DemoYearsPagerAdapter adapter = new DemoYearsPagerAdapter();
        adapter.addAll(items);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(startYear - initialYear);

        RecyclerTabLayout recyclerTabLayout = findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithViewPager(viewPager);

//        fetchAndShowAppointmentsTime();
    }

    public static Date addDays(Date d, int days) {
        d.setTime(d.getTime() + days * 1000L * 60L * 60L * 24L);
        return d;
    }

//    private void fetchAndShowAppointmentsTime() {
//        String id = String.valueOf(SpecialityActivity.doctorID);
//
//        Call<DoctorScheduleResponse> call = RetrofitClient.getInstance().getMyInterface().getAppointmentTime("Bearer " + sharedPrefManager.getAccessToken(), id);
//        call.enqueue(new Callback<DoctorScheduleResponse>() {
//            @Override
//            public void onResponse(@NotNull Call<DoctorScheduleResponse> call, @NotNull Response<DoctorScheduleResponse> response) {
//
//                if (response.isSuccessful()) {
//                    for (List<Slot> item : response.body().getSlot()) {
//                        for (Slot slot: item) {
//                            System.out.print("Slot "+ slot.getAvailSlots());
//                        }
//                        System.out.println(" ");
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<DoctorScheduleResponse> call, @NotNull Throwable t) {
//                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}