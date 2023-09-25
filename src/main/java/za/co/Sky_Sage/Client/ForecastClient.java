package za.co.Sky_Sage.Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import za.co.Sky_Sage.Exceptions.ApiResponseException;
import za.co.Sky_Sage.Exceptions.ConnectionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class ForecastClient {
    private final String apiKey = "2f8176feb051d4d8a922f01c75553571";

    public String establishDailyForcastConnection(String city) throws ParseException {
        try {
            URL url = new URL(String.format("http://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s", city, apiKey));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            String jsonBoi;

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new ApiResponseException("Unexpected ResponseCode:" + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    informationString.append(line);
                }
                reader.close();

                JSONParser parser = new JSONParser();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                JSONObject countryData = (JSONObject) parser.parse(String.valueOf(informationString));

                jsonBoi = gson.toJson(countryData);

                return jsonBoi;

            }
        } catch (IOException e) {
            throw new ConnectionException("Failed to establish connection", e);
        }
    }

    public String establishCurrentLocationForcastConnection(String lat, String lon) throws ParseException {
        try {
            URL url = new URL(String.format("http://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&appid=%s", lat, lon, apiKey));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            String jsonBoi;

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new ApiResponseException("Unexpected ResponseCode:" + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    informationString.append(line);
                }
                reader.close();

                JSONParser parser = new JSONParser();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                JSONObject countryData = (JSONObject) parser.parse(String.valueOf(informationString));

                jsonBoi = gson.toJson(countryData);

                return jsonBoi;
            }
        } catch (IOException e) {
            throw new ConnectionException("Failed to establish connection", e);
        }
    }
}
