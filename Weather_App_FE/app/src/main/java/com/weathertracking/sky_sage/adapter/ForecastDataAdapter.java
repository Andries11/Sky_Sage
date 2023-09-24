package com.weathertracking.sky_sage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.weathertracking.sky_sage.R;
import com.weathertracking.sky_sage.dao.forecast.ListEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ForecastDataAdapter extends ArrayAdapter<ListEntry> {

    private final LayoutInflater inflater;
    private final List<ListEntry> forecastDataList;


    public ForecastDataAdapter(Context context, List<ListEntry> forecastDataList) {
        super(context, 0, forecastDataList);
        inflater = LayoutInflater.from(context);
        this.forecastDataList = forecastDataList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position >= 0 && position < forecastDataList.size()) {
            ListEntry entry = forecastDataList.get(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_forecast, parent, false);
            }

            if (convertView != null) {

                TextView dateTimeTextView = convertView.findViewById((R.id.day_date));
                TextView weatherDescriptionTextView = convertView.findViewById(R.id.weather_description);
                TextView tempMinTextView = convertView.findViewById(R.id.temp_min);
                TextView tempMaxTextView = convertView.findViewById(R.id.temp_max);

                // Format the date
                String dateString = entry.getDt_txt();
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());

                try {
                    Date date = inputFormat.parse(dateString);
                    String formattedDate = outputFormat.format(date);
                    dateTimeTextView.setText(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    dateTimeTextView.setText("");
                }

                weatherDescriptionTextView.setText(entry.getWeather().get(0).getDescription());
                tempMinTextView.setText(String.valueOf("Min: " + ((int) (entry.getMain().getTemp_min() - 273.15) + "°C")));
                tempMaxTextView.setText(String.valueOf("Max: " + ((int) (entry.getMain().getTemp_max() - 273.15) + "°C")));
            }
        }
        return convertView;
    }
}
