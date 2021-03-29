package com.uberdoktor.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.TextView;

import com.uberdoktor.android.R;

public class LoadingDialog {

    Activity activity;
    public ProgressDialog progressDialog;


    public LoadingDialog(Activity mActivity) {
        activity = mActivity;
    }


    public void startLoadingDialog() {


        progressDialog = new ProgressDialog(activity);
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_loading_progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }


}
