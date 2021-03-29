package com.uberdoktor.android.apis;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://uberdoktor.com/";
    private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    SharedPrefManager sharedPrefManager;

    private RetrofitClient() {


        retrofit = new Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static synchronized RetrofitClient getInstance() {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public MyInterface getMyInterface() {
        return retrofit.create(MyInterface.class);
    }
}
