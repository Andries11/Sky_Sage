package za.co.Sky_Sage.Controller;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.co.Sky_Sage.Client.ForecastClient;
import za.co.Sky_Sage.Client.WeatherClient;
import za.co.Sky_Sage.MockVals.MockResponseVals;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = WeatherController.class)
class WeatherControllerTest {

    private MockResponseVals mockResponse;
    @InjectMocks
    private WeatherController controller;

    @Mock
    private WeatherClient weatherClient;

    @Mock
    private ForecastClient forecastClient;


    @BeforeEach
    void setUp() {
        controller = new WeatherController();
        mockResponse = new MockResponseVals();
    }

    @Test
    void getWeatherByCity() throws ParseException {
        String city = "Cape town";
        String expectedWeatherDate = mockResponse.getMockWeatherResponseCity();

        when(weatherClient.establishWeatherConnection(city))
                .thenReturn(expectedWeatherDate);
        ResponseEntity<String> response = controller.getWeatherByCity(city);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedWeatherDate, response.getBody());
    }

    @Test
    void getWeatherByLatLon() throws ParseException, IOException {
        String lat = "-33.9258";
        String lon = "18.4232";
        String expectedWeatherDate = mockResponse.getMockWeatherResponseLatLon();

        when(weatherClient.establishCurrentLocationConnection(lat, lon))
                .thenReturn(expectedWeatherDate);
        ResponseEntity<String> response = controller.getWeatherByLatLon(lat, lon);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedWeatherDate, response.getBody());
    }

    @Test
    void getForcastByCity() throws ParseException, IOException {
        String city = "Cape town";
        String expectedForecastDate = mockResponse.getGetMockForecastResponseCity();

        when(forecastClient.establishDailyForcastConnection(city))
                .thenReturn(expectedForecastDate);
        ResponseEntity<String> response = controller.getForcastByCity(city);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedForecastDate, response.getBody());
    }

    @Test
    void getForcastByLatLon() throws ParseException, IOException {
        String lat = "-33.9258";
        String lon = "18.4232";
        String expectedForecastDate = mockResponse.getMockForecastResponseLatLon();

        when(forecastClient.establishCurrentLocationForcastConnection(lat, lon))
                .thenReturn(expectedForecastDate);
        ResponseEntity<String> response = controller.getForcastByLatLon(lat, lon);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedForecastDate, response.getBody());
    }
}