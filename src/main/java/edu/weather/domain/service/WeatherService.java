package edu.weather.domain.service;

import edu.weather.controller.dto.LocationDTO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Locale;

public class WeatherService {

    private CloseableHttpClient httpClient;

    public WeatherService() {
        this.httpClient = HttpClients.createDefault();
    }

    public String getWeatherData(String apiKey, String city) throws IOException {
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
    public void convertTemperatureToCelsius(LocationDTO locationDTO){

    }
    public void close() throws IOException {
        httpClient.close();
    }
}
