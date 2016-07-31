package com.manish.openweather.mvp;

import com.manish.openweather.data.WeatherInformation;

/**
 * Created by manishp on 3/7/16.
 */

public interface WeatherView {

    void showProgress();

    void hideProgress();

    void displayWeatherList(WeatherInformation weatherInformation);

    void displayError();

}
