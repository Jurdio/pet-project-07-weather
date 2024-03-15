package edu.weather.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.weather.domain.model.City;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class GeoIPService {
    public String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");

        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public City getCityByIp(HttpServletRequest request) {
        String ipAddress = getClientIp(request);

        try {
            URL url = new URL("https://json.geoiplookup.io/" + ipAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                ObjectMapper objectMapper = new ObjectMapper();
                City city = objectMapper.readValue(response.toString(), City.class);

                // Check if the response contains a 'success' field and it is true
                if (city.isSuccess()) {
                    return city;
                } else {
                    log.warn("Unsuccessful response received for IP address: {}", ipAddress);
                    log.warn("Defaulting to London");
                    return getDefaultCity();
                }
            }
        } catch (Exception e) {
            log.error("Error finding city by IP", e);
            log.warn("Defaulting to London for IP address: {}", ipAddress);
            return getDefaultCity();
        }
    }

    private City getDefaultCity() {
        return City.builder()
                .name("London")
                .latitude(51.4995)
                .longitude(-0.196679)
                .build();
    }
    }
