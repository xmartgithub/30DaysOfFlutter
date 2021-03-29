package com.uberdoktor.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uberdoktor.android.R;
import com.uberdoktor.android.adapter.CustomAdapter;
import com.uberdoktor.android.adapter.PendingAppointmentsAdapter;
import com.uberdoktor.android.apis.RetrofitClient;
import com.uberdoktor.android.apis.SharedPrefManager;
import com.uberdoktor.android.fragment.OverviewFragment;
import com.uberdoktor.android.response.VerifyPaymentResponse;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodActivity extends AppCompatActivity {

    Dialog dialog;
    ConstraintLayout constraintLayout;
    LinearLayout easyPaisaLayout;
    LinearLayout jazzCashLayout;
    SwitchCompat switchButton;
    private Button payNowBtn;

    /////////
    TextView doctorName;
    TextView doctorFee;
    TextView walletBalance;
    TextView paymentMethodAppointmentDateTime;
    TextView paymentMethodWalletBalanceToBeUsed;

    public static String walletAmount, paymentMethod, paymentType = "mobile", senderName, senderMobileNumber, paymentAmount, trackingId;
    SharedPrefManager sharedPrefManager;
    //dialog class variables
    EditText editTextSenderName, editTextSenderNo, editTextAmount, editTextTrackingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_method_layout);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Payment Method");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        sharedPrefManager = new SharedPrefManager(this);

        constraintLayout = findViewById(R.id.easy_paisa_layout);
        easyPaisaLayout = findViewById(R.id.easy_paisa_linear_layout);
        jazzCashLayout = findViewById(R.id.jazz_cash_linear_layout);
        payNowBtn = findViewById(R.id.pay_now_btn);

//        switch button to use wallet amount or not
        switchButton = findViewById(R.id.swtich_to_use_wallet_amount);

        //dialog class find ids
        editTextSenderName = dialog.findViewById(R.id.editText_sender_name);
        editTextSenderNo = dialog.findViewById(R.id.editText_sender_mobile_no);
        editTextAmount = dialog.findViewById(R.id.editText_Amt);
        editTextTrackingId = dialog.findViewById(R.id.editText_TxId);

        Button okayBtn = dialog.findViewById(R.id.ok_d_btn);
        Button cancelBtn = dialog.findViewById(R.id.cancel_d_btn);


        doctorName = findViewById(R.id.tv_payment_method_doc_name);
        doctorFee = findViewById(R.id.payment_method_tv_appointment_fee);
        walletBalance = findViewById(R.id.tv_wallet_available_amount);
        paymentMethodAppointmentDateTime = findViewById(R.id.tv_payment_method_date_time);
        paymentMethodWalletBalanceToBeUsed = findViewById(R.id.payment_method_use_wallet_balance);

        //from overview fragment
        String docName = getIntent().getStringExtra("doctorName");
        String appFee = getIntent().getStringExtra("fee");
        String balance = getIntent().getStringExtra("walletBalance");
        String appDate = getIntent().getStringExtra("appointmentDate");

        //from pendingAppointments adapter
        String pendingAppointmentsDocName = getIntent().getStringExtra("doctorName");
        String pendingAppointmentsAppFee = getIntent().getStringExtra("appointmentFee");
        String pendingAppointmentsBalance = getIntent().getStringExtra("walletBalance");
        String pendingAppointmentsAppDate = getIntent().getStringExtra("appointmentDate");
        String pendingAppointmentsAppTime = getIntent().getStringExtra("appointmentTime");

        //from customAdapter
        String customAdapterDocName = getIntent().getStringExtra("doctorName");
        String customAdapterAppFee = getIntent().getStringExtra("appointmentFee");
        String customAdapterBalance = getIntent().getStringExtra("walletBalance");
        String customAdapterAppDate = getIntent().getStringExtra("appointmentDate");
        String customAdapterAppTime = getIntent().getStringExtra("appointmentTime");

        if (OverviewFragment.doctorName != null) {
            doctorName.setText(docName);
            doctorFee.setText(appFee);
            walletBalance.setText(balance);

            String str = appDate;

            try {
                SimpleDateFormat fmInput = new SimpleDateFormat("yyyy-MM-dd");
                Date date = fmInput.parse(str);
                SimpleDateFormat fmtOutput = new SimpleDateFormat("dd MMM");
                assert date != null;
                str = fmtOutput.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String dateTime = str + " " + OverviewFragment.appointmentTime;
            paymentMethodAppointmentDateTime.setText(dateTime);
            paymentMethodWalletBalanceToBeUsed.setText(balance);

            payNowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<VerifyPaymentResponse> call = RetrofitClient.getInstance().getMyInterface().verifyPayment("Bearer " + sharedPrefManager.getAccessToken(), walletAmount, paymentMethod, paymentType, senderName, senderMobileNumber, paymentAmount, trackingId, Integer.parseInt(OverviewFragment.patientAppointmentId));
                    call.enqueue(new Callback<VerifyPaymentResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<VerifyPaymentResponse> call, @NotNull Response<VerifyPaymentResponse> response) {

                            assert response.body() != null;
                            if (response.isSuccessful()) {
                                if (response.body().getMobile() != null) {
                                    Toast.makeText(PaymentMethodActivity.this, response.body().getMobile(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(PaymentMethodActivity.this, MainActivity.class));
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<VerifyPaymentResponse> call, @NotNull Throwable t) {
                            Toast.makeText(PaymentMethodActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        if (PendingAppointmentsAdapter.paymentBookedDoctorName != null) {
            doctorName.setText(pendingAppointmentsDocName);
            doctorFee.setText(pendingAppointmentsAppFee);
            walletBalance.setText(pendingAppointmentsBalance);

            String str1 = PendingAppointmentsAdapter.paymentBookedDate;

            try {
                SimpleDateFormat fmInput = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = fmInput.parse(str1);
                SimpleDateFormat fmtOutput = new SimpleDateFormat("dd MMM");
                assert date1 != null;
                str1 = fmtOutput.format(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String dateTime1 = str1 + " " + pendingAppointmentsAppTime;
            paymentMethodAppointmentDateTime.setText(dateTime1);
            paymentMethodWalletBalanceToBeUsed.setText(pendingAppointmentsBalance);

            payNowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<VerifyPaymentResponse> call = RetrofitClient.getInstance().getMyInterface().verifyPayment("Bearer " + sharedPrefManager.getAccessToken(), walletAmount, paymentMethod, paymentType, senderName, senderMobileNumber, paymentAmount, trackingId, Integer.parseInt(PendingAppointmentsAdapter.pendingPaymentId));
                    call.enqueue(new Callback<VerifyPaymentResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<VerifyPaymentResponse> call, @NotNull Response<VerifyPaymentResponse> response) {

                            assert response.body() != null;
                            if (response.isSuccessful()) {
                                if (response.body().getMobile() != null) {
                                    Toast.makeText(PaymentMethodActivity.this, response.body().getMobile(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(PaymentMethodActivity.this, MainActivity.class));
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<VerifyPaymentResponse> call, @NotNull Throwable t) {
                            Toast.makeText(PaymentMethodActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        if (CustomAdapter.doctorName != null) {
            doctorName.setText(customAdapterDocName);
            doctorFee.setText(customAdapterAppFee);
            walletBalance.setText(customAdapterBalance);

            String str2 = CustomAdapter.appointmentDate;

            try {
                SimpleDateFormat fmInput = new SimpleDateFormat("yyyy-MM-dd");
                Date date2 = fmInput.parse(str2);
                SimpleDateFormat fmtOutput = new SimpleDateFormat("dd MMM");
                assert date2 != null;
                str2 = fmtOutput.format(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String dateTime1 = str2 + " " + pendingAppointmentsAppTime;
            paymentMethodAppointmentDateTime.setText(dateTime1);
            paymentMethodWalletBalanceToBeUsed.setText(customAdapterBalance);

            payNowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<VerifyPaymentResponse> call = RetrofitClient.getInstance().getMyInterface().verifyPayment("Bearer " + sharedPrefManager.getAccessToken(), walletAmount, paymentMethod, paymentType, senderName, senderMobileNumber, paymentAmount, trackingId, CustomAdapter.pendingPaymentId);
                    call.enqueue(new Callback<VerifyPaymentResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<VerifyPaymentResponse> call, @NotNull Response<VerifyPaymentResponse> response) {

                            assert response.body() != null;
                            if (response.isSuccessful()) {
                                if (response.body().getMobile() != null) {
                                    Toast.makeText(PaymentMethodActivity.this, response.body().getMobile(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(PaymentMethodActivity.this, MainActivity.class));
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<VerifyPaymentResponse> call, @NotNull Throwable t) {
                            Toast.makeText(PaymentMethodActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()) {
                    walletAmount = "use";
                } else {
                    walletAmount = "not use";
                }
            }
        });


        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senderName = editTextSenderName.getText().toString();
                senderMobileNumber = editTextSenderNo.getText().toString();
                paymentAmount = editTextAmount.getText().toString();
                trackingId = editTextTrackingId.getText().toString();

                dialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        easyPaisaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                paymentMethod = "easypaisa";
            }
        });

        jazzCashLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
//                paymentMethod = "jazzcash";
            }
        });


    }
}