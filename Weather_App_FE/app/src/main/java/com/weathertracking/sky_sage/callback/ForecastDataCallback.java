package com.weathertracking.sky_sage.callback;

import com.weathertracking.sky_sage.dao.forecast.ForecastData;

import java.io.IOException;

public interface ForecastDataCallback {
    void onForecastDataReceived(ForecastData forecastData);

    default void onForecastDataError(IOException errorMessage) {
        errorMessage.getStackTrace();
    }
}
