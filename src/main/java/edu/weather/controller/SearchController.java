package edu.weather.controller;

import edu.weather.controller.dto.LocationDTO;
import edu.weather.domain.model.Location;
import edu.weather.domain.model.Session;
import edu.weather.domain.service.LocationService;
import edu.weather.domain.service.SessionService;
import edu.weather.domain.service.WeatherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

@WebServlet(value = "/search")
public class SearchController extends BaseController{
    private WeatherService weatherService = new WeatherService();
    SessionService sessionService = new SessionService();
    LocationService locationService = new LocationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cityName = req.getParameter("name");
        LocationDTO locationDTO = weatherService.findLocationByName(cityName);

        webContext.setVariable("locationDTO", locationDTO);
        webContext.setVariable("locale", new Locale("en"));
        System.out.print(locationDTO.getName());


        templateEngine.process("search", webContext, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Location location = Location.builder()
                .name(req.getParameter("name"))
                .latitude(BigDecimal.valueOf(Double.parseDouble(req.getParameter("latitude"))))
                .longitude(BigDecimal.valueOf(Double.parseDouble(req.getParameter("longitude"))))
                .build();
        Session session = sessionService.getSessionById(UUID.fromString(String.valueOf(webContext.getVariable("sessionId"))));
        System.out.println("POST");
        locationService.saveLocation(location, session.getUserId());
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
