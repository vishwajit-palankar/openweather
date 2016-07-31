package com.manish.openweather.mvp;

import android.util.Log;

import com.manish.openweather.R;
import com.manish.openweather.data.WeatherInformation;
import com.manish.openweather.network.GetWeather;
import com.manish.openweather.utility.Constant;
import com.manish.openweather.utility.NetworkUtil;
import com.manish.openweather.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by manishp on 5/7/16.
 */

public class WeatherInteractorImpl implements WeatherInteractor {


    private OnWeatherDownloadFinish mOnWeatherDownloadFinish;


    public WeatherInteractorImpl(OnWeatherDownloadFinish mOnWeatherDownloadFinish) {
        this.mOnWeatherDownloadFinish = mOnWeatherDownloadFinish;

    }

    @Override
    public void fetchWeatherInformation(double latitude, double longitude) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GetWeather apiService =
                retrofit.create(GetWeather.class);

        Call<WeatherInformation> call = apiService.getWeatherInformation(latitude, longitude, Constant.METRIC, Constant.NO_OF_DAYS, Constant.API_KEY);

        call.enqueue(new Callback<WeatherInformation>() {
            @Override
            public void onResponse(Call<WeatherInformation> call, Response<WeatherInformation> response) {
                WeatherInformation weatherInformation = response.body();

                if (null != weatherInformation) {
                    mOnWeatherDownloadFinish.onDownloadSuccess(weatherInformation);
                } else {
                    mOnWeatherDownloadFinish.onDownloadFailure(Utility.getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<WeatherInformation> call, Throwable t) {
                Log.e(TAG, "Fetch weather information failed", t);
                mOnWeatherDownloadFinish.onDownloadFailure(Utility.getString(R.string.something_went_wrong));
            }
        });

    }
}
