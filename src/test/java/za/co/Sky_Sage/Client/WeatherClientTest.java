package za.co.Sky_Sage.Client;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.Sky_Sage.Exceptions.ApiResponseException;
import za.co.Sky_Sage.Exceptions.ConnectionException;

import static org.junit.jupiter.api.Assertions.*;

class WeatherClientTest {
    private WeatherClient weatherClient;

//    private String expectedResponse = "{\n" +
//            "  \"visibility\": 10000,\n" +
//            "  \"timezone\": 7200,\n" +
//            "  \"main\": {\n" +
//            "    \"temp\": 298.62,\n" +
//            "    \"temp_min\": 298.62,\n" +
//            "    \"humidity\": 31,\n" +
//            "    \"pressure\": 1022,\n" +
//            "    \"feels_like\": 298.03,\n" +
//            "    \"temp_max\": 298.62\n" +
//            "  },\n" +
//            "  \"clouds\": {\n" +
//            "    \"all\": 0\n" +
//            "  },\n" +
//            "  \"sys\": {\n" +
//            "    \"country\": \"ZA\",\n" +
//            "    \"sunrise\": 1695613746,\n" +
//            "    \"sunset\": 1695657531,\n" +
//            "    \"id\": 1979,\n" +
//            "    \"type\": 1\n" +
//            "  },\n" +
//            "  \"dt\": 1695635476,\n" +
//            "  \"coord\": {\n" +
//            "    \"lon\": 29.4688,\n" +
//            "    \"lat\": -23.9045\n" +
//            "  },\n" +
//            "  \"weather\": [\n" +
//            "    {\n" +
//            "      \"icon\": \"01d\",\n" +
//            "      \"description\": \"clear sky\",\n" +
//            "      \"main\": \"Clear\",\n" +
//            "      \"id\": 800\n" +
//            "    }\n" +
//            "  ],\n" +
//            "  \"name\": \"Polokwane\",\n" +
//            "  \"cod\": 200,\n" +
//            "  \"id\": 965289,\n" +
//            "  \"base\": \"stations\",\n" +
//            "  \"wind\": {\n" +
//            "    \"deg\": 50,\n" +
//            "    \"speed\": 7.72\n" +
//            "  }\n}";

    @BeforeEach
    void setUp() {
        weatherClient = new WeatherClient();
    }

    @Test
    void establishWeatherConnection() {
        try {
            String city = "Polokwane";
            String response = weatherClient.establishWeatherConnection(city);
            assertNotNull(response);
//            assertEquals( expectedResponse , response);
//            Expected and actual cant be tested effectively since the actual response data is live and will never match the static expected values
        } catch (ConnectionException | ApiResponseException e) {
            fail("Unexpected exception: " + e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getClass().getSimpleName());
        }

    }

    @Test
    public void testEstablishConnection_ApiResponseException() {
        try {
            String city = "Unicorn City";
            String response = weatherClient.establishWeatherConnection(city);
            fail("Expected ApiResponseException");
        } catch (ApiResponseException e) {
            // Expected exception
            assertEquals("Unexpected ResponseCode:404", e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void establishCurrentLocationConnection() {
        try {
            String lat = "-23.9045";
            String lon = "29.4688";
            String response = weatherClient.establishCurrentLocationConnection(lat, lon);
            assertNotNull(response);
//            assertEquals( expectedResponse , response);
//            Expected and actual cant be tested effectively since the actual response data is live and will never match the static expected values
        } catch (ConnectionException | ApiResponseException e) {
            fail("Unexpected exception: " + e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getClass().getSimpleName());
        }

    }

    @Test
    public void testEstablishCurrentLocationConnection_ApiResponseException() {
        try {
            String lat = "37.4226711";
            String lon = "Caaaarrlllll";
            String response = weatherClient.establishCurrentLocationConnection(lat, lon);
            fail("Expected ApiResponseException");
        } catch (ApiResponseException e) {
            // Expected exception
            assertEquals("Unexpected ResponseCode:400", e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //I have found that parse exceptions occur very rarely since our values are coming from an existing api,
    // if there is a parse exception it could be because their system is down but even then the exception that will be thrown is a ApiResponseException
}