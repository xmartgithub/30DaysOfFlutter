package com.uberdoktor.android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uberdoktor.android.R;

public class PaymentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);


//        BottomNavigationView bottomNavigationView = findViewById(R.id.my_bottom_nav);
//
//        bottomNavigationView.setSelectedItemId(R.id.paymentActivity);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.overviewActivity:
//                        startActivity(new Intent(getApplicationContext(), OverviewActivity.class));
//                        overridePendingTransition(0,0);
//                        break;
//                    case R.id.appointmentActivity:
//                        startActivity(new Intent(getApplicationContext(), AppointmentsActivity.class));
//                        overridePendingTransition(0,0);
//                        break;
//                    case R.id.paymentActivity:
//
//                        break;
//                }
//                return true;
//            }
//        });
    }
}