package edu.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import edu.weather.controller.dto.DayDTO;
import edu.weather.controller.dto.LocationDTO;
import edu.weather.domain.service.WeatherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.util.Locale;

@WebServlet(value = "/weather")
public class WeatherController extends BaseController {

    private WeatherService weatherService;


    @Override
    public void init() throws ServletException {
        super.init();
        weatherService = new WeatherService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String apiKey = "6d0aadf4bc13feed027bc8c22d7a338d";
        String city = "Lviv";

        String weatherData = weatherService.getWeatherData(apiKey, city);

        // Створюємо ObjectMapper і додаємо JavaTimeModule
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LocationDTO locationDTO = objectMapper.readValue(weatherData, LocationDTO.class);
        locationDTO.setName(JsonPath.parse(weatherData).read("$.city.name"));




        webContext.setVariable("locationDTO", locationDTO);
        webContext.setVariable("locale", new Locale("en"));
        webContext.setVariable("nameOfImg", "sunny-with-cloud.jpg");
        System.out.print(locationDTO.getName());

        templateEngine.process("home", webContext, response.getWriter());
    }

    @Override
    public void destroy() {
        try {
            weatherService.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.destroy();
    }
}
