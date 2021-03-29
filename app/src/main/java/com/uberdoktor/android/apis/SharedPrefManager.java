package com.uberdoktor.android.apis;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Switch;

import com.uberdoktor.android.R;
import com.uberdoktor.android.fragment.DoctorHomeFragment;
import com.uberdoktor.android.model.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "uberdoctor";
    private static SharedPreferences sharedPreferences;
    Context context;
    private String accessToken;
    private static SharedPreferences.Editor editor;


    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }


    public void saveAccessToken(String token) {
        editor = sharedPreferences.edit();
        editor.putString("access_token", token).apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString("access_token", "");
    }

    public void saveUser(User user) {
        editor = sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("phone_no", user.getPhoneNo());
        editor.putBoolean("logged", true);
//        editor.putBoolean(ONLINE_SWITCH, )
        editor.apply();
    }

//    public void setPhone(User user) {
//        editor = sharedPreferences.edit();
//        editor.putString("phone_no", user.getPhoneNo());
//        editor.apply();
//    }
//
//    public String getPhone() {
//        return sharedPreferences.getString("phone_no", "");
//    }

    public User getUser() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User();
    }

    public boolean isLoggedIn() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged", false);
    }

    //
    public void logout() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
