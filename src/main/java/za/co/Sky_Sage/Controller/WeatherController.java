package za.co.Sky_Sage.Controller;

import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.Sky_Sage.Client.ApiClient;

import java.io.IOException;

@RestController
@RequestMapping("/Sky_Sage")
public class WeatherController {

    ApiClient clientService = new ApiClient();

    @GetMapping(value = "/weather-by-city/{city}")
    public ResponseEntity<String> getWeatherByCity(@PathVariable("city") String city) throws IOException, ParseException {
        String weatherData = clientService.establishConnection(city);
        return ResponseEntity.ok().body(weatherData);
    }

    @GetMapping(value = "/weather-by-lat-lon/lat={lat}&lon={lon}")
    public ResponseEntity<String> getWeatherByLatLon(@PathVariable("lat") String lat, @PathVariable("lon") String lon) throws IOException, ParseException {
        String weatherData = clientService.establishCurrentLocationConnection(lat, lon);
        return ResponseEntity.ok().body(weatherData);
    }

    @GetMapping(value = "/forecast-by-city/{city}")
    public ResponseEntity<String> getForcastByCity(@PathVariable("city") String city) throws IOException, ParseException {
        String weatherData = clientService.establishDailyForcastConnection(city);
        return ResponseEntity.ok().body(weatherData);
    }

    @GetMapping(value = "/forecast-by-lat-lon/lat={lat}&lon={lon}")
    public ResponseEntity<String> getForcastByLatLon(@PathVariable("lat") String lat, @PathVariable("lon") String lon) throws IOException, ParseException {
        String weatherData = clientService.establishCurrentLocationForcastConnection(lat, lon);
        return ResponseEntity.ok().body(weatherData);
    }

}
