package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.response.AddDoctorScheduleResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleTimeActivity extends AppCompatActivity {

    DatePicker datePicker;
    SharedPrefManager sharedPrefManager;
    SharedPreferences.Editor editor;

    public static int lastClicked0 = 0;
    public static int lastClicked1 = 0;
    public static int lastClicked2 = 0;
    public static int lastClicked3 = 0;

    TextView textView;

    final List<String> startTimeList = new ArrayList<>();
    final List<String> endTimeList = new ArrayList<>();
    final List<String> amPMList = new ArrayList<>();

    String date;
    public static String startTime;
    public static String endTime;

    ///////
    SharedPreferences sharedPreferences;

    private Spinner startTimeSpinner, amPMSpinner, endTimeSpinner, amPMSpinner2;
    private Button saveScheduleTimeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_time);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Schedule Time");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sharedPrefManager = new SharedPrefManager(this);

        ////////////
        startTimeSpinner = findViewById(R.id.startScheduleTimeSpinner);
        endTimeSpinner = findViewById(R.id.endTimeSpinner);
        amPMSpinner = findViewById(R.id.ampmSpinner);
        amPMSpinner2 = findViewById(R.id.spinner4);
        saveScheduleTimeBtn = findViewById(R.id.save_schedule_time_Btn);

        textView = findViewById(R.id.spinner_item_tv);

        startTimeList.add("01:00");
        startTimeList.add("02:00");
        startTimeList.add("03:00");
        startTimeList.add("04:00");
        startTimeList.add("05:00");
        startTimeList.add("06:00");
        startTimeList.add("07:00");
        startTimeList.add("08:00");
        startTimeList.add("09:00");
        startTimeList.add("10:00");
        startTimeList.add("11:00");
        startTimeList.add("12:00");

        endTimeList.add("01:00");
        endTimeList.add("02:00");
        endTimeList.add("03:00");
        endTimeList.add("04:00");
        endTimeList.add("05:00");
        endTimeList.add("06:00");
        endTimeList.add("07:00");
        endTimeList.add("08:00");
        endTimeList.add("09:00");
        endTimeList.add("10:00");
        endTimeList.add("11:00");
        endTimeList.add("12:00");

        amPMList.add("AM");
        amPMList.add("PM");

        sharedPreferences = getSharedPreferences("genderSharedPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        lastClicked0 = sharedPreferences.getInt("lastClicked0", 0);
        lastClicked1 = sharedPreferences.getInt("lastClicked1", 0);
        lastClicked2 = sharedPreferences.getInt("lastClicked2", 0);
        lastClicked3 = sharedPreferences.getInt("lastClicked3", 0);

        /////// start time spinner
        ArrayAdapter<String> startTimeAdapter = new ArrayAdapter<>(ScheduleTimeActivity.this, R.layout.support_simple_spinner_dropdown_item, startTimeList);
        startTimeSpinner.setAdapter(startTimeAdapter);
        startTimeSpinner.setSelection(lastClicked0);

        startTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("lastClicked0", position);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /////// end time spinner
        ArrayAdapter<String> endTimeAdapter = new ArrayAdapter<>(ScheduleTimeActivity.this, R.layout.support_simple_spinner_dropdown_item, endTimeList);
        endTimeSpinner.setAdapter(endTimeAdapter);
        endTimeSpinner.setSelection(lastClicked1);

        endTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("lastClicked1", position);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> amPMAdapter = new ArrayAdapter<>(ScheduleTimeActivity.this, R.layout.support_simple_spinner_dropdown_item, amPMList);
        amPMSpinner.setAdapter(amPMAdapter);
        amPMSpinner.setSelection(lastClicked2);

        amPMSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("lastClicked2", position);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> amPMAdapter1 = new ArrayAdapter<>(ScheduleTimeActivity.this, R.layout.support_simple_spinner_dropdown_item, amPMList);
        amPMSpinner2.setAdapter(amPMAdapter1);
        amPMSpinner2.setSelection(lastClicked3);

        amPMSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("lastClicked3", position);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ////////////

        datePicker = findViewById(R.id.cal_date_picker);
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                date = year + "/" + monthOfYear + "/" + dayOfMonth;
            }
        });


        saveScheduleTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDoctorScheduleTime();
            }
        });

    }

    private void addDoctorScheduleTime() {

        startTime = startTimeSpinner.getSelectedItem().toString() + " " + amPMSpinner.getSelectedItem().toString();
        endTime = endTimeSpinner.getSelectedItem().toString() + " " + amPMSpinner2.getSelectedItem().toString();

        Toast.makeText(this, startTime, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, endTime, Toast.LENGTH_SHORT).show();

        Call<AddDoctorScheduleResponse> call = RetrofitClient.getInstance().getMyInterface().addSchedule("Bearer " + sharedPrefManager.getAccessToken(), startTime, endTime, date);

        call.enqueue(new Callback<AddDoctorScheduleResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddDoctorScheduleResponse> call, @NotNull Response<AddDoctorScheduleResponse> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Toast.makeText(ScheduleTimeActivity.this, response.body().getResults(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddDoctorScheduleResponse> call, @NotNull Throwable t) {
                Toast.makeText(ScheduleTimeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}