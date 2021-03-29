package com.uberdoktor.android.activity;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.uberdoktor.android.R;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.fragment.AppointmentFragment;
import com.uberdoktor.android.fragment.LabFragment;
import com.uberdoktor.android.fragment.OverviewFragment;
import com.uberdoktor.android.fragment.PaymentFragment;
import com.uberdoktor.android.fragment.PharmacyFragment;
import com.uberdoktor.android.navigation.BaseItem;
import com.uberdoktor.android.navigation.CustomDataProvider;
import com.uberdoktor.android.response.LogoutResponse;
import com.uberdoktor.android.response.ProfileResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListAdapter;
import pl.openrnd.multilevellistview.MultiLevelListView;
import pl.openrnd.multilevellistview.OnItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle actionBarDrawerToggle;
    SharedPrefManager sharedPrefManager;

    private long backPressedTime;

    private final OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        private void showItemDescription(Object object, ItemInfo itemInfo) {

            if (((BaseItem) object).getName().contains("Overview")) {
                displaySelectedScreen("OVERVIEW");
            }
            if (((BaseItem) object).getName().contains("Medical History")) {
                displaySelectedScreen("MEDICAL HISTORY");
            }
            if (((BaseItem) object).getName().contains("Consultations")) {
                displaySelectedScreen("CONSULTATIONS");
            }
            if (((BaseItem) object).getName().contains("Payments")) {
                displaySelectedScreen("PAYMENTS");
            }
            if (((BaseItem) object).getName().contains("Pharmacy")) {
                displaySelectedScreen("PHARMACY");
            }
            if (((BaseItem) object).getName().contains("Lab")) {
                displaySelectedScreen("LAB");
            }
            if (((BaseItem) object).getName().contains("Video Call")) {
                displaySelectedScreen("VIDEO CALL");
            }
            if (((BaseItem) object).getName().contains("Help")) {
                displaySelectedScreen("HELP");
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPrefManager = new SharedPrefManager(MainActivity.this);

        Button completeProfileBtn = findViewById(R.id.complete_profile_btn);
        TextView patientPhoneNo = findViewById(R.id.nav_header_patient_phone_no);
        TextView patientBalance = findViewById(R.id.nav_header_textView_patient_balance);


        BottomNavigationView bottomNavigationView = findViewById(R.id.my_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigationView.setItemIconTintList(null);
        confMenu();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        displaySelectedScreen("OVERVIEW");
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.first_color));
        actionBarDrawerToggle.syncState();


        completeProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), PatientProfileDetailsActivity.class));
            }
        });

        /////////////////////
        Call<ProfileResponse> call = RetrofitClient.getInstance().getMyInterface().userProfile("Bearer " + sharedPrefManager.getAccessToken());
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProfileResponse> call, @NotNull Response<ProfileResponse> response) {

                ProfileResponse profileResponse = response.body();
                if (response.isSuccessful()) {
                    assert profileResponse != null;
                    patientBalance.setText(profileResponse.getUserProfile().getBalance());
                    patientPhoneNo.setText(profileResponse.getUserProfile().getPhoneNo());

                }
            }

            @Override
            public void onFailure(@NotNull Call<ProfileResponse> call, @NotNull Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        /////////////////////
    }


    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.overviewFragment:
                            selectedFragment = new OverviewFragment();
                            break;
                        case R.id.appointmentFragment:
                            selectedFragment = new AppointmentFragment();
                            break;
                        case R.id.paymentFragment:
                            selectedFragment = new PaymentFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                            selectedFragment).commit();
                    return true;
                }
            };

    private void confMenu() {
        MultiLevelListView multiLevelListView = findViewById(R.id.multi_nav);
        // custom ListAdapter
        ListAdapter listAdapter = new ListAdapter();
        multiLevelListView.setAdapter(listAdapter);
        multiLevelListView.setOnItemClickListener(mOnItemClickListener);
        listAdapter.setDataItems(CustomDataProvider.getInitialItems());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
            case "OVERVIEW":
                fragment = new OverviewFragment();
                break;
            case "CONSULTATIONS":
                startActivity(new Intent(getApplicationContext(), PatientConsultationsActivity.class));
                break;
            case "MEDICAL HISTORY":
                startActivity(new Intent(getApplicationContext(), MedicalHistoryActivity.class));
                break;
            case "PAYMENTS":
                fragment = new PaymentFragment();
                break;
            case "PHARMACY":
                fragment = new PharmacyFragment();
                break;
            case "LAB":
                fragment = new LabFragment();
                break;
            case "VIDEO CALL":
                startActivity(new Intent(getApplicationContext(), VideoCall.class));
                break;
            case "HELP":
                startActivity(new Intent(getApplicationContext(), HelpActivity.class));
                break;
            case "LOGOUT":
                Call<LogoutResponse> call = RetrofitClient.getInstance().getMyInterface().patientLogout("Bearer " + sharedPrefManager.getAccessToken());

                call.enqueue(new Callback<LogoutResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<LogoutResponse> call, @NotNull Response<LogoutResponse> response) {

                        if (response.isSuccessful()) {
                            Intent logoutIntent = new Intent(MainActivity.this, RegisterActivity.class);
                            startActivity(logoutIntent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<LogoutResponse> call, @NotNull Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

    }

    private void logout() {
        sharedPrefManager.logout();
        Toast.makeText(this, "You are logged out", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method display selected screen and passing the id of selected menu
        displaySelectedScreen(String.valueOf(item.getItemId()));
        //make this method blank
        return true;
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
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.data_item, null);
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

