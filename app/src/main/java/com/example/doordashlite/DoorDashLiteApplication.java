package com.example.doordashlite;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoorDashLiteApplication extends Application {
    private static DoorDashLiteApplication instance;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.doordash.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static DoorDashLiteApplication getInstance() {
        return instance;
    }

    public static Retrofit getRetrofit() {
        return getInstance().retrofit;
    }

}
