package com.uberdoktor.android.activity;

import android.app.Activity;
import android.app.ProgressDialog;

import com.uberdoktor.android.R;

public class UploadingDialog {

    Activity activity;
    ProgressDialog uploadingProgressDialog;

    UploadingDialog(Activity mActivity) {
        activity = mActivity;
    }


    void startUploadingDialog() {
        uploadingProgressDialog = new ProgressDialog(activity);
        uploadingProgressDialog.show();
        uploadingProgressDialog.setContentView(R.layout.custom_uploading_progress_dialog);
        uploadingProgressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}
