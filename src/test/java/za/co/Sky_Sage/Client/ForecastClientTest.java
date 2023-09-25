package za.co.Sky_Sage.Client;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.Sky_Sage.Exceptions.ApiResponseException;
import za.co.Sky_Sage.Exceptions.ConnectionException;

import static org.junit.jupiter.api.Assertions.*;

class ForecastClientTest {

    private ForecastClient forecastClient;

    @BeforeEach
    void setUp() {
        forecastClient = new ForecastClient();
    }

    @Test
    void establishdailyForecastConnection() {
        try {
            String city = "Polokwane";
            String response = forecastClient.establishDailyForcastConnection(city);
            assertNotNull(response);
//            Expected and actual cant be tested effectively since the actual response data is live and will never match the static expected values
        } catch (ConnectionException | ApiResponseException e) {
            fail("Unexpected exception: " + e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getClass().getSimpleName());
        }

    }

    @Test
    public void testEstablishForecastConnection_ApiResponseException() {
        try {
            String city = "Candy Mountain";
            String response = forecastClient.establishDailyForcastConnection(city);
            fail("Expected ConnectionException");
        } catch (ApiResponseException e) {
            // Expected exception
            assertEquals("Unexpected ResponseCode:404", e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void establishCurrentForecastLocationConnection() {
        try {
            String lat = "37.4226711";
            String lon = "122.0849872";
            String response = forecastClient.establishCurrentLocationForcastConnection(lat, lon);
            assertNotNull(response);
//            Expected and actual cant be tested effectively since the actual response data is live and will never match the static expected values
        } catch (ConnectionException | ApiResponseException e) {
            fail("Unexpected exception: " + e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getClass().getSimpleName());
        }

    }

    @Test
    public void testEstablishCurrentForecastLocationConnection_ApiResponseException() {
        try {
            String lat = "37.4226711";
            String lon = "This is right? right?";
            String response = forecastClient.establishCurrentLocationForcastConnection(lat, lon);
            fail("Expected ConnectionException");
        } catch (ApiResponseException e) {
            // Expected exception
            assertEquals("Unexpected ResponseCode:400", e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}