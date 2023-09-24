package com.weathertracking.sky_sage.callback;

import com.weathertracking.sky_sage.dao.weather.WeatherData;

import java.io.IOException;

public interface WeatherDataCallback {
    void onWeatherDataReceived(WeatherData weatherData);

    default void onWeatherDataError(IOException errorMessage) {
        errorMessage.getStackTrace();
    }

}
