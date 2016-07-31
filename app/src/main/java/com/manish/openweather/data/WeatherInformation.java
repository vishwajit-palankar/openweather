package com.manish.openweather.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by manishp on 4/7/16.
 */

public class WeatherInformation {

    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private double message;
    @SerializedName("cnt")
    @Expose
    private int cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<WeatherList> weatherList = new ArrayList<>();

    /**
     * @return The city
     */
    public City getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * @return The cod
     */
    public String getCod() {
        return cod;
    }

    /**
     * @param cod The cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     * @return The message
     */
    public double getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(double message) {
        this.message = message;
    }

    /**
     * @return The cnt
     */
    public int getCnt() {
        return cnt;
    }

    /**
     * @param cnt The cnt
     */
    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    /**
     * @return The weatherList
     */
    public java.util.List<WeatherList> getWeatherList() {
        return weatherList;
    }

    /**
     * @param weatherList The weatherList
     */
    public void setWeatherList(java.util.List<WeatherList> weatherList) {
        this.weatherList = weatherList;
    }

}
