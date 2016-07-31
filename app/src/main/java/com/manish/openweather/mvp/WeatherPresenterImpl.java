package com.manish.openweather.mvp;

import com.manish.openweather.data.WeatherInformation;

/**
 * Created by manishp on 4/7/16.
 */

public class WeatherPresenterImpl implements WeatherPresenter, OnWeatherDownloadFinish {

    private WeatherView mWeatherView;
    private WeatherInteractor mWeatherInteractor;

    public WeatherPresenterImpl(WeatherView mWeatherView) {
        this.mWeatherView = mWeatherView;
        mWeatherInteractor = new WeatherInteractorImpl(this);

    }


    @Override
    public void downloadWeatherInfo(String cityName) {

    }

    @Override
    public void downloadWeatherInfo(double latitude, double longitude) {
        if (null != mWeatherInteractor && null != mWeatherView) {
            mWeatherView.showProgress();
            mWeatherInteractor.fetchWeatherInformation(latitude, longitude);
        } else if (null != mWeatherView) {
            mWeatherView.displayError();
        }
    }

    @Override
    public void onDestroy() {
        mWeatherView = null;
    }

    @Override
    public void onDownloadSuccess(WeatherInformation weatherInformation) {


        if (null != mWeatherView) {
            mWeatherView.hideProgress();
            mWeatherView.displayWeatherList(weatherInformation);
        }
    }

    @Override
    public void onDownloadFailure(String error) {
        if (null != mWeatherView) {
            mWeatherView.hideProgress();
            mWeatherView.displayError();
        }
    }
}
