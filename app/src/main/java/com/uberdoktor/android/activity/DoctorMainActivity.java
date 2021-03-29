package com.uberdoktor.android.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.uberdoktor.android.R;
import com.uberdoktor.android.adapter.DoctorSchedulesAdapter;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.fragment.DoctorHomeFragment;
import com.uberdoktor.android.navigation.BaseItem;
import com.uberdoktor.android.navigation.CustomDataProvider;
import com.uberdoktor.android.navigation.CustomDataProvider2;
import com.uberdoktor.android.response.DocProfileResponse;
import com.uberdoktor.android.response.DoctorEarningsResponse;
import com.uberdoktor.android.response.LogoutResponse;
import com.uberdoktor.android.response.ProfileResponse;
import com.uberdoktor.android.response.ShowScheduleResponse;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListAdapter;
import pl.openrnd.multilevellistview.MultiLevelListView;
import pl.openrnd.multilevellistview.OnItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle actionBarDrawerToggle;
    SharedPrefManager sharedPrefManager;
    private long backPressedTime;

    private final OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        private void showItemDescription(Object object, ItemInfo itemInfo) {

            if (((BaseItem) object).getName().contains("Doctor Home")) {
                displaySelectedScreen("DOCTOR HOME");
            }
            if (((BaseItem) object).getName().contains("Schedule Time")) {
                displaySelectedScreen("SCHEDULE TIME");
            }
            if (((BaseItem) object).getName().contains("Schedules")) {
                displaySelectedScreen("SCHEDULES");
            }
            if (((BaseItem) object).getName().contains("Consultations")) {
                displaySelectedScreen("CONSULTATIONS");
            }
            if (((BaseItem) object).getName().contains("Credentials")) {
                displaySelectedScreen("CREDENTIALS");
            }
            if (((BaseItem) object).getName().contains("Portfolio")) {
                displaySelectedScreen("PORTFOLIO");
            }
            if (((BaseItem) object).getName().contains("Earnings")) {
                displaySelectedScreen("EARNINGS");
            }
//            if (((BaseItem) object).getName().contains("Pharmacy")) {
//                displaySelectedScreen("PHARMACY");
//            }
//            if (((BaseItem) object).getName().contains("Lab")) {
//                displaySelectedScreen("LAB");
//            }
            if (((BaseItem) object).getName().contains("Reviews")) {
                displaySelectedScreen("REVIEWS");
            }
            if (((BaseItem) object).getName().contains("Logout")) {
                displaySelectedScreen("LOGOUT");
            }

        }

        @Override
        public void onItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
            showItemDescription(item, itemInfo);
        }

        @Override
        public void onGroupItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
            showItemDescription(item, itemInfo);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        Button completeUserDoctorProfile = findViewById(R.id.doc_complete_profile_btn);
        sharedPrefManager = new SharedPrefManager(this);


        completeUserDoctorProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), DoctorProfileDetailsActivity.class));
            }
        });



        TextView doctorPhoneNo = findViewById(R.id.doc_nav_header_phone_no);


        confMenu();
        DrawerLayout drawer = findViewById(R.id.doc_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        displaySelectedScreen("DOCTOR HOME");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.first_color));
        actionBarDrawerToggle.syncState();

        ///// set doctor phone no on nav header

        Call<DocProfileResponse> callPhoneNo = RetrofitClient.getInstance().getMyInterface().viewDoctorProfile("Bearer " + sharedPrefManager.getAccessToken());
        callPhoneNo.enqueue(new Callback<DocProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<DocProfileResponse> call, @NotNull Response<DocProfileResponse> response) {

                DocProfileResponse docProfileResponse = response.body();
                if (response.isSuccessful()) {
                    assert docProfileResponse != null;
                    doctorPhoneNo.setText(docProfileResponse.getOwnData().getPhoneNo());
                }
            }

            @Override
            public void onFailure(@NotNull Call<DocProfileResponse> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void confMenu() {
        MultiLevelListView multiLevelListView = findViewById(R.id.multi_nav2);
        // custom ListAdapter
        ListAdapter listAdapter = new ListAdapter();
        multiLevelListView.setAdapter(listAdapter);
        multiLevelListView.setOnItemClickListener(mOnItemClickListener);
        listAdapter.setDataItems(CustomDataProvider2.getInitialItems());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.doc_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            } else {
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            backPressedTime = System.currentTimeMillis();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(String itemName) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemName) {
            case "DOCTOR HOME":
                fragment = new DoctorHomeFragment();
                break;
            case "SCHEDULE TIME":
                startActivity(new Intent(getApplicationContext(), ScheduleTimeActivity.class));
                break;
            case "SCHEDULES":
                startActivity(new Intent(getApplicationContext(), DoctorSchedulesActivity.class));
                break;
            case "CONSULTATIONS":
                startActivity(new Intent(getApplicationContext(), DoctorConsultationActivity.class));
                break;
            case "CREDENTIALS":
                startActivity(new Intent(getApplicationContext(), CredentialsActivity.class));
                break;
            case "PORTFOLIO":
                startActivity(new Intent(getApplicationContext(), PortfolioActivity.class));
                break;
            case "EARNINGS":
                startActivity(new Intent(getApplicationContext(), EarningsActivity.class));
                break;
//            case "PHARMACY":
//                fragment = new PharmacyFragment();
//                break;
//            case "LAB":
//                fragment = new LabFragment();
//                break;
            case "REVIEWS":
                startActivity(new Intent(getApplicationContext(), ReviewsActivity.class));
                break;
            case "LOGOUT":
                Call<LogoutResponse> call = RetrofitClient.getInstance().getMyInterface().doctorLogout("Bearer " + sharedPrefManager.getAccessToken());

                call.enqueue(new Callback<LogoutResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<LogoutResponse> call, @NotNull Response<LogoutResponse> response) {

                        if (response.isSuccessful()) {
                            Intent logoutIntent = new Intent(DoctorMainActivity.this, RegisterActivity.class);
                            startActivity(logoutIntent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<LogoutResponse> call, @NotNull Throwable t) {
                        Toast.makeText(DoctorMainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment);
            ft.commit();
            DrawerLayout drawer = findViewById(R.id.doc_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method display selected screen and passing the id of selected menu
        displaySelectedScreen(String.valueOf(item.getItemId()));
        //make this method blank
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class ListAdapter extends MultiLevelListAdapter {

        @Override
        public List<?> getSubObjects(Object object) {
            return CustomDataProvider.getSubItems((BaseItem) object);
        }

        @Override
        public boolean isExpandable(Object object) {
            return CustomDataProvider.isExpandable((BaseItem) object);
        }

        @SuppressLint("InflateParams")
        @Override
        public View getViewForObject(Object object, View convertView, ItemInfo itemInfo) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(DoctorMainActivity.this).inflate(R.layout.data_item, null);
                viewHolder.nameView = convertView.findViewById(R.id.dataItemName);
                viewHolder.arrowView = convertView.findViewById(R.id.dataItemArrow);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.nameView.setText(((BaseItem) object).getName());
            if (itemInfo.isExpandable()) {
                viewHolder.arrowView.setVisibility(View.VISIBLE);
                viewHolder.arrowView.setImageResource(itemInfo.isExpanded() ?
                        R.drawable.ic_bottom_arrow : R.drawable.ic_right_arrow);
            } else {
                viewHolder.arrowView.setVisibility(View.GONE);
            }

            return convertView;
        }

        private class ViewHolder {
            TextView nameView;
            ImageView arrowView;
        }
    }
}