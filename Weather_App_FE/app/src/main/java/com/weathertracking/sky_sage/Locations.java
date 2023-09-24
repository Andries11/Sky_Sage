package com.weathertracking.sky_sage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.weathertracking.sky_sage.adapter.ForecastDataAdapter;
import com.weathertracking.sky_sage.callback.ForecastDataCallback;
import com.weathertracking.sky_sage.callback.WeatherDataCallback;
import com.weathertracking.sky_sage.client.LocationRetrieverClient;
import com.weathertracking.sky_sage.dao.forecast.ForecastData;
import com.weathertracking.sky_sage.dao.forecast.ListEntry;
import com.weathertracking.sky_sage.dao.weather.WeatherData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Locations extends Fragment {
    private final LocationRetrieverClient locationRetrieverClient = new LocationRetrieverClient();
    private EditText cityInput;
    private TextView weatherNameTextView;
    private TextView weatherTempTextView;
    private ForecastDataAdapter forecastAdapter;

    public static Locations newInstance(double latitude, double longitude) {
        Locations fragment = new Locations();
        Bundle args = new Bundle();
        args.putDouble("latitude", latitude);
        args.putDouble("longitude", longitude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.locations_fragment, container, false);
        cityInput = rootView.findViewById(R.id.cityInput);
        Button searchButton = rootView.findViewById(R.id.searchButton);
        weatherNameTextView = rootView.findViewById(R.id.weatherNameTextView);
        weatherTempTextView = rootView.findViewById(R.id.weatherTempTextView);

        //Set up list adapter and import it into the list_view
        ListView forecastListView = rootView.findViewById(R.id.forecastListView);
        forecastAdapter = new ForecastDataAdapter(requireContext(), new ArrayList<>());
        forecastListView.setAdapter(forecastAdapter);

        Bundle args = getArguments();
        if (args != null) {
            double latitude = args.getDouble("latitude");
            double longitude = args.getDouble("longitude");
            fetchWeatherDataLatLon(String.valueOf(latitude), String.valueOf(longitude));
        }

        searchButton.setOnClickListener(v -> {
            String cityName = cityInput.getText().toString();
            if (!cityName.isEmpty()) {
                fetchWeatherData(cityName);
            }
        });

        // Enter action acts as search button also #Convenience ;D
        cityInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String cityName = cityInput.getText().toString();
                if (!cityName.isEmpty()) {
                    fetchWeatherData(cityName);
                    return true;
                }
            }
            return false;
        });

        return rootView;
    }

    private void fetchWeatherData(String cityName) {
        locationRetrieverClient.fetchWeatherData(cityName, new WeatherDataCallback() {
            @Override
            public void onWeatherDataReceived(WeatherData weatherData) {
                int currentTemp = (int) (weatherData.getMain().getTemp() - 273.15);
                String cityName = weatherData.getName() + "\n";
                displayWeatherData(cityName, currentTemp);
            }

            @Override
            public void onWeatherDataError(IOException errorMessage) {
                throw new RuntimeException("Could not receive and parse data");
            }
        });
        locationRetrieverClient.fetchForecastData(cityName, new ForecastDataCallback() {
            @Override
            public void onForecastDataReceived(ForecastData forecastData) {
                List<ListEntry> dailyForecast = forecastData.getList();

                displayForecastData(dailyForecast);
            }
        });
    }

    private void fetchWeatherDataLatLon(String latitude, String longitude) {
        locationRetrieverClient.fetchWeatherDataLatLon(latitude, longitude, new WeatherDataCallback() {

            @Override
            public void onWeatherDataReceived(WeatherData weatherData) {
                int currentTemp = (int) (weatherData.getMain().getTemp() - 273.15);
                String cityName = weatherData.getName() + "\n";
                displayWeatherData(cityName, currentTemp);
            }

            @Override
            public void onWeatherDataError(IOException errorMessage) {
                throw new RuntimeException("Could not receive and parse data");
            }
        });
        locationRetrieverClient.fetchForecastLatLon(latitude, longitude, new ForecastDataCallback() {
            @Override
            public void onForecastDataReceived(ForecastData forecastData) {
                List<ListEntry> dailyForecast = forecastData.getList();

                displayForecastData(dailyForecast);
            }
        });

    }

    private void displayWeatherData(String cityName, int currentTemp) {
        requireActivity().runOnUiThread(() -> {
            weatherNameTextView.setText(cityName);
            weatherTempTextView.setText(currentTemp + "°C");
        });
    }

    private void displayForecastData(List<ListEntry> dailyForecast) {
        requireActivity().runOnUiThread(() -> {
            // Clear the existing data in the adapter
            forecastAdapter.clear();

            // Extract one entry for each of the 5 days
            List<ListEntry> oneDayForecast = new ArrayList<>();
            SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String currentDay = "";
            int dayCount = 0;

            for (ListEntry entry : dailyForecast) {
                String day = dayFormat.format(new Date(entry.getDt() * 1000L));

                // Check if it's a new day
                if (!day.equals(currentDay)) {
                    oneDayForecast.add(entry);
                    currentDay = day;
                    dayCount++;

                    if (dayCount >= 5) {
                        break;
                    }
                }
            }

            forecastAdapter.addAll(oneDayForecast);
            forecastAdapter.notifyDataSetChanged();
        });
    }
 }
