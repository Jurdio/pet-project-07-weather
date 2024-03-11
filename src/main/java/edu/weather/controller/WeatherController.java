package edu.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.weather.controller.dto.LocationDTO;
import edu.weather.domain.service.WeatherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
        String apiKey = "E6N4QSCR3QJ2XL5HUYC3CCABB";
        String city = "London,UK";

        String weatherData = weatherService.getWeatherData(apiKey, city);

        // Створюємо ObjectMapper і додаємо JavaTimeModule
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LocationDTO locationDTO = objectMapper.readValue(weatherData, LocationDTO.class);

        webContext.setVariable("locationDTO", locationDTO);
        webContext.setVariable("locale", new Locale("en"));

        // Розпарсюємо JSON до об'єкту LocationDTO
//        LocationDTO locationDTO = objectMapper.readValue(weatherData, LocationDTO.class);
//        DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE", new Locale("en"));
//        DateTimeFormatter numberOfDayFormatter = DateTimeFormatter.ofPattern("d", new Locale("en"));
//        DateTimeFormatter nameOfMonthFormatter = DateTimeFormatter.ofPattern("MMMM", new Locale("en"));
//
//        webContext.setVariable("nameOfMonth", locationDTO.getDays().get(0).getDatetime().format(nameOfMonthFormatter));
//        webContext.setVariable("numberOfDay", locationDTO.getDays().get(0).getDatetime().format(numberOfDayFormatter));
//        webContext.setVariable("dayOfWeek", locationDTO.getDays().get(0).getDatetime().format(dayOfWeekFormatter));
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
