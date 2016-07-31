package com.manish.openweather.mvp;

import com.manish.openweather.data.WeatherInformation;

/**
 * Created by manishp on 5/7/16.
 */

public interface OnWeatherDownloadFinish {

    void onDownloadSuccess(WeatherInformation weatherInformation);

    void onDownloadFailure(String error);
}
