package com.weathertracking.sky_sage.client;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.weathertracking.sky_sage.callback.ForecastDataCallback;
import com.weathertracking.sky_sage.callback.WeatherDataCallback;
import com.weathertracking.sky_sage.dao.forecast.ForecastData;
import com.weathertracking.sky_sage.dao.weather.WeatherData;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LocationRetrieverClient {
    public void fetchWeatherData(String cityName, WeatherDataCallback callback) {
        OkHttpClient client = new OkHttpClient();
        String baseUrl = String.format("http://192.168.68.126:8080/Sky_Sage/weather-by-city/%s", cityName);

        Request request = new Request.Builder()
                .url(baseUrl)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String jsonResponse = response.body().string();

                    Gson gson = new Gson();
                    WeatherData weatherData = gson.fromJson(jsonResponse, WeatherData.class);
                    callback.onWeatherDataReceived(weatherData);

                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onWeatherDataError(e);
            }
        });
    }

    public void fetchWeatherDataLatLon(String lat, String lon, WeatherDataCallback callback) {
        OkHttpClient client = new OkHttpClient();
        String baseUrl = String.format("http://192.168.68.126:8080/Sky_Sage/weather-by-lat-lon/lat=%s&lon=%s", lat, lon);

        Request request = new Request.Builder()
                .url(baseUrl)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String jsonResponse = response.body().string();

                    Gson gson = new Gson();
                    WeatherData weatherData = gson.fromJson(jsonResponse, WeatherData.class);
                    callback.onWeatherDataReceived(weatherData);

                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onWeatherDataError(e);
            }
        });
    }

    public void fetchForecastData(String cityName, ForecastDataCallback callback) {
        OkHttpClient client = new OkHttpClient();
        String baseUrl = String.format("http://192.168.68.126:8080/Sky_Sage/forecast-by-city/%s", cityName);

        Request request = new Request.Builder()
                .url(baseUrl)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String jsonResponse = response.body().string();

                    Gson gson = new Gson();
                    ForecastData weatherData = gson.fromJson(jsonResponse, ForecastData.class);
                    callback.onForecastDataReceived(weatherData);

                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onForecastDataError(e);
            }
        });
    }

    public void fetchForecastLatLon(String lat, String lon, ForecastDataCallback callback) {
        OkHttpClient client = new OkHttpClient();

        String baseUrl = String.format("http://192.168.68.126:8080/Sky_Sage/forecast-by-lat-lon/lat=%s&lon=%s", lat, lon);

        Request request = new Request.Builder()
                .url(baseUrl)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String jsonResponse = response.body().string();

                    Gson gson = new Gson();
                    ForecastData weatherData = gson.fromJson(jsonResponse, ForecastData.class);
                    callback.onForecastDataReceived(weatherData);

                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                callback.onForecastDataError(e);
            }
        });
    }
}
