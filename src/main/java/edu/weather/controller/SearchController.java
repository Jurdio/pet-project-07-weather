package edu.weather.controller;

import edu.weather.controller.dto.LocationDTO;
import edu.weather.domain.model.Location;
import edu.weather.domain.model.Session;
import edu.weather.domain.service.LocationService;
import edu.weather.domain.service.SessionService;
import edu.weather.domain.service.WeatherService;
import edu.weather.exception.LocationException;
import edu.weather.exception.weather.OpenWeatherResponseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

@WebServlet(value = "/search")
public class SearchController extends BaseController {
    private final WeatherService weatherService = new WeatherService();
    private final SessionService sessionService = new SessionService();
    private final LocationService locationService = new LocationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Перевірка, чи був переданий параметр "name"
            if (req.getParameter("name") != null) {
                String cityName = req.getParameter("name");
                LocationDTO locationDTO = weatherService.findLocationByName(cityName);
                if (locationDTO.getName() == null){
                    throw new OpenWeatherResponseException("Wrong location name");
                }
                // Отримання ідентифікатора сесії та перевірка на null
                UUID sessionId = UUID.fromString(String.valueOf(webContext.getVariable("sessionId")));
                if (sessionId != null) {
                    Session session = sessionService.getSessionById(sessionId);
                    // Перевірка на null перед використанням об'єкта session
                    if (session != null) {
                        webContext.setVariable("isAlreadyAdded", locationService.isUserAlreadyAddedLocation(locationDTO, session.getUserId()));
                    }
                }
                webContext.setVariable("locationDTO", locationDTO);
                webContext.setVariable("locale", new Locale("en"));
                // Вивід назви місця
                System.out.print(locationDTO.getName());
            } else {
                throw new OpenWeatherResponseException("Wrong location name");
            }
        } catch (OpenWeatherResponseException e) {
            // Обробка помилки при отриманні погодних даних
            webContext.setVariable("error", e.getMessage());
        }
        // Обробка шаблону навіть якщо сталася помилка
        templateEngine.process("search", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Location location = Location.builder()
                .name(req.getParameter("name"))
                .latitude(BigDecimal.valueOf(Double.parseDouble(req.getParameter("latitude"))))
                .longitude(BigDecimal.valueOf(Double.parseDouble(req.getParameter("longitude"))))
                .build();
        try {
            // Отримання ідентифікатора сесії та перевірка на null
            UUID sessionId = UUID.fromString(String.valueOf(webContext.getVariable("sessionId")));
            if (sessionId != null) {
                Session session = sessionService.getSessionById(sessionId);
                // Перевірка на null перед використанням об'єкта session
                if (session != null) {
                    locationService.saveLocation(location, session.getUserId());
                    resp.sendRedirect(req.getContextPath() + "/");
                    return; // Завершення методу після переспрямування
                }
            }
            throw new LocationException("Session ID is null or invalid.");
        } catch (LocationException e) {
            // Обробка помилки при збереженні місця
            webContext.setVariable("error", e.getMessage());
        }
        // Обробка шаблону сторінки пошуку
        templateEngine.process("search", webContext, resp.getWriter());
    }
}
