package com.manish.openweather.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.manish.openweather.application.OpenWeatherApplication;
import com.manish.openweather.utility.Utility;

public class MyBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OpenWeatherApplication.getInstance().setLiveContext(this);
        checkInternetConnection();

    }

    protected void checkInternetConnection() {
        if (!Utility.isConnectedToInternet) {
//            Utility.showNoInternetDialogWithFinish();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        OpenWeatherApplication.getInstance().setLiveContext(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        OpenWeatherApplication.getInstance().setLiveContext(this);
    }
}
