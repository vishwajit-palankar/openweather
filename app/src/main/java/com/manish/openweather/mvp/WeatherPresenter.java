package com.manish.openweather.mvp;

/**
 * Created by manishp on 4/7/16.
 */

public interface WeatherPresenter {

    void downloadWeatherInfo(String cityName);

    void downloadWeatherInfo(double latitude, double longitude);

    void onDestroy();

}
