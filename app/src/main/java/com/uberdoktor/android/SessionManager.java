package com.uberdoktor.android;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    public SharedPreferences sharedPreferences;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }
}
