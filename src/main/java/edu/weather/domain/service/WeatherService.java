package edu.weather.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import edu.weather.controller.dto.LocationDTO;
import edu.weather.domain.model.City;
import edu.weather.exception.weather.OpenWeatherExceedingRequestsException;
import edu.weather.exception.weather.OpenWeatherResponseException;
import edu.weather.exception.weather.OpenWeatherUserKeyException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WeatherService {
    private static final String apiKey = "6d0aadf4bc13feed027bc8c22d7a338d";

    private CloseableHttpClient httpClient;

    public WeatherService() {
        this.httpClient = HttpClients.createDefault();
    }

    public String getWeatherDataByName(String city) throws IOException {
        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast/daily?q=" + city + "&" + "cnt=7" + "&" + "units=metric" + "&" + "appid=" + apiKey;

        HttpGet httpGet = new HttpGet(apiUrl);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        String json = EntityUtils.toString(entity);


        int statusCode = Integer.parseInt(JsonPath.parse(json).read("$.cod"));
        try {
            checkResponseCode(statusCode);
        } catch (Exception e) {
            throw new OpenWeatherResponseException("Error requesting weather for location: " + city);
        }
        return json;
    }

    public LocationDTO findLocationByCity(City city) throws IOException {
        String weatherData = getWeatherDataByName(city.getName());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LocationDTO locationDTO = objectMapper.readValue(weatherData, LocationDTO.class);

        locationDTO.setName(JsonPath.parse(weatherData).read("$.city.name"));
        locationDTO.setId(JsonPath.parse(weatherData).read("$.city.id"));
        locationDTO.setCountry(JsonPath.parse(weatherData).read("$.city.country"));
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
        locationDTO.setCountry(JsonPath.parse(weatherData).read("$.city.country"));

        return locationDTO;
    }

    private void checkResponseCode(int code) throws OpenWeatherUserKeyException {
        switch (code) {
            case 401 -> throw new OpenWeatherUserKeyException("You did not specify your API key in API request.");
            case 429 -> throw new OpenWeatherExceedingRequestsException("If you have a Free plan of Professional subscriptions and make more than 60 API calls per minute (surpassing the limit of your subscription). To avoid this situation, please consider upgrading to a subscription plan that meets your needs or reduce the number of API calls in accordance with the limits.");
            case 404 -> throw new OpenWeatherExceedingRequestsException("You can get this error when you specified the wrong city name, ZIP-code or city ID. For your reference, this list contains City name, City ID, Geographical coordinates of the city (lon, lat), Zoom, etc.");
            case 500, 502, 503, 504 -> throw new OpenWeatherResponseException("In case you receive one of the following errors 500, 502, 503 or 504 please contact us for assistance. Please enclose an example of your API request that receives this error into your email to let us analyze it and find a solution for you promptly.");
        }
    }


    public void close() throws IOException {
        httpClient.close();
    }
}
