package edu.weather.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import edu.weather.controller.dto.LocationDTO;
import edu.weather.domain.model.City;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;

public class WeatherService {
    private static final String apiKey = "6d0aadf4bc13feed027bc8c22d7a338d";

    private CloseableHttpClient httpClient;

    public WeatherService() {
        this.httpClient = HttpClients.createDefault();
    }

    public String getWeatherDataByName(String city) throws IOException {
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast/daily?q=" + city + "&" + "cnt=7" + "&" + "units=metric" + "&" + "appid=" + apiKey;

        // Виконуємо HTTP-запит
        HttpGet httpGet = new HttpGet(apiUrl);
        HttpResponse httpResponse = httpClient.execute(httpGet);


        // Отримуємо відповідь
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity);
        } else {
            return "HTTP request failed: " + statusCode;
        }
    }

    public LocationDTO findLocationByCity(City city) throws IOException {
        String weatherData = getWeatherDataByName(city.getName());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LocationDTO locationDTO = objectMapper.readValue(weatherData, LocationDTO.class);

        locationDTO.setName(JsonPath.parse(weatherData).read("$.city.name"));
        locationDTO.setId(JsonPath.parse(weatherData).read("$.city.id"));
        locationDTO.setLatitude(city.getLatitude());
        locationDTO.setLongitude((city.getLongitude()));

        return locationDTO;
    }
    public LocationDTO findLocationByName(String name) throws IOException {
        String weatherData = getWeatherDataByName(name);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LocationDTO locationDTO = objectMapper.readValue(weatherData, LocationDTO.class);

        locationDTO.setName(JsonPath.parse(weatherData).read("$.city.name"));
        locationDTO.setId(JsonPath.parse(weatherData).read("$.city.id"));
        locationDTO.setLatitude(JsonPath.parse(weatherData).read("$.city.coord.lat"));
        locationDTO.setLongitude(JsonPath.parse(weatherData).read("$.city.coord.lon"));

        return locationDTO;
    }


    public void close() throws IOException {
        httpClient.close();
    }
}
