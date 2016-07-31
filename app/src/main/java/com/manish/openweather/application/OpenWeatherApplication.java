package com.manish.openweather.application;

import android.app.Activity;
import android.app.Application;

import com.manish.openweather.utility.Utility;

/**
 * Created by manishp on 3/7/16.
 */

public class OpenWeatherApplication extends Application {

    private static OpenWeatherApplication sInstance;
    private Activity mLiveContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        Utility.isConnectedToInternet = Utility.isConnectedToInternet(this);
    }

    public static OpenWeatherApplication getInstance() {
        return sInstance;
    }

    public Activity getLiveContext() {
        return mLiveContext;
    }

    public void setLiveContext(Activity mLiveContext) {
        this.mLiveContext = mLiveContext;
    }
}
