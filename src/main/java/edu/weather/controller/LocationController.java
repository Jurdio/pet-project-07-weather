package edu.weather.controller;

import edu.weather.controller.dto.LocationDTO;
import edu.weather.domain.model.City;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@WebServlet(value = "/my-locations")
public class LocationController extends BaseController {
    private SessionService sessionService = new SessionService();
    private LocationService locationService = new LocationService();
    private WeatherService weatherService = new WeatherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = sessionService.getSessionById(UUID.fromString(String.valueOf(webContext.getVariable("sessionId"))));

        List<Location> locations = locationService.getAllUserLocation(session.getUserId());
        List<LocationDTO> locationDTOList = new ArrayList<>();
        for (Location location : locations){
            LocationDTO locationDTO = weatherService.findLocationByCity(City.builder()
                    .name(location.getName())
                    .longitude(Double.parseDouble(String.valueOf(location.getLongitude())))
                    .latitude(Double.parseDouble(String.valueOf(location.getLatitude())))
                    .build());
            locationDTOList.add(locationDTO);
        }
        webContext.setVariable("locations", locationDTOList);
        webContext.setVariable("locale", new Locale("en"));

        templateEngine.process("mylocations", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Location location = Location.builder()
                .name(req.getParameter("name"))
                .latitude(BigDecimal.valueOf(Double.parseDouble(req.getParameter("latitude"))))
                .longitude(BigDecimal.valueOf(Double.parseDouble(req.getParameter("longitude"))))
                .build();
        Session session = sessionService.getSessionById(UUID.fromString(String.valueOf(webContext.getVariable("sessionId"))));


        locationService.deleteLocation(location, session.getUserId());
        resp.sendRedirect(req.getContextPath() + "/my-locations");
    }
}
