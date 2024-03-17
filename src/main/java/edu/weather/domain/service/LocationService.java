package edu.weather.domain.service;

import edu.weather.controller.dto.LocationDTO;
import edu.weather.domain.model.Location;
import edu.weather.domain.repository.LocationRepository;

import java.util.List;

public class LocationService {
    private final LocationRepository locationRepository = new LocationRepository();

    public void saveLocation(Location location, int userId) {
        locationRepository.save(Location.builder()
                .name(location.getName())
                .userId(userId)
                .longitude(location.getLongitude())
                .latitude(location.getLatitude())
                .build());

    }
    public List<Location> getAllUserLocation(int userId){
        return locationRepository.findAllByUserId(userId);
    }
}
