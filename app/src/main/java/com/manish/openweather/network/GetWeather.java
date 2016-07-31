package com.manish.openweather.network;

import com.manish.openweather.data.WeatherInformation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by manishp on 4/7/16.
 */

public interface GetWeather {

    @GET("daily")
    Call<WeatherInformation> getWeatherInformation(@Query("lat") double latitude, @Query("lon") double longitude, @Query("units") String units, @Query("cnt") int numberOfDays, @Query("APPID") String appId);
}
