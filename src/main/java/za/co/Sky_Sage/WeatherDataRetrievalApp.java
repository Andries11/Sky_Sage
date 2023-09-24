package za.co.Sky_Sage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class WeatherDataRetrievalApp {
    public static void main(final String[] args) {
        SpringApplication.run(WeatherDataRetrievalApp.class, args);
    }
}