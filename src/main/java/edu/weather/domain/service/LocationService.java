package edu.weather.domain.service;

import edu.weather.controller.dto.LocationDTO;
import edu.weather.domain.model.Location;
import edu.weather.domain.repository.LocationRepository;
import edu.weather.exception.LocationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class LocationService {
    private final LocationRepository locationRepository = new LocationRepository();

    public void saveLocation(Location location, int userId) {
        locationRepository.findByCoordinates(location.getLatitude(), location.getLongitude(), userId)
                .ifPresent(existingLocation -> {
                    throw new LocationException("Location already exists");
                });

        locationRepository.save(Location.builder()
                .name(location.getName())
                .userId(userId)
                .longitude(location.getLongitude())
                .latitude(location.getLatitude())
                .build());
    }


    public List<Location> getAllUserLocation(int userId) {
        return locationRepository.findAllByUserId(userId);
    }
    public boolean isUserAlreadyAddedLocation(LocationDTO locationDTO, int userId) {
        Optional<Location> optionalLocation = locationRepository.findByName(locationDTO.getName(), userId);

        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();

            BigDecimal longitudeBD = location.getLongitude().setScale(2, RoundingMode.HALF_UP);
            BigDecimal latitudeBD = location.getLatitude().setScale(2, RoundingMode.HALF_UP);
            BigDecimal longitudeDTOBD = BigDecimal.valueOf(locationDTO.getLongitude()).setScale(2, RoundingMode.HALF_UP);
            BigDecimal latitudeDTOBD = BigDecimal.valueOf(locationDTO.getLatitude()).setScale(2, RoundingMode.HALF_UP);

            return longitudeBD.equals(longitudeDTOBD) && latitudeBD.equals(latitudeDTOBD);
        }

        return false;
    }



    public void deleteLocation(Location location, int userId) {
        locationRepository.deleteByUserId(location, userId);
    }


}
